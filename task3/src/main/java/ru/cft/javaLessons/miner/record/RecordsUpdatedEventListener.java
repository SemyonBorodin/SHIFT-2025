package ru.cft.javaLessons.miner.model.listeners;

import ru.cft.javaLessons.miner.record.Record;

public interface RecordsUpdatedEventListener {
    void onRecordsUpdated(Record noviceRecord, Record mediumRecord, Record expertRecord);
}