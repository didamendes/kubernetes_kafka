package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class KafkaClientTest {

    @InjectMocks
    private KafkaClient kafkaClient;

    @Mock
    private KafkaTemplate<String, ShopDTO> kafkaTemplate;

    private static final String SHOP_TOPIC_NAME = "SHOP_TOPIC";

    @Test
    public void testSendMessage() {
        var shopDTO = new ShopDTO();
        shopDTO.setStatus("SUCCESS");
        shopDTO.setBuyerIdentifier("b-1");

        kafkaClient.sendMessage(shopDTO);

        Mockito.verify(kafkaTemplate, Mockito.times(1))
                .send(SHOP_TOPIC_NAME, "b-1", shopDTO);
    }

}