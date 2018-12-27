package com.alexr.game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyListener;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class GameScreen extends JPanel{

    private Handler handler;
    private KeyManager keyManager;

    public GameScreen(Handler handler){
        this.handler = handler;

        JFrame jFrame = new JFrame();
        jFrame.setTitle("Tetris");
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setPreferredSize(new Dimension(1000, 800));

        this.setPreferredSize(new Dimension(1000, 800));
        this.setMaximumSize(new Dimension(1000, 800));
        this.setMinimumSize(new Dimension(1000, 800));

        jFrame.addKeyListener(keyManager = new KeyManager());

        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        this.revalidate();

        jFrame.add(this);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(handler.getTetris().getGameScreen() != null) {
            handler.getTetris().getMainWindow().render(g2d);
        }
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }
}
