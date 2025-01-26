package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import br.com.diogomendes.repository.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiveKafkaMessage {

    private final ShopRepository repository;

    private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";
    private static final Logger log = LoggerFactory.getLogger(ReceiveKafkaMessage.class);

    public ReceiveKafkaMessage(ShopRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = SHOP_TOPIC_EVENT_NAME, groupId = "group")
    public void listenShopEvents(ShopDTO shopDTO) {
        try {
            log.info("Status da compra recebida no tópico: {}.", shopDTO.getIdentifier());

            var shop = repository.findByIdentifier(shopDTO.getIdentifier());
            shop.setStatus(shopDTO.getStatus());
            repository.save(shop);
        } catch (Exception e) {
            log.error("Erro no processamento da compra {}", shopDTO.getIdentifier());
        }
    }

}
