package com.semyon.consumer;

import com.semyon.storage.Resource;
import com.semyon.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final int id;
    private final Storage storage;
    private final int consumptionTime;

    public Consumer(Storage storage, int consumptionTime) {
        this.id = ConsumerIdGenerator.nextId();
        this.storage = storage;
        this.consumptionTime = consumptionTime;
        logger.info("Создан потребитель с id: {}", id);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(consumptionTime);
                Resource resource = storage.take();
                logger.info("Потребитель {} забрал ресурс с id = {} со склада", id, resource.getId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.info("Потребитель {} остановлен", id);
        }
    }
}
