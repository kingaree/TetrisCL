package com.alexr.game;

import sun.applet.Main;

public class Handler {
    private Tetris tetris;
    private MainWindow mainWindow;
    private GameScreen gameScreen;
    private KeyManager keyManager;

    public Handler(Tetris tetris){
        this.tetris = tetris;
    }

    public Tetris getTetris() {
        return tetris;
    }

    public void setTetris(Tetris tetris) {
        this.tetris = tetris;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }
}
