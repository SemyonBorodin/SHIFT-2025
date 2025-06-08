package ru.cft.javaLessons.miner.record;

import ru.cft.javaLessons.miner.model.GameType;

public interface RecordEventListener {
    void onRecordAchieved(GameType gameType, int timeInSeconds, boolean isNewRecord);
}