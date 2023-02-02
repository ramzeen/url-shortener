package com.ramzeen.urlshortener.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class URLShortenerResponse {
    public static final String BASE_URL = "https://www.shorturl.com";
    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-MM HH:mm:ss");
    private final String longURL;
    private final String shortURLSuffix;
    private final String userId;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime expirationDateTime;

    public URLShortenerResponse(String longURL, String shortURLSuffix, String userId, LocalDateTime createdDateTime, LocalDateTime expirationDateTime) {
        this.longURL = longURL;
        this.shortURLSuffix = shortURLSuffix;
        this.userId = userId;
        this.createdDateTime = createdDateTime;
        this.expirationDateTime = expirationDateTime;
    }

    public String getLongURL() {
        return longURL;
    }

    public String getShortURL() {
        return BASE_URL + "/" + shortURLSuffix;
    }

    @JsonIgnore
    public String getShortURLSuffix() {
        return shortURLSuffix;
    }

    public String getExpiration() {
        return expirationDateTime.format(FORMAT);
    }

    public String getUserId() {
        return userId;
    }

    public String getCreated() {
        return createdDateTime.format(FORMAT);
    }

    @JsonIgnore
    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    @JsonIgnore
    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    @Override
    public String toString() {
        return "URLShortenerResponse{" +
                "longURL='" + longURL + '\'' +
                ", shortURLSuffix='" + shortURLSuffix + '\'' +
                ", userId='" + userId + '\'' +
                ", created=" + createdDateTime +
                ", expiration=" + expirationDateTime +
                '}';
    }
}
