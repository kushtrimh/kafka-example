package com.kushtrimh.kafkaexampleproducer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author Kushtrim Hajrizi
 */
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfigurationProperties {

    private String bootstrapServer;
    private String topic;

    public KafkaConfigurationProperties() {
    }

    public KafkaConfigurationProperties(String bootstrapServer, String topic) {
        this.bootstrapServer = bootstrapServer;
        this.topic = topic;
    }

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public void setBootstrapServer(String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KafkaConfigurationProperties that = (KafkaConfigurationProperties) o;
        return Objects.equals(bootstrapServer, that.bootstrapServer) && Objects.equals(topic, that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bootstrapServer, topic);
    }

    @Override
    public String toString() {
        return "KafkaConfigurationProperties{" +
                "bootstrapServer='" + bootstrapServer + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
