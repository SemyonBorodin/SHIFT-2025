package ru.cft.javaLessons.miner.model.listeners;

import ru.cft.javaLessons.miner.model.GameType;

public interface GameResetEventListener {
    void onGameReset(GameType gameType, int bombCount);
}