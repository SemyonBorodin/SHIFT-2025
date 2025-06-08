package ru.cft.javaLessons.miner.model;

public enum GameType {
    NOVICE(9, 9, 10),
    MEDIUM(16, 16, 40),
    EXPERT(16, 30, 99);

    private final int height;
    private final int width;
    private final int bombs;

    GameType(int height, int width, int bombs) {
        this.height = height;
        this.width = width;
        this.bombs = bombs;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBombs() {
        return bombs;
    }
}
