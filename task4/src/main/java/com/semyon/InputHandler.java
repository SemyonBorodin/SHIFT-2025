package com.semyon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class InputHandler {
    private static final Logger logger = LoggerFactory.getLogger(InputHandler.class);

    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public long readInput() {
        while (true) {
            try {
                logger.info("Введите целое положительное число n:");
                long input = scanner.nextLong();
                if (input > 0) {
                    return input;
                }
                logger.warn("Введите целое неотрицательное число n! Вы ввели: {}", input);
            } catch (Exception e) {
                logger.warn("Ошибка валидации - введите целое неотрицательное число");
                scanner.next();
            }
        }
    }

    public void close() {
        scanner.close();
    }
}

