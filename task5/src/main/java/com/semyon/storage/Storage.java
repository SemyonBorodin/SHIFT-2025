package com.semyon.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    private static final Logger logger = LoggerFactory.getLogger(Storage.class);

    private final Queue<Resource> resources = new LinkedList<>();
    private final int capacity;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(Resource resource) throws InterruptedException {
        while (resources.size() >= capacity) {
            logger.info("Склад заполнен: {}/{}", resources.size(), capacity);
            wait();
        }

        resources.add(resource);
        logger.info("Состояние склада после put: {}/{}", resources.size(), capacity);

        notifyAll();
    }

    public synchronized Resource take() throws InterruptedException {
        while (resources.isEmpty()) {
            logger.info("Cклад пуст");
            wait();
        }
        Resource resource = resources.poll();
        logger.info("Состояние склада после take: {}/{}", resources.size(), capacity);

        notifyAll();
        return resource;
    }
}
