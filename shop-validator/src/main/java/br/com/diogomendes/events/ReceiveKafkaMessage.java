package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import br.com.diogomendes.dto.ShopItemDTO;
import br.com.diogomendes.model.Product;
import br.com.diogomendes.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class ReceiveKafkaMessage {

    private static final String SHOP_TOPIC_NAME = "SHOP_TOPIC";
    private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";
    private static final Logger log = LoggerFactory.getLogger(ReceiveKafkaMessage.class);

    private final ProductRepository repository;
    private final KafkaTemplate<String, ShopDTO> kafkaTemplate;

    public ReceiveKafkaMessage(ProductRepository repository, KafkaTemplate<String, ShopDTO> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = SHOP_TOPIC_NAME, groupId = "group")
    public void listenShopTopic(ShopDTO shopDTO) {
        try {
            log.info("Compra recebida no tópico: {}.", shopDTO.getIdentifier());

            var success = true;
            for (var item : shopDTO.getItems()) {
                var product = repository.findByIdentifier(item.getProductIdentifier());

                if (!isValidShop(item, product)) {
                    shopError(shopDTO);
                    success = false;
                    break;
                }

            }

            if (success) {
                shopSuccess(shopDTO);
            }

        } catch (Exception e) {
            log.error("Erro no processamento da comprar {}", shopDTO.getIdentifier());
        }
    }

    private boolean isValidShop(ShopItemDTO item, Product product) {
        return product != null && product.getAmount() >= item.getAmount();
    }

    private void shopError(ShopDTO shopDTO) {
        log.info("Erro no processamento da comprar {}.", shopDTO.getIdentifier());
        shopDTO.setStatus("ERROR");
        kafkaTemplate.send(SHOP_TOPIC_EVENT_NAME, shopDTO);
    }

    private void shopSuccess(ShopDTO shopDTO) {
        log.info("Compra {} efetuada com sucesso.", shopDTO.getIdentifier());
        shopDTO.setStatus("SUCCESS");
        kafkaTemplate.send(SHOP_TOPIC_EVENT_NAME, shopDTO);
    }
}
