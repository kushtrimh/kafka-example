package com.kushtrimh.kafkaexampleproducer.http;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kushtrimh.kafkaexampleproducer.http.entity.PlayerCountResponse;
import com.kushtrimh.kafkaexampleproducer.json.JSONConverter;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Kushtrim Hajrizi
 */
public class SteamAPIClient {

    @Value("${steam.stats.uri}")
    private String steamStatsURI;

    private HttpClient client;
    private JSONConverter jsonConverter;

    public static SteamAPIClient newInstanceWithDefaults() {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        HttpClient client = HttpClient.newBuilder()
                .executor(executorService)
                .connectTimeout(Duration.ofMillis(5000))
                .build();
        JSONConverter jsonConverter = new JSONConverter(PropertyNamingStrategy.LOWER_CASE);
        return new SteamAPIClient(client, jsonConverter);
    }

    public static SteamAPIClient newInstance(HttpClient client, JSONConverter jsonConverter) {
        return new SteamAPIClient(client, jsonConverter);
    }

    private SteamAPIClient(HttpClient client, JSONConverter jsonConverter) {
        this.client = client;
        this.jsonConverter = jsonConverter;
    }

    public PlayerCountResponse getPlayerCount(String appId) {
        var request = HttpRequest.newBuilder(buildURI(steamStatsURI, Map.of("appid", appId)))
                .GET()
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> jsonConverter.toJSON(PlayerCountResponse.class));
        // TODO: When response is received process it and send to the Kafka consumer
        return new PlayerCountResponse(); // Temporary
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
