package com.semyon.producer;

import java.util.concurrent.atomic.AtomicInteger;

final class ProducerIdGenerator {
    private static final AtomicInteger counter = new AtomicInteger();

    private ProducerIdGenerator() {
    }

    static int nextId() {
        return counter.incrementAndGet();
    }
}
