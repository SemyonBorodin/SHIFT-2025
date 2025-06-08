package ru.cft.javaLessons.miner.app;

import ru.cft.javaLessons.miner.controller.GameController;
import ru.cft.javaLessons.miner.model.*;
import ru.cft.javaLessons.miner.record.RecordsManager;
import ru.cft.javaLessons.miner.utils.GameTimer;
import ru.cft.javaLessons.miner.view.*;

public class Application {
    public static void main(String[] args) {
        RecordsManager recordsManager = new RecordsManager();
        MainWindow mainWindow = new MainWindow();
        FieldGenerator generator = new RandomMinesGenerator();
        GameModel model = GameModel.create(GameType.NOVICE);
        GameTimer timer = new GameTimer();
        GameController controller = new GameController(model);

        setupListeners(mainWindow, model, timer, recordsManager, controller);

        mainWindow.setNewGameMenuAction(e -> mainWindow.requestNewGame());
        mainWindow.setOnNewGameRequested(controller::onNewGameRequested);
        mainWindow.setSettingsMenuAction(e -> {
            SettingsWindow settings = new SettingsWindow(mainWindow.getFrame());
            settings.setGameTypeListener(controller);
            settings.setVisible(true);
        });
        mainWindow.setOnExitRequested(() -> System.exit(0));
        mainWindow.setViewEventListener(controller);
        mainWindow.setHighScoresMenuAction(e -> {
            HighScoresWindow window = new HighScoresWindow(mainWindow.getFrame());
            recordsManager.addRecordsUpdatedEventListener((novice, medium, expert) -> {
                if (novice != null) {
                    window.setNoviceRecord(novice.getName(), novice.getTime());
                }
                if (medium != null) {
                    window.setMediumRecord(medium.getName(), medium.getTime());
                }
                if (expert != null) {
                    window.setExpertRecord(expert.getName(), expert.getTime());
                }
            });
            recordsManager.notifyRecordsUpdatedListeners();
            window.showDialog();
        });
        mainWindow.setExitMenuAction(e -> System.exit(0));

        mainWindow.setOnRecordNameEntered(name -> {
            recordsManager.saveNewRecord(model.getGameType(), name, timer.getSecondsElapsed());
        });

        mainWindow.getFrame().setVisible(true);
    }

    private static void setupListeners(MainWindow mainWindow, GameModel model, GameTimer timer, RecordsManager recordsManager, GameController controller) {
        model.addCellUpdateListener(cellDtos -> {
            for (CellDto dto : cellDtos) {
                mainWindow.updateCell(dto.x(), dto.y(), dto);
            }
            mainWindow.updateBombsCounter(model.getRemainingMines());
        });

        model.addGameResetListener((gameType, bombCount) -> {
            mainWindow.resetGameView(gameType);
            mainWindow.setBombsCount(bombCount);
            mainWindow.setTimerValue(0);
        });
        model.addGameStateChangeListener((state, gameType) -> {
            switch (state) {
                case GAME_IN_PROGRESS -> timer.start();
                case WON, LOST -> timer.stop();
                case GAME_NOT_STARTED -> {
                    mainWindow.resetField();
                    timer.reset();
                }
            }
        });
        model.addGameEndedListener((state, gameType, fieldState, remainingMines) -> {
            mainWindow.blockField();
            mainWindow.updateField(fieldState);
            if (state == GameState.WON) {
                boolean isNewRecord = recordsManager.isNewRecord(gameType, timer.getSecondsElapsed());
                mainWindow.showWinDialog(isNewRecord);
            } else if (state == GameState.LOST) {
                mainWindow.showLoseDialog();
            }
        });
        timer.addTimerTickListener(mainWindow::setTimerValue);
    }
}