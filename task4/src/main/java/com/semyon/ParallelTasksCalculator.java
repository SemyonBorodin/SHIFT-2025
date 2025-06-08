package com.semyon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelTasksCalculator {
    private static final Logger logger = LoggerFactory.getLogger(ParallelTasksCalculator.class);

    private final ExecutorService executor;
    private final List<CalculationPartTask> tasks;

    public ParallelTasksCalculator() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(availableProcessors);
        this.tasks = new ArrayList<>();
        logger.info("Инициализация на {} ядрах", availableProcessors);
    }


    public void addTask(long start, long end) {
        CalculationPartTask task = new CalculationPartTask(start, end);
        tasks.add(task);
        logger.debug("Добавлена task'a на интервале {}-{}", start, end);
    }

    public void addAutoChunkedTasks(long totalNumbers) {
        if (totalNumbers <= 0) {
            throw new IllegalArgumentException("Число элементов для расчёта должно быть целым числом, большим 0.");
        }

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        long chunkSize = (long) Math.ceil((double) totalNumbers / availableProcessors);

        logger.info("Установлен размер чанка: {}", chunkSize);

        for (int i = 0; i < availableProcessors; i++) {
            long start = i * chunkSize + 1;
            long end = Math.min((i + 1) * chunkSize, totalNumbers);
            if (start <= totalNumbers) {
                addTask(start, end);
            }
        }
    }

    public double calculate() throws ExecutionException, InterruptedException {
        List<Future<Double>> futures = executor.invokeAll(tasks);
        double result = 0;
        for (Future<Double> future : futures) {
            result += future.get();
        }
        return result;
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}