package ru.cft.javaLessons.miner.utils;

import ru.cft.javaLessons.miner.model.CellDto;
import ru.cft.javaLessons.miner.view.GameImage;

public class CellImageResolver {
    public static GameImage resolve(CellDto dto) {
        if (!dto.isOpen()) {
            return dto.isFlagged() ? GameImage.MARKED : GameImage.CLOSED;
        }
        if (dto.hasBomb()) {
            return GameImage.BOMB;
        }
        return GameImage.fromNumber(dto.adjacentBombsCount());
    }
}
