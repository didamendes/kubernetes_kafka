package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import br.com.diogomendes.dto.ShopItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ReceiveKafkaMessageTest {

    @InjectMocks
    private ReceiveKafkaMessage receiveKafkaMessage;

    @Mock
    private KafkaTemplate<String, ShopDTO> kafkaTemplate;

    private static final String	SHOP_TOPIC_RETRY = "SHOP_TOPIC_RETRY";

    public ShopDTO getShopDTO() {
        var	shopDTO	=	new	ShopDTO();
        shopDTO.setBuyerIdentifier("b-1");
        var	shopItemDTO	=	new ShopItemDTO();
        shopItemDTO.setAmount(1000);
        shopItemDTO.setProductIdentifier("product-1");
        shopItemDTO.setPrice((float)	100);
        shopDTO.getItems().add(shopItemDTO);
        return	shopDTO;
    }

    @Test
    public void testProcessShopSuccess() throws Exception {
        var shopDTO = getShopDTO();

        receiveKafkaMessage.listenShopTopic(shopDTO);

        Mockito.verify(kafkaTemplate, Mockito.never())
                .send(SHOP_TOPIC_RETRY, shopDTO);
    }

    @Test
    public void testProcessShopError() throws Exception {
        var shopDTO = getShopDTO();
        shopDTO.setItems(new ArrayList<>());

        receiveKafkaMessage.listenShopTopic(shopDTO);

        Mockito.verify(kafkaTemplate, Mockito.times(1))
                .send(SHOP_TOPIC_RETRY, shopDTO);

    }

}