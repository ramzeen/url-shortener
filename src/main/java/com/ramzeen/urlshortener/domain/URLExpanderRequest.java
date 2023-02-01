package com.ramzeen.urlshortener.domain;

public class URLExpanderRequest {
    private final String shortURL;

    public URLExpanderRequest(String shortURL) {
        this.shortURL = shortURL;
    }

    public String getShortURL() {
        return shortURL;
    }
}
