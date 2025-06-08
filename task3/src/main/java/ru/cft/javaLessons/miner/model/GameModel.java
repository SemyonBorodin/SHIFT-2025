package ru.cft.javaLessons.miner.model;

import ru.cft.javaLessons.miner.model.listeners.*;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private GameType gameType;
    private final FieldGenerator fieldGenerator;
    private Cell[][] field;
    private int bombCount;
    private int flagsCount;
    private boolean isFirstClick;
    private GameState gameState;

    private final List<CellUpdateEventListener> cellUpdateListeners = new ArrayList<>();
    private final List<GameStateChangeEventListener> stateChangeListeners = new ArrayList<>();
    private final List<GameEndedEventListener> gameEndedListeners = new ArrayList<>();
    private final List<GameResetEventListener> resetListeners = new ArrayList<>();

    public GameModel(GameType gameType, FieldGenerator fieldGenerator) {
        this.gameType = gameType;
        this.fieldGenerator = fieldGenerator;
        this.bombCount = gameType.getBombs();
        this.flagsCount = 0;
        this.isFirstClick = true;
        this.gameState = GameState.GAME_NOT_STARTED;
        reset();
    }

    public void reset() {
        this.field = new Cell[gameType.getHeight()][gameType.getWidth()];
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                field[y][x] = new Cell(x, y);
            }
        }
        this.flagsCount = 0;
        this.isFirstClick = true;
        this.gameState = GameState.GAME_NOT_STARTED;
        notifyResetListeners();
    }

    private void clearField() {
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                Cell cell = field[y][x];
                cell.setOpen(false);
                cell.setFlag(false);
                cell.setBomb(false);
                cell.setAdjacentBombs(0);
            }
        }
    }

    public void reset(GameType newGameType) {
        this.gameType = newGameType;
        this.bombCount = newGameType.getBombs();
        reset();
    }

    public void processAction(int x, int y, CellAction action) {
        if (gameState == GameState.WON || gameState == GameState.LOST) {
            return;
        }
        switch (action) {
            case OPEN -> openCell(x, y);
            case FLAG -> flagCell(x, y);
            case MIDDLE_BUTTON -> openAroundIfFlagged(x, y);
        }
    }

    private void openCell(int x, int y) {
        if (isFirstClick) {
            fieldGenerator.generate(field, bombCount, x, y);
            isFirstClick = false;
            setGameState(GameState.GAME_IN_PROGRESS);
        }
        if (openSingleCell(x, y)) {
            setGameState(GameState.LOST);
            return;
        }
        if (field[y][x].getAdjacentBombsCount() == 0) {
            openAdjacent(x, y);
        }
        checkVictory();
    }

    private boolean openSingleCell(int x, int y) {
        Cell cell = field[y][x];
        if (cell.isOpen() || cell.isFlagged()) {
            return false;
        }
        cell.setOpen();
        notifyCellUpdateListeners(List.of(new CellDto(x, y, cell.isOpen(), cell.hasBomb(), cell.isFlagged(), cell.getAdjacentBombsCount())));
        return cell.hasBomb();
    }

    private void flagCell(int x, int y) {
        Cell cell = field[y][x];
        if (cell.isOpen()) {
            return;
        }
        if (cell.isFlagged()) {
            cell.setFlag(false);
            flagsCount--;
        } else if (flagsCount < bombCount) {
            cell.setFlag(true);
            flagsCount++;
        }
        notifyCellUpdateListeners(List.of(new CellDto(x, y, cell.isOpen(), cell.hasBomb(), cell.isFlagged(), cell.getAdjacentBombsCount())));
    }

    private void openAroundIfFlagged(int x, int y) {
        Cell cell = field[y][x];
        if (!cell.isOpen() || cell.getAdjacentBombsCount() != countFlagsAround(x, y)) {
            return;
        }
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int nx = x + dx;
                int ny = y + dy;
                if (isInBounds(nx, ny) && !field[ny][nx].isOpen() && !field[ny][nx].isFlagged()) {
                    openCell(nx, ny);
                }
            }
        }
    }

    private void openAdjacent(int x, int y) {
        List<CellDto> updatedCells = new ArrayList<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dy == 0 && dx == 0) continue;
                int nx = x + dx;
                int ny = y + dy;
                if (isInBounds(nx, ny)) {
                    Cell neighbor = field[ny][nx];
                    if (!neighbor.isOpen()) {
                        if (neighbor.isFlagged()) {
                            neighbor.setFlag(false);
                            flagsCount--;
                        }
                        neighbor.setOpen();
                        updatedCells.add(new CellDto(nx, ny, neighbor.isOpen(), neighbor.hasBomb(), neighbor.isFlagged(), neighbor.getAdjacentBombsCount()));
                        if (neighbor.getAdjacentBombsCount() == 0) {
                            openAdjacent(nx, ny);
                        }
                    }
                }
            }
        }
        if (!updatedCells.isEmpty()) {
            notifyCellUpdateListeners(updatedCells);
        }
    }

    private void checkVictory() {
        boolean allNonBombsOpened = true;
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                Cell cell = field[y][x];
                if (!cell.hasBomb() && !cell.isOpen()) {
                    allNonBombsOpened = false;
                    break;
                }
            }
            if (!allNonBombsOpened) break;
        }
        if (allNonBombsOpened) {
            setGameState(GameState.WON);
        }
    }

    private int countFlagsAround(int x, int y) {
        int count = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int nx = x + dx;
                int ny = y + dy;
                if (isInBounds(nx, ny) && field[ny][nx].isFlagged()) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < field[0].length && y >= 0 && y < field.length;
    }

    private void setGameState(GameState state) {
        this.gameState = state;
        notifyStateChangeListeners(state);
        if (state == GameState.WON || state == GameState.LOST) {
            notifyGameEndedListeners();
        }
    }

    private CellDto[][] createFieldState() {
        CellDto[][] fieldState = new CellDto[field.length][field[0].length];
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                Cell cell = field[y][x];
                fieldState[y][x] = new CellDto(x, y, cell.isOpen(), cell.hasBomb(), cell.isFlagged(), cell.getAdjacentBombsCount());
            }
        }
        return fieldState;
    }

    public int getRemainingMines() {
        return bombCount - flagsCount;
    }

    public GameType getGameType() {
        return gameType;
    }

    public static GameModel create(GameType type) {
        return new GameModel(type, new RandomMinesGenerator());
    }

    public void addCellUpdateListener(CellUpdateEventListener listener) {
        cellUpdateListeners.add(listener);
    }

    public void addGameStateChangeListener(GameStateChangeEventListener listener) {
        stateChangeListeners.add(listener);
    }

    public void addGameEndedListener(GameEndedEventListener listener) {
        gameEndedListeners.add(listener);
    }

    public void addGameResetListener(GameResetEventListener listener) {
        resetListeners.add(listener);
    }

    private void notifyCellUpdateListeners(List<CellDto> cellDtos) {
        for (CellUpdateEventListener listener : cellUpdateListeners) {
            listener.onCellUpdated(cellDtos);
        }
    }

    private void notifyStateChangeListeners(GameState state) {
        for (GameStateChangeEventListener listener : stateChangeListeners) {
            listener.onGameStateChanged(state, gameType);
        }
    }

    private void notifyGameEndedListeners() {
        CellDto[][] fieldState = createFieldState();
        for (GameEndedEventListener listener : gameEndedListeners) {
            listener.onGameEnded(gameState, gameType, fieldState, getRemainingMines());
        }
    }

    private void notifyResetListeners() {
        for (GameResetEventListener listener : resetListeners) {
            listener.onGameReset(gameType, bombCount);
        }
    }
}