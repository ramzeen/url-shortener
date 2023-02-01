package com.ramzeen.urlshortener.repository;

import com.ramzeen.urlshortener.domain.URLShortenerResponse;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryURLRepository implements URLRepository {
    private final Map<String, URLShortenerResponse> map = new HashMap<>();

    @Override
    public Optional<URLShortenerResponse> getURLShortenerResponse(String shortURLSuffix) {
        return Optional.ofNullable(map.get(shortURLSuffix));
    }

    @Override
    public void saveURLShortenerResponse(URLShortenerResponse urlShortenerResponse) {
        map.put(urlShortenerResponse.getShortURLSuffix(), urlShortenerResponse);
    }

    @Override
    public List<URLShortenerResponse> getAll() {
        return new ArrayList<>(map.values());
    }
}
