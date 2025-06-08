package ru.cft.javaLessons.miner.controller;

import ru.cft.javaLessons.miner.model.*;
import ru.cft.javaLessons.miner.view.*;

public class GameController implements GameTypeListener, ViewEventListener {
    private final GameModel model;

    public GameController(GameModel model) {
        if (model == null) {
            throw new IllegalArgumentException("GameModel is null btw");
        }
        this.model = model;
        startNewGame();
    }

    public void startNewGame() {
        model.reset();
    }


    @Override
    public void onCellClicked(int x, int y, ButtonType buttonType) {
        CellAction action = switch (buttonType) {
            case LEFT_BUTTON -> CellAction.OPEN;
            case RIGHT_BUTTON -> CellAction.FLAG;
            case MIDDLE_BUTTON -> CellAction.MIDDLE_BUTTON;
        };
        model.processAction(x, y, action);
    }

    @Override
    public void onGameTypeChanged(GameType gameType) {
        model.reset(gameType);
    }

    @Override
    public void onNewGameRequested() {
        model.reset();
    }
}
