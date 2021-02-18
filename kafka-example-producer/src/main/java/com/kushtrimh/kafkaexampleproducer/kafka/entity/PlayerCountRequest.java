package com.kushtrimh.kafkaexampleproducer.kafka.entity;

import java.util.Objects;

/**
 * @author Kushtrim Hajrizi
 */
public class PlayerCountRequest implements MessageRequest {
    private int playerCount;
    private String appId;
    private String appName;

    public PlayerCountRequest() {
    }

    public PlayerCountRequest(int playerCount, String appId, String appName) {
        this.playerCount = playerCount;
        this.appId = appId;
        this.appName = appName;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerCountRequest that = (PlayerCountRequest) o;
        return playerCount == that.playerCount && Objects.equals(appId, that.appId) && Objects.equals(appName, that.appName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerCount, appId, appName);
    }

    @Override
    public String toString() {
        return "PlayerCountRequest{" +
                "playerCount=" + playerCount +
                ", appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }
}
