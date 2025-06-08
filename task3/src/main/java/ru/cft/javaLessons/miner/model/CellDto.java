package ru.cft.javaLessons.miner.model;

public record CellDto(
        int x,
        int y,
        boolean isOpen,
        boolean hasBomb,
        boolean isFlagged,
        int adjacentBombsCount
) {
}
