package ru.cft.javaLessons.miner.view;

import ru.cft.javaLessons.miner.model.*;
import ru.cft.javaLessons.miner.utils.CellImageResolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow implements View {
    private final JFrame frame;
    private final Container contentPane;
    private final GridBagLayout mainLayout;
    private JMenuItem newGameMenu;
    private JMenuItem highScoresMenu;
    private JMenuItem settingsMenu;
    private JMenuItem exitMenu;
    private Runnable onNewGameRequested;
    private Runnable onExitRequested;
    private java.util.function.Consumer<String> onRecordNameEntered;
    private ViewEventListener viewListener;
    private JButton[][] cellButtons;
    private JLabel timerLabel;
    private JLabel bombsCounterLabel;

    public MainWindow() {
        frame = new JFrame("Miner");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(false);
        createMenu();

        contentPane = frame.getContentPane();
        mainLayout = new GridBagLayout();
        contentPane.setLayout(mainLayout);

        contentPane.setBackground(new Color(144, 158, 184));
        createGameField(GameType.NOVICE.getHeight(), GameType.NOVICE.getWidth());
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        gameMenu.add(newGameMenu = new JMenuItem("New Game"));
        newGameMenu.addActionListener(e -> {
            if (viewListener != null) viewListener.onNewGameRequested();
        });

        gameMenu.addSeparator();

        gameMenu.add(highScoresMenu = new JMenuItem("High Scores"));
        gameMenu.add(settingsMenu = new JMenuItem("Settings"));

        gameMenu.addSeparator();

        gameMenu.add(exitMenu = new JMenuItem("Exit"));
        exitMenu.addActionListener(e -> onExitRequested());


        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
    }

    public void onExitRequested() {
        frame.dispose();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setNewGameMenuAction(ActionListener listener) {
        newGameMenu.addActionListener(listener);
    }

    public void setHighScoresMenuAction(ActionListener listener) {
        highScoresMenu.addActionListener(listener);
    }

    public void setSettingsMenuAction(ActionListener listener) {
        settingsMenu.addActionListener(listener);
    }

    public void setExitMenuAction(ActionListener listener) {
        exitMenu.addActionListener(listener);
    }

    public void setCellImage(int x, int y, GameImage gameImage) {
        cellButtons[y][x].setIcon(gameImage.getImageIcon());
        cellButtons[y][x].revalidate();
        cellButtons[y][x].repaint();
    }

    public void setBombsCount(int bombsCount) {
        if (bombsCounterLabel != null) {
            bombsCounterLabel.setText(String.valueOf(bombsCount));
            bombsCounterLabel.revalidate();
            bombsCounterLabel.repaint();
        }
    }

    public void setTimerValue(int value) {
        if (timerLabel != null) {
            timerLabel.setText(String.valueOf(value));
        }
    }

    public void createGameField(int rowsCount, int colsCount) {
        contentPane.removeAll();
        addButtonsPanel(createButtonsPanel(rowsCount, colsCount));
        addTimerImage();
        addTimerLabel(timerLabel = new JLabel("0"));
        addBombCounter(bombsCounterLabel = new JLabel("0"));
        addBombCounterImage();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createButtonsPanel(int numberOfRows, int numberOfCols) {
        cellButtons = new JButton[numberOfRows][numberOfCols];
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(20 * numberOfCols, 20 * numberOfRows));
        buttonsPanel.setLayout(new GridLayout(numberOfRows, numberOfCols, 0, 0));

        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfCols; col++) {
                final int x = col;
                final int y = row;

                cellButtons[y][x] = new JButton(GameImage.CLOSED.getImageIcon());
                cellButtons[y][x].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (viewListener == null) return;
                        switch (e.getButton()) {
                            case MouseEvent.BUTTON1 -> viewListener.onCellClicked(x, y, ButtonType.LEFT_BUTTON);
                            case MouseEvent.BUTTON3 -> viewListener.onCellClicked(x, y, ButtonType.RIGHT_BUTTON);
                            case MouseEvent.BUTTON2 -> viewListener.onCellClicked(x, y, ButtonType.MIDDLE_BUTTON);
                        }
                    }
                });
                buttonsPanel.add(cellButtons[y][x]);
            }
        }

        return buttonsPanel;
    }

    private void addButtonsPanel(JPanel buttonsPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.insets = new Insets(20, 20, 5, 20);
        mainLayout.setConstraints(buttonsPanel, gbc);
        contentPane.add(buttonsPanel);
    }

    private void addTimerImage() {
        JLabel label = new JLabel(GameImage.TIMER.getImageIcon());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        mainLayout.setConstraints(label, gbc);
        contentPane.add(label);
    }

    private void addTimerLabel(JLabel timerLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 0, 0);
        mainLayout.setConstraints(timerLabel, gbc);
        contentPane.add(timerLabel);
    }

    private void addBombCounter(JLabel bombsCounterLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0.7;
        mainLayout.setConstraints(bombsCounterLabel, gbc);
        contentPane.add(bombsCounterLabel);
    }

    private void addBombCounterImage() {
        JLabel label = new JLabel(GameImage.BOMB_ICON.getImageIcon());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 3;
        gbc.insets = new Insets(0, 5, 0, 20);
        gbc.weightx = 0.1;
        mainLayout.setConstraints(label, gbc);
        contentPane.add(label);
    }

    public void resetField() {
        if (cellButtons != null) {
            for (JButton[] row : cellButtons) {
                for (JButton button : row) {
                    button.setIcon(GameImage.CLOSED.getImageIcon());
                    button.setEnabled(true);
                }
            }
        }
    }

    public void updateBombsCounter(int count) {
        if (bombsCounterLabel != null) {
            bombsCounterLabel.setText(String.valueOf(count));
        }
    }

    public void requestNewGame() {
        if (onNewGameRequested != null) {
            onNewGameRequested.run();
        }
    }

    private void showSimpleWinWindow() {
        WinWindow winWindow = new WinWindow(frame);
        winWindow.setNewGameListener(e -> {
            if (onNewGameRequested != null) onNewGameRequested.run();
            winWindow.getDialog().dispose();
        });
        winWindow.setExitListener(e -> {
            if (onExitRequested != null) onExitRequested.run();
        });
        winWindow.showDialog();
    }

    public void setOnNewGameRequested(Runnable listener) {
        this.onNewGameRequested = listener;
    }

    public void setOnExitRequested(Runnable onExitRequested) {
        this.onExitRequested = onExitRequested;
    }

    public void setOnRecordNameEntered(java.util.function.Consumer<String> listener) {
        this.onRecordNameEntered = listener;
    }

    @Override
    public void updateCell(int x, int y, CellDto cellDto) {
        GameImage image = CellImageResolver.resolve(cellDto);
        setCellImage(x, y, image);
    }

    @Override
    public void resetGameView(GameType type) {
        resetField();
        createGameField(type.getHeight(), type.getWidth());
        setBombsCount(type.getBombs());
        setTimerValue(0);
        updateBombsCounter(0);
        getFrame().pack();
    }

    @Override
    public void blockField() {
        for (JButton[] cellButton : cellButtons) {
            for (JButton jButton : cellButton) {
                jButton.setEnabled(false);
            }
        }
    }

    @Override
    public void showWinDialog(boolean isNewRecord) {
        if (isNewRecord) {
            RecordsWindow recordsWindow = new RecordsWindow(frame);
            recordsWindow.setNameListener(name -> {
                if (onRecordNameEntered != null) {
                    onRecordNameEntered.accept(name);
                }
                recordsWindow.getDialog().dispose();
            });
            recordsWindow.getDialog().setVisible(true);
        } else {
            showSimpleWinWindow();
        }
        frame.toFront();
    }

    @Override
    public void showLoseDialog() {
        LoseWindow loseWindow = new LoseWindow(frame);
        loseWindow.setNewGameListener(e -> {
            if (onNewGameRequested != null) onNewGameRequested.run();
            loseWindow.getDialog().dispose();
        });
        loseWindow.setExitListener(e -> {
            if (onExitRequested != null) onExitRequested.run();
        });
        loseWindow.showDialog();
        frame.toFront();
    }

    public void setViewEventListener(ViewEventListener listener) {
        this.viewListener = listener;
    }

    public void updateField(CellDto[][] field) {
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                if (field[y][x].hasBomb()) {
                    setCellImage(x, y, GameImage.BOMB);
                } else {
                    GameImage image = CellImageResolver.resolve(field[y][x]);
                    setCellImage(x, y, image);
                }
            }
        }
    }
}