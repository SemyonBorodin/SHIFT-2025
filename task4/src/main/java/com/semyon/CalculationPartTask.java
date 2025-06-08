package com.semyon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Callable;

public class CalculationPartTask implements Callable<Double> {
    private static final Logger logger = LoggerFactory.getLogger(CalculationPartTask.class);

    private final long start;
    private final long end;

    public CalculationPartTask(long start, long end) {
        this.start = start;
        this.end = end;
        logger.info("{} Создана Task'а для диапазона {}-{}", Thread.currentThread().getName(), start, end);
    }


    private double complexFunction(double x) {
        // Сумма ряда = \frac{\pi^2}{6} ~ 1.6449
        return 1/(x*x);
    }

    @Override
    public Double call() {
        logger.info("Начало вычислений в диапазоне {}-{}", start, end);
        double result = 0;
        for (long i = start; i <= end; i++) {
            result += complexFunction(i);
        }
        logger.info("Завершены вычисления в диапазоне {}-{} с результатом {}", start, end, result);
        return result;
    }
}