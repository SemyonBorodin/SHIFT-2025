package ru.cft.javaLessons.miner.view;

import ru.cft.javaLessons.miner.model.*;

import java.util.List;

public interface View {
    void updateCell(int x, int y, CellDto cellDto);

    void resetGameView(GameType type);

    void blockField();

    void showWinDialog(boolean isNewRecord);

    void showLoseDialog();

    void setOnRecordNameEntered(java.util.function.Consumer<String> callback);
}

