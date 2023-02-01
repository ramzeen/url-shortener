package com.ramzeen.urlshortener.domain;

public class URLExpanderResponse {
    private final String shortURL;
    private final String longURL;

    public URLExpanderResponse(String shortURL, String longURL) {
        this.shortURL = shortURL;
        this.longURL = longURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public String getLongURL() {
        return longURL;
    }

    @Override
    public String toString() {
        return "URLExpanderResponse{" +
                "shortURL='" + shortURL + '\'' +
                ", longURL='" + longURL + '\'' +
                '}';
    }
}
