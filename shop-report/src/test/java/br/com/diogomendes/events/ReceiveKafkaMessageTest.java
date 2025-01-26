package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import br.com.diogomendes.repository.ReportRepository;
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
    private ReportRepository repository;

    public ShopDTO getShopDTO() {
        var	shopDTO	=	new	ShopDTO();
        shopDTO.setStatus("SUCCESS");
        return	shopDTO;
    }

    @Test
    public void testProcessShopSuccess() {
        var shopDTO = getShopDTO();

        receiveKafkaMessage.listenShopTopic(shopDTO);

        Mockito.verify(repository, Mockito.times(1))
                .incrementShopStatus(shopDTO.getStatus());
    }

}