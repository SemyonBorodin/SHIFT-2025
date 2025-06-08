package ru.cft.javaLessons.miner.record;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Record {
    private String name;
    private int time;

    @JsonCreator
    public Record(@JsonProperty("name") String name,
                  @JsonProperty("time") int time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return name + ": " + time;
    }
}
