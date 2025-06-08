package ru.cft.javaLessons.miner.model.listeners;

import ru.cft.javaLessons.miner.model.CellDto;
import ru.cft.javaLessons.miner.model.GameState;
import ru.cft.javaLessons.miner.model.GameType;

public interface GameEndedEventListener {
    void onGameEnded(GameState state, GameType gameType, CellDto[][] fieldState, int remainingMines);
}