package com.ramzeen.urlshortener.service;

import com.ramzeen.urlshortener.NoSuchURLException;
import com.ramzeen.urlshortener.domain.URLExpanderRequest;
import com.ramzeen.urlshortener.domain.URLExpanderResponse;
import com.ramzeen.urlshortener.domain.URLShortenerRequest;
import com.ramzeen.urlshortener.domain.URLShortenerResponse;
import com.ramzeen.urlshortener.repository.URLRepository;
import com.ramzeen.urlshortener.utils.Encoder;
import com.ramzeen.urlshortener.utils.UniqueIdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class URLShortenerServiceImpl implements URLShortenerService {

    static final long DEFAULT_TTL_DAYS = 365;
    private URLRepository urlRepository;
    private UniqueIdGenerator idGenerator;
    private Encoder encoder;

    public URLShortenerServiceImpl(URLRepository urlRepository, UniqueIdGenerator idGenerator, Encoder encoder) {
        this.urlRepository = urlRepository;
        this.idGenerator = idGenerator;
        this.encoder = encoder;
    }

    @Override
    public URLShortenerResponse shortenURL(URLShortenerRequest request) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        URLShortenerResponse response = new URLShortenerResponse(request.getLongURL(), makeShortURL(), request.getUserId(), now, calculateExpiration(now));
        urlRepository.saveURLShortenerResponse(response);
        return response;
    }

    private String makeShortURL() {
        long nextId = idGenerator.nextId();
        return encoder.encode(nextId);
    }

    @Override
    public URLExpanderResponse expandURL(URLExpanderRequest request) throws NoSuchURLException {
        String shortURL = request.getShortURL();
        Optional<URLShortenerResponse> urlShortenerResponse = urlRepository.getURLShortenerResponse(shortURL);
        if (urlShortenerResponse.isEmpty())
            throw new NoSuchURLException(shortURL);
        // We can add logic to validate the expiration date here
        return new URLExpanderResponse(shortURL, urlShortenerResponse.get().getLongURL());
    }

    @Override
    public List<URLShortenerResponse> getAll() {
        return urlRepository.getAll();
    }

    private LocalDateTime calculateExpiration(LocalDateTime now) {
        return now.plusDays(DEFAULT_TTL_DAYS);
    }
}
