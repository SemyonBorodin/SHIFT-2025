package ru.cft.javaLessons.miner.model.listeners;

import ru.cft.javaLessons.miner.model.CellDto;

import java.util.List;

public interface CellUpdateEventListener {
    void onCellUpdated(List<CellDto> cellDtos);
}

