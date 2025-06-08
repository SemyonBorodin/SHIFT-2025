package com.semyon.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static final String DEFAULT_CONFIG_FILE = "app.properties";
    private static final String PRODUCER_COUNT = "producer.count";
    private static final String CONSUMER_COUNT = "consumer.count";
    private static final String PRODUCER_TIME = "producer.time";
    private static final String CONSUMER_TIME = "consumer.time";
    private static final String STORAGE_SIZE = "storage.size";

    public ConfigLoader() {
    }

    public Config loadConfig(String fileName) throws ConfigException {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new ConfigException("Файл конфигурации не найден: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new ConfigException("Ошибка загрузки файла конфигурации", e);
        }

        return new Config(
                getInt(properties, PRODUCER_COUNT),
                getInt(properties, CONSUMER_COUNT),
                getInt(properties, PRODUCER_TIME),
                getInt(properties, CONSUMER_TIME),
                getInt(properties, STORAGE_SIZE)
        );
    }


    public int getInt(Properties properties, String key) throws ConfigException {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new ConfigException("Ключ в конфигурации не найден: " + key);
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ConfigException("Ключ '" + key + "' не парсится как int: " + value, e);
        }
    }
}
