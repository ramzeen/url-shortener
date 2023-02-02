package com.ramzeen.urlshortener.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramzeen.urlshortener.NoSuchURLException;
import com.ramzeen.urlshortener.domain.URLExpanderResponse;
import com.ramzeen.urlshortener.domain.URLShortenerRequest;
import com.ramzeen.urlshortener.domain.URLShortenerResponse;
import com.ramzeen.urlshortener.service.URLShortenerService;
import com.ramzeen.urlshortener.service.URLShortenerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.ramzeen.urlshortener.domain.URLShortenerResponse.BASE_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(URLShortenerController.class)
public class TestURLShortenerController {

    private final static String A_SAMPLE_SHORTENED_URL_SUFFIX = "pqrs";
    private final static String A_SAMPLE_SHORTENED_URL = BASE_URL + "/" + A_SAMPLE_SHORTENED_URL_SUFFIX;
    private final static String A_SAMPLE_LONG_URL = "http://www.yahoo.com";
    private final static String A_SAMPLE_USER_ID = "someuser";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private URLShortenerService urlShortenerService;

    @Test
    public void testThatWhenTheShortenerServiceReturnsAGoodResponseForURLShorteningThenTheControllerReturnsAGoodResponseToTheCaller() throws Exception {
        URLShortenerResponse response = makeGoodShortenerResponse();
        when(urlShortenerService.shortenURL(any())).thenReturn(response);
        this.mockMvc.perform(post("/api/v1/create").contentType(MediaType.APPLICATION_JSON).content(toJson(makeRequest()))).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    public void testThatWhenTheShortenerServiceReturnsAGoodResponseForURLExpansionThenTheControllerRedirectsToTheCorrectLongURL() throws Exception {
        URLExpanderResponse response = makeGoodExpanderResponse();
        when(urlShortenerService.expandURL(any())).thenReturn(response);
        this.mockMvc.perform(get("/" + A_SAMPLE_SHORTENED_URL_SUFFIX)).andDo(print()).andExpect(redirectedUrl(A_SAMPLE_LONG_URL)).andExpect(status().is3xxRedirection());
    }

    @Test
    public void testThatWhenTheShortenerServiceReturnsNoURLFoundResponseForURLExpansionThenTheControllerRespondsWithANotFoundError() throws Exception {
        URLExpanderResponse response = makeGoodExpanderResponse();
        when(urlShortenerService.expandURL(any())).thenThrow(new NoSuchURLException(""));
        this.mockMvc.perform(get("/" + A_SAMPLE_SHORTENED_URL_SUFFIX)).andDo(print()).andExpect(status().isNotFound());
    }

    private URLExpanderResponse makeGoodExpanderResponse() {
        return new URLExpanderResponse(A_SAMPLE_SHORTENED_URL_SUFFIX, A_SAMPLE_LONG_URL);
    }

    private URLShortenerResponse makeGoodShortenerResponse() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        return new URLShortenerResponse(A_SAMPLE_LONG_URL, A_SAMPLE_SHORTENED_URL, A_SAMPLE_USER_ID, now, now.plusDays(10));
    }


    private URLShortenerRequest makeRequest() {
        return new URLShortenerRequest(A_SAMPLE_USER_ID, A_SAMPLE_LONG_URL);
    }

    private String toJson(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
