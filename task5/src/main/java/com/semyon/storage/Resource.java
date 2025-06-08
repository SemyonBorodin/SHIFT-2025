package com.semyon.storage;

import java.util.concurrent.atomic.AtomicInteger;

public class Resource {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private final int id;

    public Resource() {
        this.id = counter.getAndIncrement();
    }

    public int getId() {
        return id;
    }
}
