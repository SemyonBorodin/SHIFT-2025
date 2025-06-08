package com.semyon;

import com.semyon.config.Config;
import com.semyon.config.ConfigException;
import com.semyon.config.ConfigLoader;
import com.semyon.consumer.Consumer;
import com.semyon.producer.Producer;
import com.semyon.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Config config;
        try {
            config = new ConfigLoader().loadConfig(ConfigLoader.DEFAULT_CONFIG_FILE);
        } catch (ConfigException e) {
            logger.error("Ошибка загрузки конфигурации: {}", e.getMessage());
            return;
        }
        int producerCount = config.producerCount();
        int consumerCount = config.consumerCount();
        int producerTime = config.producerTime();
        int consumerTime = config.consumerTime();
        int storageSize = config.storageSize();

        Storage storage = new Storage(config.storageSize());
        logger.info("Заппуск для: producers={}, consumers={}, storageSize={}",
                producerCount, consumerCount, storageSize);

        for (int i = 0; i < producerCount; i++) {
            new Thread(new Producer(storage, producerTime)).start();
        }

        for (int i = 0; i < consumerCount; i++) {
            new Thread(new Consumer(storage, consumerTime)).start();
        }
    }
}
