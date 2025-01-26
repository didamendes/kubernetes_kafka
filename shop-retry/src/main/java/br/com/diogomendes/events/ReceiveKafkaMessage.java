package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReceiveKafkaMessage {

    private final KafkaTemplate<String, ShopDTO> kafkaTemplate;

    private static final String SHOP_TOPIC = "SHOP_TOPIC";
    private static final String SHOP_TOPIC_RETRY = "SHOP_TOPIC_RETRY";
    private static final Logger log = LoggerFactory.getLogger(ReceiveKafkaMessage.class);

    public ReceiveKafkaMessage(KafkaTemplate<String, ShopDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = SHOP_TOPIC, groupId = "group_retry")
    public void listenShopTopic(ShopDTO shopDTO) throws Exception {
        try {
            log.info("Compra recebida no topico: {}", shopDTO.getIdentifier());

            if (shopDTO.getItems() == null || shopDTO.getItems().isEmpty()) {
                log.error("Comprar sem items.");
                throw new Exception();
            }
        } catch (Exception e) {
            log.info("Erro na aplicação");
            kafkaTemplate.send(SHOP_TOPIC_RETRY, shopDTO);
        }

    }

    @KafkaListener(topics = SHOP_TOPIC_RETRY, groupId = "group_retry")
    public void listenShopTopicRetry(ShopDTO shopDTO) throws Exception {
        log.info("Retentativa de processamento: {},", shopDTO.getIdentifier());
    }

}
