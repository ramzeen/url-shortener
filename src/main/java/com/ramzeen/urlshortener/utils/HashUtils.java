package com.ramzeen.urlshortener.utils;

import org.springframework.stereotype.Component;

@Component
public class HashUtils {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public String encode(long n) {
        int baseSize = ALPHABET.length;
        StringBuffer result = new StringBuffer();
        while(n > 0) {
            result.append(ALPHABET[(int) (n % baseSize)]);
            n = n / baseSize;
        }
        return result.reverse().toString();
    }
}
