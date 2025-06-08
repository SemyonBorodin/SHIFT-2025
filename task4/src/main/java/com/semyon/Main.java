package com.semyon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        try {
            long n = inputHandler.readInput();

            ParallelTasksCalculator calculator = new ParallelTasksCalculator();
            calculator.addAutoChunkedTasks(n);

            double result = calculator.calculate();
            logger.info("Итоговый результат: {}", result);

            calculator.shutdown();
        } catch (Exception e) {
            logger.error("Ошибка вычисления: {}", e.getMessage());
            System.exit(1);
        }
    }
}
