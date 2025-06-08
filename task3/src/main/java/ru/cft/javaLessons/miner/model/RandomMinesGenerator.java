package ru.cft.javaLessons.miner.model;

import java.util.Random;

public class RandomMinesGenerator implements FieldGenerator {
    private final Random random = new Random();

    @Override
    public void generate(Cell[][] field, int bombsRequiredCount, int firstClickedX, int firstClickedY) {
        int height = field.length;
        int width = field[0].length;

        for (Cell[] cells : field) {
            for (int x = 0; x < width; x++) {
                cells[x].setBomb(false);
                cells[x].setAdjacentBombs(0);
            }
        }

        int placedBombs = 0;
        while (placedBombs < bombsRequiredCount) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (isSafeCell(x, y, firstClickedX, firstClickedY) && !field[y][x].hasBomb()) {
                field[y][x].setBomb(true);
                placedBombs++;
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!field[y][x].hasBomb()) {
                    int count = countAdjacentBombs(field, width, height, x, y);
                    field[y][x].setAdjacentBombs(count);
                }
            }
        }

    }

    private int countAdjacentBombs(Cell[][] field, int width, int height, int x, int y) {
        int count = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < width && ny < height && field[ny][nx].hasBomb()) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isSafeCell(int x, int y, int firstClickedX, int firstClickedY) {
        return x != firstClickedX || y != firstClickedY;
    }
}