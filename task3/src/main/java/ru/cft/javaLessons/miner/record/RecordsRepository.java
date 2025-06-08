package ru.cft.javaLessons.miner.record;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.javaLessons.miner.model.GameType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class RecordsRepository {
    private static final String FILE_PATH = new File("records.json").getAbsolutePath();
    private static final String DEFAULT_RECORD_NAME = "Unknown";
    private static final int DEFAULT_RECORD_TIME = 999;
    private static final Record DEFAULT_RECORD = new Record(DEFAULT_RECORD_NAME, DEFAULT_RECORD_TIME);
    private static RecordsRepository instance;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File(FILE_PATH);
    private Map<String, Record> records;

    private RecordsRepository() {
        load();
    }

    public static RecordsRepository getInstance() {
        if (instance == null) {
            instance = new RecordsRepository();
        }
        return instance;
    }

    public void load() {
        try {
            if (file.exists()) {
                records = objectMapper.readValue(file, new TypeReference<>() {
                });
            } else {
                records = new HashMap<>();
                for (GameType gameType : GameType.values()) {
                    records.put(gameType.toString(), DEFAULT_RECORD);
                }
                save();
            }
        } catch (IOException e) {
            records = new HashMap<>();
            for (GameType gameType : GameType.values()) {
                records.put(gameType.toString(), DEFAULT_RECORD);
            }
        }
    }

    public Map<String, Record> getRecords() {
        return records;
    }

    public void updateRecord(String difficulty, Record newRecord) {
        records.put(difficulty, newRecord);
        save();
    }

    private void save() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, records);
        } catch (IOException e) {
            records = new HashMap<>();
        }
    }
}
