package com.ramzeen.urlshortener.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class UniqueIdGenerator {

    private static final AtomicLong sequence = new AtomicLong(10000000l);


    public long nextId() {
        return sequence.getAndIncrement();
    }
}
