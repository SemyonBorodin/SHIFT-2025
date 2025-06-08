package ru.cft.javaLessons.miner.record;

import ru.cft.javaLessons.miner.model.GameType;
import ru.cft.javaLessons.miner.model.listeners.RecordsUpdatedEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecordsManager {
    private final RecordsRepository recordsRepository;
    private final List<RecordEventListener> recordListeners = new ArrayList<>();
    private final List<RecordsUpdatedEventListener> recordsUpdatedListeners = new ArrayList<>();

    public RecordsManager() {
        this.recordsRepository = RecordsRepository.getInstance();
        recordsRepository.load();
        notifyRecordsUpdatedListeners();
    }

    public boolean isNewRecord(GameType gameType, int time) {
        return checkNewRecord(gameType, time);
    }

    private boolean checkNewRecord(GameType gameType, int time) {
        Record currentRecord = recordsRepository.getRecords().get(gameType.toString());
        return currentRecord == null || time <= currentRecord.getTime();
    }

    public void saveNewRecord(GameType gameType, String name, int time) {
        recordsRepository.updateRecord(gameType.toString(), new Record(name, time));
        notifyRecordListeners(gameType, time, true);
        notifyRecordsUpdatedListeners();
    }

    public void addRecordsUpdatedEventListener(RecordsUpdatedEventListener listener) {
        recordsUpdatedListeners.add(listener);
    }

    private void notifyRecordListeners(GameType gameType, int timeInSeconds, boolean isNewRecord) {
        for (RecordEventListener listener : recordListeners) {
            listener.onRecordAchieved(gameType, timeInSeconds, isNewRecord);
        }
    }

    public void notifyRecordsUpdatedListeners() {
        Record noviceRecord = recordsRepository.getRecords().get("NOVICE");
        Record mediumRecord = recordsRepository.getRecords().get("MEDIUM");
        Record expertRecord = recordsRepository.getRecords().get("EXPERT");
        for (RecordsUpdatedEventListener listener : recordsUpdatedListeners) {
            listener.onRecordsUpdated(noviceRecord, mediumRecord, expertRecord);
        }
    }
}