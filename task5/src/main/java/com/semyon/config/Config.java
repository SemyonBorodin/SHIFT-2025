package com.semyon.config;

public record Config(
        int producerCount,
        int consumerCount,
        int producerTime,
        int consumerTime,
        int storageSize
) {
}
