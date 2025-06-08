package com.semyon.consumer;

import java.util.concurrent.atomic.AtomicInteger;

final class ConsumerIdGenerator {
    private static final AtomicInteger counter = new AtomicInteger();

    private ConsumerIdGenerator() {}

    static int nextId() {
        return counter.incrementAndGet();
    }
}
