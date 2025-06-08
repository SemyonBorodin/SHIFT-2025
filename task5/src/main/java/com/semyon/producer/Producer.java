package com.semyon.producer;

import com.semyon.storage.Resource;
import com.semyon.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private final int id;
    private final Storage storage;
    private final int productionTime;

    public Producer(Storage storage, int productionTime) {
        this.id = ProducerIdGenerator.nextId();
        this.storage = storage;
        this.productionTime = productionTime;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(productionTime);
                Resource resource = new Resource();
                storage.put(resource);
                logger.info("Производитель {} поместил ресурс c Id = {} на склад", id, resource.getId());

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.info("Производитель {} остановлен", id);
        }
    }
}

