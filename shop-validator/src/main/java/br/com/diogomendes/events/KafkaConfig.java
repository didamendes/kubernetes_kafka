package br.com.diogomendes.events;

import br.com.diogomendes.dto.ShopDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrapAddress:localhost:9092}")
    private String bootstrapAddress;

    public ProducerFactory<String, ShopDTO> producerFactory() {
        var props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "shop-api");
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, ShopDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    public ConsumerFactory<String, ShopDTO> consumerFactory() {
        var deserializar = new JsonDeserializer<ShopDTO>(ShopDTO.class);

        var props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializar);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShopDTO> kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, ShopDTO>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
