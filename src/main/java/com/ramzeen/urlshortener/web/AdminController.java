package com.ramzeen.urlshortener.web;

import com.ramzeen.urlshortener.domain.URLShortenerResponse;
import com.ramzeen.urlshortener.service.URLShortenerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private URLShortenerService urlShortenerService;

    public AdminController(URLShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/show")
    public List<URLShortenerResponse> showContents() {
        return urlShortenerService.getAll();
    }
}
