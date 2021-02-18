package com.kushtrimh.kafkaexampleproducer.config.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Kushtrim Hajrizi
 */
public class Games {
    private List<Game> games = new ArrayList<>();

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Games games1 = (Games) o;
        return Objects.equals(games, games1.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(games);
    }

    @Override
    public String toString() {
        return "Games{" +
                "games=" + games +
                '}';
    }
}
