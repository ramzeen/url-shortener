package com.ramzeen.urlshortener.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramzeen.urlshortener.domain.URLShortenerResponse;
import com.ramzeen.urlshortener.service.URLShortenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class TestAdminController {
    @MockBean
    private URLShortenerService urlShortenerService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testThatWhenTheServiceReturnsResponsesThenTheControllerReturnsThemToTheCaller() throws Exception {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        URLShortenerResponse responseA = new URLShortenerResponse("AAA", "A", "A_USER", now, now.plusDays(365));
        URLShortenerResponse responseB = new URLShortenerResponse("BBB", "B", "B_USER", now.plusDays(-5), now.plusDays(360));
        List<URLShortenerResponse> responses = List.of(responseA, responseB);
        when(urlShortenerService.getAll()).thenReturn(responses);
        this.mockMvc.perform(get("/admin/show")).andDo(print()).andExpect(content().json(toJson(responses))).andExpect(status().isOk());
    }

    private String toJson(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
