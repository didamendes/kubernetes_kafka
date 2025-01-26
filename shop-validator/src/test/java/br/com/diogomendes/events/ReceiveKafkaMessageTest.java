package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import br.com.diogomendes.dto.ShopItemDTO;
import br.com.diogomendes.model.Product;
import br.com.diogomendes.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ReceiveKafkaMessageTest {

    @InjectMocks
    private ReceiveKafkaMessage receiveKafkaMessage;

    @Mock
    private KafkaTemplate<String, ShopDTO> kafkaTemplate;

    @Mock
    private ProductRepository productRepository;

    private static final	String	SHOP_TOPIC_EVENT_NAME	=	"SHOP_TOPIC_EVENT";

    public	ShopDTO	getShopDTO() {
        var	shopDTO	=   new ShopDTO();
        shopDTO.setBuyerIdentifier("b-1");
        var	shopItemDTO	=	new ShopItemDTO();
        shopItemDTO.setAmount(1000);
        shopItemDTO.setProductIdentifier("product-1");
        shopItemDTO.setPrice((float)	100);
        shopDTO.getItems().add(shopItemDTO);
        return	shopDTO;
    }
    public Product getProduct() {
        Product	product	=	new	Product();
        product.setAmount(1000);
        product.setId(1L);
        product.setIdentifier("product-1");
        return	product;
    }

    @Test
    public void testProcessShopSuccess() {
        var shopDTO = getShopDTO();
        var product = getProduct();

        Mockito.when(productRepository.findByIdentifier("product-1")).thenReturn(product);

        receiveKafkaMessage.listenShopTopic(shopDTO);

        Mockito.verify(kafkaTemplate, Mockito.times(1))
                .send(SHOP_TOPIC_EVENT_NAME, shopDTO);
        Assertions.assertEquals(shopDTO.getStatus(), "SUCCESS");

    }

    @Test
    public void testProcessShopError() {
        var shopDTO = getShopDTO();

        Mockito.when(productRepository.findByIdentifier("product-1")).thenReturn(null);

        receiveKafkaMessage.listenShopTopic(shopDTO);
        Mockito.verify(kafkaTemplate, Mockito.times(1))
                .send(SHOP_TOPIC_EVENT_NAME, shopDTO);
        Assertions.assertEquals(shopDTO.getStatus(), "ERROR");
    }

}