package com.kushtrimh.kafkaexampleconsumer.kafka;

import com.kushtrimh.kafkaexampleconsumer.config.KafkaConfigurationProperties;
import com.kushtrimh.kafkaexampleconsumer.kafka.entity.PlayerCountRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Kushtrim Hajrizi
 */
@Component
public class StatsGatherer {

    private static final Logger log = LoggerFactory.getLogger(StatsGatherer.class);

    private KafkaConfigurationProperties kafkaConfigurationProperties;

    public StatsGatherer(KafkaConfigurationProperties kafkaConfigurationProperties) {
        this.kafkaConfigurationProperties = kafkaConfigurationProperties;
    }

    @KafkaListener(id = "playerCountStatsListener", topics =  "${kafka.topic}")
    public void listForPlayerCountStats(PlayerCountRequest playerCountRequest) {
        log.info("Received: {}", playerCountRequest);
    }
}
