package ru.cft.javaLessons.miner.view;

import ru.cft.javaLessons.miner.model.GameType;

public interface GameTypeListener {
    void onGameTypeChanged(GameType gameType);
}
