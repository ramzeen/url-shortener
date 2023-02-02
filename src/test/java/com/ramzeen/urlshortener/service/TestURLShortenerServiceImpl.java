package com.ramzeen.urlshortener.service;

import com.ramzeen.urlshortener.NoSuchURLException;
import com.ramzeen.urlshortener.domain.URLExpanderRequest;
import com.ramzeen.urlshortener.domain.URLShortenerRequest;
import com.ramzeen.urlshortener.domain.URLShortenerResponse;
import com.ramzeen.urlshortener.repository.URLRepository;
import com.ramzeen.urlshortener.utils.Encoder;
import com.ramzeen.urlshortener.utils.UniqueIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TestURLShortenerServiceImpl {
    private final static String A_SAMPLE_SHORTENED_URL_SUFFIX = "pqrs";
    private final static String A_SAMPLE_LONG_URL = "http://www.yahoo.com";
    private final static String A_SAMPLE_USER_ID = "someuser";

    @Mock
    Encoder encoder;
    @Mock
    UniqueIdGenerator idGenerator;
    @Mock
    URLRepository repository;

    @InjectMocks
    URLShortenerServiceImpl service;

    @BeforeEach
    public void setup() {
        when(idGenerator.nextId()).thenReturn(1234l);
        when(encoder.encode(anyLong())).thenReturn(A_SAMPLE_SHORTENED_URL_SUFFIX);
    }

    @Test
    public void testThatWhenCalledWithCorrectParametersThenTheServiceProducesCorrectResults() {
        URLShortenerResponse response = service.shortenURL(new URLShortenerRequest("a", "b"));
        assertEquals(A_SAMPLE_SHORTENED_URL_SUFFIX, response.getShortURLSuffix());
        //verify data is persisted in the db
        Mockito.verify(repository, Mockito.times(1)).saveURLShortenerResponse(response);
    }

    @Test
    public void testThatWhenTheSuppliedShortURLIsLegitimateThenTheCorrespondingLongURLIsReturned() throws Exception {
        URLExpanderRequest request = new URLExpanderRequest(A_SAMPLE_SHORTENED_URL_SUFFIX);
        URLShortenerResponse shortenerResponse = new URLShortenerResponse(A_SAMPLE_LONG_URL, A_SAMPLE_SHORTENED_URL_SUFFIX, A_SAMPLE_USER_ID,
                createLocalDateTime(-5), createLocalDateTime(360));
        when(repository.getURLShortenerResponse(A_SAMPLE_SHORTENED_URL_SUFFIX)).thenReturn(Optional.of(shortenerResponse));
        assertEquals(A_SAMPLE_LONG_URL, service.expandURL(request).getLongURL());
    }

    @Test
    public void testThatWhenTheSuppliedShortURLHasNoRecordThenANoSuchURLExceptionIsThrown() {
        URLExpanderRequest request = new URLExpanderRequest(A_SAMPLE_SHORTENED_URL_SUFFIX);
        when(repository.getURLShortenerResponse(A_SAMPLE_SHORTENED_URL_SUFFIX)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchURLException.class, () -> {
            service.expandURL(request);
        });
    }

    @Test
    public void testThatWhenTheSuppliedShortURLHasExpiredThenANoSuchURLExceptionIsThrown() {
        URLExpanderRequest request = new URLExpanderRequest(A_SAMPLE_SHORTENED_URL_SUFFIX);
        URLShortenerResponse shortenerResponse = new URLShortenerResponse(A_SAMPLE_LONG_URL, A_SAMPLE_SHORTENED_URL_SUFFIX, A_SAMPLE_USER_ID,
                createLocalDateTime(-375), createLocalDateTime(-10));
        when(repository.getURLShortenerResponse(A_SAMPLE_SHORTENED_URL_SUFFIX)).thenReturn(Optional.of(shortenerResponse));
        Exception exception = assertThrows(NoSuchURLException.class, () -> {
            service.expandURL(request);
        });
    }

    private LocalDateTime createLocalDateTime(long days) {
        return LocalDateTime.now(ZoneOffset.UTC).plusDays(days);
    }
}
