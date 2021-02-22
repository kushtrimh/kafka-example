package com.kushtrimh.kafkaexampleconsumer.config;

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
    private String groupId;
    private String offsetResetConfig;

    public KafkaConfigurationProperties() {
    }

    public KafkaConfigurationProperties(String bootstrapServer, String topic,
                                        String groupId, String offsetResetConfig) {
        this.bootstrapServer = bootstrapServer;
        this.topic = topic;
        this.groupId = groupId;
        this.offsetResetConfig = offsetResetConfig;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOffsetResetConfig() {
        return offsetResetConfig;
    }

    public void setOffsetResetConfig(String offsetResetConfig) {
        this.offsetResetConfig = offsetResetConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KafkaConfigurationProperties that = (KafkaConfigurationProperties) o;
        return Objects.equals(bootstrapServer, that.bootstrapServer) && Objects.equals(topic, that.topic)
                && Objects.equals(groupId, that.groupId) && Objects.equals(offsetResetConfig, that.offsetResetConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bootstrapServer, topic, groupId, offsetResetConfig);
    }

    @Override
    public String toString() {
        return "KafkaConfigurationProperties{" +
                "bootstrapServer='" + bootstrapServer + '\'' +
                ", topic='" + topic + '\'' +
                ", groupId='" + groupId + '\'' +
                ", offsetResetConfig='" + offsetResetConfig + '\'' +
                '}';
    }
}
