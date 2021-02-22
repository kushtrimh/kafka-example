package com.kushtrimh.kafkaexampleconsumer.kafka;

import com.kushtrimh.kafkaexampleconsumer.config.KafkaConfigurationProperties;
import com.kushtrimh.kafkaexampleconsumer.kafka.entity.PlayerCountRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kushtrim Hajrizi
 */
@Configuration
@EnableKafka
public class KafkaConfiguration {

    private KafkaConfigurationProperties kafkaConfigurationProperties;

    public KafkaConfiguration(KafkaConfigurationProperties kafkaConfigurationProperties) {
        this.kafkaConfigurationProperties = kafkaConfigurationProperties;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, PlayerCountRequest>>
            kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PlayerCountRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(1000);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PlayerCountRequest> consumerFactory() {
        var stringDeserializer = new StringDeserializer();
        var jsonDeserializer = new JsonDeserializer<>(PlayerCountRequest.class, false);
        jsonDeserializer.trustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                stringDeserializer,
                jsonDeserializer);
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigurationProperties.getBootstrapServer());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigurationProperties.getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfigurationProperties.getOffsetResetConfig());
        return props;
    }
}
