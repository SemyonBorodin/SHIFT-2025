package ru.cft.javaLessons.miner.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameTimer {
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> timerTask;
    private long startTimeMillis;
    private int secondsElapsed;
    private boolean isRunning;
    private final List<TimerTickEventListener> listeners = new ArrayList<>();

    public GameTimer() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.secondsElapsed = 0;
        this.isRunning = false;
    }

    public void start() {
        if (!isRunning) {
            startTimeMillis = System.currentTimeMillis();
            secondsElapsed = 0;
            isRunning = true;
            timerTask = scheduler.scheduleAtFixedRate(() -> {
                secondsElapsed = (int) ((System.currentTimeMillis() - startTimeMillis) / 1000);
                notifyListeners();
            }, 0, 1, TimeUnit.SECONDS);
            notifyListeners();
        }
    }

    public void reset() {
        if (timerTask != null) {
            timerTask.cancel(false);
        }
        isRunning = false;
        secondsElapsed = 0;
        notifyListeners();
    }

    public void stop() {
        if (timerTask != null) {
            timerTask.cancel(false);
        }
        secondsElapsed = getSecondsElapsed();
        isRunning = false;
        notifyListeners();
    }

    public int getSecondsElapsed() {
        if (isRunning) {
            return (int) ((System.currentTimeMillis() - startTimeMillis) / 1000);
        }
        return secondsElapsed;
    }

    public void addTimerTickListener(TimerTickEventListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (TimerTickEventListener listener : listeners) {
            listener.onTimerTick(secondsElapsed);
        }
    }
}