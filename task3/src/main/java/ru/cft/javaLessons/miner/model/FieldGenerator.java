package ru.cft.javaLessons.miner.model;

public interface FieldGenerator {
    void generate(Cell[][] field, int bombsRequiredCount, int firstClickedX, int firstClickedY);
}
