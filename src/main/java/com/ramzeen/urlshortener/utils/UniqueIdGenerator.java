package com.ramzeen.urlshortener.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UniqueIdGenerator {
    @Value("${server.id:101}")
    private int serverId;


    public long nextId() {
        return System.nanoTime() + serverId;
    }
}
