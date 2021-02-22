package com.kushtrimh.kafkaexampleproducer;

import com.kushtrimh.kafkaexampleproducer.config.GameProperties;
import com.kushtrimh.kafkaexampleproducer.config.entity.Game;
import com.kushtrimh.kafkaexampleproducer.config.entity.Games;
import com.kushtrimh.kafkaexampleproducer.http.SteamAPIClient;
import com.kushtrimh.kafkaexampleproducer.http.entity.PlayerCountResponse;
import com.kushtrimh.kafkaexampleproducer.kafka.entity.MessageRequest;
import com.kushtrimh.kafkaexampleproducer.kafka.entity.PlayerCountRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Kushtrim Hajrizi
 */
@Component
public class StatsManager {

    private static final Logger log = LoggerFactory.getLogger(StatsManager.class);

    private KafkaTemplate<String, MessageRequest> kafkaTemplate;
    private SteamAPIClient steamAPIClient;
    private GameProperties gameProperties;

    public StatsManager(KafkaTemplate<String, MessageRequest> kafkaTemplate,
                        SteamAPIClient steamAPIClient,
                        GameProperties gameProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.steamAPIClient = steamAPIClient;
        this.gameProperties = gameProperties;
    }

    @Scheduled(fixedRate = 10000)
    public void retrievePlayerCountStats() {
        Games games = gameProperties.getGames();
        games.getGames().forEach(game -> {
            steamAPIClient.getPlayerCountAsync(game.getAppId())
                    .thenAccept(playerCountResponseOpt -> {
                        playerCountResponseOpt.ifPresentOrElse(playerCountResponse -> {
                            sendMessageToKafka(playerCountResponse, game);
                        }, () -> log.error("Did not receive response for player count for app id {}: ", game.getAppId()));
                    });
        });
    }

    private void sendMessageToKafka(PlayerCountResponse response, Game game) {
        String appId = game.getAppId();
        var request = new PlayerCountRequest(
                response.getResponse().getPlayerCount(),
                appId,
                game.getName());
        kafkaTemplate.sendDefault(appId, request)
                .addCallback(result -> {
                    log.info("Message sent to Kafka successfully for app id {}", appId);
                }, exception -> {
                    log.info("Could not send message {} to Kafka", request, exception);
                });
    }
}
