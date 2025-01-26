package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import br.com.diogomendes.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiveKafkaMessage {

    private final ReportRepository repository;

    private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";
    private static final Logger log = LoggerFactory.getLogger(ReceiveKafkaMessage.class);

    public ReceiveKafkaMessage(ReportRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @KafkaListener(topics = SHOP_TOPIC_EVENT_NAME, groupId = "group_report")
    public void listenShopTopic(ShopDTO shopDTO) {
        try {
            log.info("Compra recebida no tópico: {}", shopDTO.getIdentifier());
            repository.incrementShopStatus(shopDTO.getStatus());
        } catch (Exception e) {
            log.error("Erro no processamento da mensagem", e);
        }
    }
}
