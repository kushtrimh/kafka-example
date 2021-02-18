package com.kushtrimh.kafkaexampleproducer.config;

import com.kushtrimh.kafkaexampleproducer.config.entity.Games;
import com.kushtrimh.kafkaexampleproducer.json.JSONConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Kushtrim Hajrizi
 */
@Component
public class GameProperties {

    private static final Logger log = LoggerFactory.getLogger(GameProperties.class);

    @Value("classpath:games.json")
    private Resource resource;
    private Games games;
    private JSONConverter jsonConverter;

    public GameProperties(JSONConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @PostConstruct
    public void init() throws IOException {
        log.info("Started to read the games.json file");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream inputStream = resource.getInputStream();
        inputStream.transferTo(output);
        String content = output.toString(StandardCharsets.UTF_8);
        log.info("Available games: {}", content);
        games = jsonConverter.fromJSON(content, Games.class).orElse(new Games());
    }

    public Games getGames() {
        return games;
    }
}
