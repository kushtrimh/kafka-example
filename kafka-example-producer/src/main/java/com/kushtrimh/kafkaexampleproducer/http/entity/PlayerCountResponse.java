package com.kushtrimh.kafkaexampleproducer.http.entity;

import java.util.Objects;

/**
 * @author Kushtrim Hajrizi
 */
public class PlayerCountResponse {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerCountResponse that = (PlayerCountResponse) o;
        return Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response);
    }

    @Override
    public String toString() {
        return "PlayerCountResponse{" +
                "response=" + response +
                '}';
    }

    public static class Response {
        private int playerCount;
        private int result;

        public int getPlayerCount() {
            return playerCount;
        }

        public void setPlayerCount(int playerCount) {
            this.playerCount = playerCount;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Response response = (Response) o;
            return playerCount == response.playerCount && result == response.result;
        }

        @Override
        public int hashCode() {
            return Objects.hash(playerCount, result);
        }

        @Override
        public String toString() {
            return "Response{" +
                    "playerCount=" + playerCount +
                    ", result=" + result +
                    '}';
        }
    }
}
