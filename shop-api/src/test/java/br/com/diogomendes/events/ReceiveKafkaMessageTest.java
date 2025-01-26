package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import br.com.diogomendes.dto.ShopItemDTO;
import br.com.diogomendes.model.Shop;
import br.com.diogomendes.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ReceiveKafkaMessageTest {

    @InjectMocks
    private ReceiveKafkaMessage receiveKafkaMessage;

    @Mock
    private ShopRepository repository;

    @Test
    public void testSuccessfulMessageReceiverd() {
        var	shopDTO	=	new ShopDTO();
        shopDTO.setStatus("SUCCESS");
        var	shopItemDTO	=	new ShopItemDTO();
        shopItemDTO.setAmount(1000);
        shopItemDTO.setProductIdentifier("product-1");
        shopItemDTO.setPrice((float)	100);
        shopDTO.getItems().add(shopItemDTO);
        var	shop =	Shop.convert(shopDTO);

        Mockito.when(repository.findByIdentifier(shopDTO.getIdentifier())).thenReturn(shop);

        receiveKafkaMessage.listenShopEvents(shopDTO);

        Mockito.verify(repository, Mockito.times(1))
                .findByIdentifier(shopDTO.getIdentifier());
        Mockito.verify(repository, Mockito.times(1))
                .save(shop);
    }


}