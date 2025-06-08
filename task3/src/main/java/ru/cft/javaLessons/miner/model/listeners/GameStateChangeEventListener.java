package ru.cft.javaLessons.miner.model.listeners;

import ru.cft.javaLessons.miner.model.GameState;
import ru.cft.javaLessons.miner.model.GameType;

public interface GameStateChangeEventListener {
    void onGameStateChanged(GameState state, GameType gameType);
}