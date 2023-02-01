package com.ramzeen.urlshortener.web;

import com.ramzeen.urlshortener.NoSuchURLException;
import com.ramzeen.urlshortener.domain.URLExpanderRequest;
import com.ramzeen.urlshortener.domain.URLExpanderResponse;
import com.ramzeen.urlshortener.domain.URLShortenerRequest;
import com.ramzeen.urlshortener.domain.URLShortenerResponse;
import com.ramzeen.urlshortener.service.URLShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class URLShortenerController {

    private static final String REDIRECT_DEFAULT_URL = "http://wwww.somedefaulturl.com";

    private URLShortenerService urlShortenerService;

    public URLShortenerController(URLShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/api/v1/create")
    public URLShortenerResponse getURLShortenerResponse(@RequestBody URLShortenerRequest request) {
        return urlShortenerService.shortenURL(request);
    }

    @GetMapping("/{shortURL}")
    public RedirectView expand(@PathVariable String shortURL) {
        try {
            URLExpanderResponse response = urlShortenerService.expandURL(new URLExpanderRequest(shortURL));
            String originalURL = response.getLongURL();
            if (!originalURL.startsWith("http")) {
                originalURL = "https://" + originalURL;
            }
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(originalURL);
            return redirectView;
        } catch (NoSuchURLException e) {
            throw new ResourceNotFoundException();
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
    }
}
