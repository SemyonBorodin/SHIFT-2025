package ru.cft.javaLessons.miner.model;

public class Cell {
    private final int x;
    private final int y;
    private boolean isOpen;
    private boolean hasBomb;
    private boolean isFlagged;
    private int adjacentBombs;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.hasBomb = false;
        this.isOpen = false;
        this.isFlagged = false;
        this.adjacentBombs = 0;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen() {
        this.isOpen = true;
    }

    public void setOpen(boolean cond) {
        this.isOpen = cond;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void setBomb(boolean bomb) {
        hasBomb = bomb;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlag(boolean flag) {
        this.isFlagged = flag;
    }

    public int getAdjacentBombsCount() {
        return adjacentBombs;
    }

    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }
}
