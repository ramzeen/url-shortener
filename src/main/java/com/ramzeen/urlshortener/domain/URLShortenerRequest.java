package com.ramzeen.urlshortener.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class URLShortenerRequest {
    private final String userId;
    private final String longURL;

    @JsonCreator
    public URLShortenerRequest(@JsonProperty("userId") String userId, @JsonProperty("longURL") String longURL) {
        this.userId = userId;
        this.longURL = longURL;
    }

    public String getLongURL() {
        return longURL;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "URLShortenerRequest{" +
                "userId='" + userId + '\'' +
                ", longURL='" + longURL + '\'' +
                '}';
    }
}
