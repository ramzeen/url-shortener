package com.ramzeen.urlshortener.service;

import com.ramzeen.urlshortener.NoSuchURLException;
import com.ramzeen.urlshortener.domain.URLExpanderRequest;
import com.ramzeen.urlshortener.domain.URLExpanderResponse;
import com.ramzeen.urlshortener.domain.URLShortenerRequest;
import com.ramzeen.urlshortener.domain.URLShortenerResponse;

import java.util.List;

public interface URLShortenerService {
    URLShortenerResponse shortenURL(URLShortenerRequest request);
    URLExpanderResponse  expandURL(URLExpanderRequest request) throws NoSuchURLException;

    List<URLShortenerResponse> getAll();
}
