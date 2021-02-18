package com.kushtrimh.kafkaexampleproducer.config.entity;

import java.util.Objects;

/**
 * @author Kushtrim Hajrizi
 */
public class Game {
    private String name;
    private String appId;

    public Game() {
    }

    public Game(String name, String appId) {
        this.name = name;
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(name, game.name) && Objects.equals(appId, game.appId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, appId);
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }
}
