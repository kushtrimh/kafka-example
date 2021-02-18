package com.kushtrimh.kafkaexampleproducer.kafka;

import com.kushtrimh.kafkaexampleproducer.config.KafkaConfigurationProperties;
import com.kushtrimh.kafkaexampleproducer.kafka.entity.MessageRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kushtrim Hajrizi
 */
@Configuration
public class KafkaConfiguration {

    private KafkaConfigurationProperties kafkaConfigurationProperties;

    public KafkaConfiguration(KafkaConfigurationProperties kafkaConfigurationProperties) {
        this.kafkaConfigurationProperties = kafkaConfigurationProperties;
    }

    @Bean
    public KafkaTemplate<String, MessageRequest> kafkaTemplate() {
        KafkaTemplate<String, MessageRequest> template = new KafkaTemplate<>(producerFactory());
        template.setDefaultTopic(kafkaConfigurationProperties.getTopic());
        return template;
    }

    @Bean
    public ProducerFactory<String, MessageRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigurationProperties.getBootstrapServer());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
}
