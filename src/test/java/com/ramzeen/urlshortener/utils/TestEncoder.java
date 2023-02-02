package com.ramzeen.urlshortener.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEncoder {

    // Unit tests may not be needed for this class. Maybe some sort of property based generative tests can be done?

    Encoder encoder = new Encoder();

    @Test
    public void testThatWhenTheInputIsMalformedThenTheReturnedValueIsEmpty() {
        assertEquals("", encoder.encode(-22));
    }

    @Test
    public void testThatWhenTheInputIsAHugeNumberThenAValidValueIsReturned() {
        assertTrue(encoder.encode(Integer.MAX_VALUE).length() > 0);
    }
}
