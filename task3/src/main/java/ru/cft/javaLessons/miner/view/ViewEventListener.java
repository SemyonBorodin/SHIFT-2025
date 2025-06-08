package ru.cft.javaLessons.miner.view;

public interface ViewEventListener {
    void onNewGameRequested();

    void onCellClicked(int x, int y, ButtonType type);
}
