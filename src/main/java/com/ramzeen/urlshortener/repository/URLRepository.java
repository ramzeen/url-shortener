package com.ramzeen.urlshortener.repository;

import com.ramzeen.urlshortener.domain.URLShortenerResponse;

import java.util.List;
import java.util.Optional;

public interface URLRepository {
    public Optional<URLShortenerResponse> getURLShortenerResponse(String shortURLSuffix);

    public void saveURLShortenerResponse(URLShortenerResponse urlShortenerResponse);

    public List<URLShortenerResponse> getAll();
}
