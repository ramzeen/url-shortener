package com.ramzeen.urlshortener;

public class NoSuchURLException extends Exception{

    private final String url;

    public NoSuchURLException(String url) {
        this.url = url;
    }
}
