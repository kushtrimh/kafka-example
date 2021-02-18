package com.kushtrimh.kafkaexampleproducer.http;

import com.kushtrimh.kafkaexampleproducer.http.entity.PlayerCountResponse;
import com.kushtrimh.kafkaexampleproducer.json.JSONConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Kushtrim Hajrizi
 */
@Component
public class SteamAPIClient {

    private static final Logger log = LoggerFactory.getLogger(SteamAPIClient.class);

    @Value("${steam.stats.uri}")
    private String steamStatsURI;

    private HttpClient client;
    private JSONConverter jsonConverter;

    @Autowired
    public SteamAPIClient(JSONConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        this.client = HttpClient.newBuilder()
                .executor(executorService)
                .connectTimeout(Duration.ofMillis(5000))
                .build();
    }

    public SteamAPIClient(HttpClient client, JSONConverter jsonConverter) {
        this.client = client;
        this.jsonConverter = jsonConverter;
    }

    public Optional<PlayerCountResponse> getPlayerCount(String appId) {
        var request = HttpRequest.newBuilder(buildURI(steamStatsURI, Map.of("appid", appId)))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return jsonConverter.fromJSON(response.body(), PlayerCountResponse.class);
        } catch (IOException | InterruptedException e) {
            log.error("Could not make request to retrieve player count", e);
        }
        return Optional.empty();
    }

    public CompletableFuture<Optional<PlayerCountResponse>> getPlayerCountAsync(String appId) {
        var request = HttpRequest.newBuilder(buildURI(steamStatsURI, Map.of("appid", appId)))
                .GET()
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApplyAsync(response -> {
                    log.info("Response received: {} - {}", response.statusCode(), response.body());
                    return jsonConverter.fromJSON(response.body(), PlayerCountResponse.class);
                });
    }

    private URI buildURI(String baseUri, Map<String, String> queryParams) {
        var uriBuilder = new StringBuilder(baseUri);
        if (queryParams.isEmpty()) {
            return URI.create(baseUri);
        }
        uriBuilder.append("?");
        Iterator<Map.Entry<String, String>> queryParamsIterator = queryParams.entrySet().iterator();
        while (queryParamsIterator.hasNext()) {
            Map.Entry<String, String> entry = queryParamsIterator.next();
            uriBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            if (queryParamsIterator.hasNext()) {
                uriBuilder.append("&");
            }
        }
        return URI.create(uriBuilder.toString());
    }
}
