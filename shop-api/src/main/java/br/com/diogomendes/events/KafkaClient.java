package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaClient {

    private final KafkaTemplate<String, ShopDTO> kafkaTemplate;

    private static final String SHOP_TOPIC_NAME = "SHOP_TOPIC";

    public KafkaClient(KafkaTemplate<String, ShopDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ShopDTO msg) {
        kafkaTemplate.send(SHOP_TOPIC_NAME, msg.getBuyerIdentifier(), msg);
    }

}
