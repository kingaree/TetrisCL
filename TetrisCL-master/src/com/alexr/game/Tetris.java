package com.alexr.game;

import com.alexr.game.GameScreen;
import com.alexr.game.Handler;

import javax.swing.*;

public class Tetris implements Runnable{
    private Handler handler;
    private GameScreen gameScreen;
    private MainWindow mainWindow;
    private Thread thread;
    private boolean running = true;

    public Tetris() {
        init();
    }

    public void init() {
        handler = new Handler(this);
        mainWindow = new MainWindow(handler, 300, 50, 400, 640);
        gameScreen = new GameScreen(handler);



    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
        handler.setTetris(this);
    }

    public synchronized void stop(){
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        long lastTime = System.nanoTime();
        double frameLimit = 60.0;
        double ns = 1000000000 / frameLimit;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running){
            long now = System.nanoTime();

            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1){
                tick();
                if(gameScreen != null) {
                    gameScreen.repaint();
                }
                delta--;
                frames++;
            }

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                // System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public void tick(){
        mainWindow.tick();
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
}

