package com.alexr.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow {

    private Handler handler;
    private int posX, posY, width, height, shapeL, activeBlockCount, potentialCount = 0;
    private MainTile[][] landed = new MainTile[18][10], unLanded = new MainTile[18][10];
    private Tetromino tetromino, oldTetromino;
    private ArrayList<Tetromino> list = new ArrayList<Tetromino>();
    private long lastUpdateTimer, delay = 1000, timer = delay;
    private BufferedImage img;

    public MainWindow(Handler handler, int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.handler = handler;
        this.width = width;
        this.height = height;

        init();
    }

    public void init() {
        int x = 0;
        int y = 0;

        img = null;

        try
        {
            img = ImageIO.read(new File("C:\\Users\\Alex\\Downloads\\TetrisCL-master\\TetrisCL-master\\src\\com\\alexr\\game\\res\\bg.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < landed.length; i++) {
            for (int o = 0; o < landed[i].length; o++) {
                landed[i][o] = new MainTile(handler, x + posX, y + posY);
                unLanded[i][o] = new MainTile(handler, x + posX, y + posY);
                x += 40;
            }
            y += 40;
            x = 0;
        }

        spawnTetromino();

    }

    public void spawnTetromino() {
        list.add(new Tetromino());
        tetromino = list.get(list.size() - 1);
        shapeL = tetromino.getShape().length -1;
    }

    public void tick() {
        for (int i = 0; i < landed.length; i++) {
            for (int o = 0; o < landed[i].length; o++) {
                if(i <= 15) {
                    landed[i][o].tick();
                    unLanded[i][o].tick();
                }
            }

        }

        if(tetromino.isLanded()){
            spawnTetromino();
        }

        handleRotation();
        moveTetrominoDown();
        moveTetromino();
        drawUnlanded(tetromino);


    }

    public void render(Graphics2D g2d){
        g2d.drawImage(img, 340, 51, 400, 640, null);

        for (int i = 0; i < landed.length; i++) {
            for (int o = 0; o < landed[i].length; o++) {
                if(i <= 15) {
                    if(landed[i][o].getValue() != 0) {
                        landed[i][o].render(g2d);
                    }
                    if(unLanded[i][o].getValue() != 0) {
                        unLanded[i][o].render(g2d);
                    }
                }
            }
        }
    }


    public void drawUnlanded(Tetromino tetromino){
            for (int row = 0; row < tetromino.getShape().length; row++) {
                for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                    if (tetromino.shape[row][col] != 0) {
                        unLanded[row + tetromino.offset[0]][col + tetromino.offset[1]].setValue(tetromino.getShape()[row][col]);
                    }
                }
        }
    }


    public void moveTetrominoDown() {
        lastUpdateTimer = System.currentTimeMillis();
        if (lastUpdateTimer < timer + delay) {
            return;
        }else{


            if(!tetromino.isLanded()) {
                wipeUnlanded(tetromino);
                tetromino = oldTetromino;
                tetromino.offset[0]++;
                delay = lastUpdateTimer;
            }

            for(int row = 0; row < tetromino.getShape().length; row++) {
                for (int col = 0; col < tetromino.shape[row].length; col++) {
                    if(tetromino.offset[0] + tetromino.shape.length <= 17) {
                        if(tetromino.shape[row][col] != 0) {
                            if (landed[row + tetromino.offset[0] + 1][col + tetromino.offset[1]].getValue() != 0) {
                                setLanded();
                            }
                        }
                    }else{
                        setLanded();
                        }
                    }
                }
            }
    }

    public void moveTetromino(){
        //move right
        if(handler.getTetris().getGameScreen().getKeyManager().isRight()){
            handler.getTetris().getGameScreen().getKeyManager().setRight(false);
            for(int row = 0; row < tetromino.shape.length; row++){
                for(int col = 0; col < tetromino.shape[row].length; col++) {
                    if (tetromino.shape[row].length + tetromino.offset[1] <= 9){//simply check if shape is off screen, doesn't matter if the tile is 0 as it still cant go beyond 9
                        if (landed[row + tetromino.offset[0] + 1][col + tetromino.offset[1]].getValue() != 0) {//check landed pos + 1 x
                           return;
                        }
                    }else{
                        //if new x position is bigger than 9 stop
                        return;
                    }
                }
                return;
            }
            //if code gets to here then tetromino passes all collision checks
            if(!tetromino.isLanded()) {
                wipeUnlanded(tetromino);
                tetromino.offset[1]++;
            }
        }

        //move left
        if(handler.getTetris().getGameScreen().getKeyManager().isLeft()){
            handler.getTetris().getGameScreen().getKeyManager().setLeft(false);
            for(int row = 0; row < tetromino.shape.length; row++){
                for(int col = 0; col < tetromino.shape[row].length; col++) {
                    if (tetromino.offset[1] -1 >= 0) {
                        if (landed[row + tetromino.offset[0]][col + tetromino.offset[1] -1].getValue() != 0) {
                            return;
                        }
                    }else{
                        return;
                    }
                }
            }
            //if code gets to here then tetromino passes all collision checks
            wipeUnlanded(tetromino);
            tetromino.offset[1]--;
        }

        //move down
        if(handler.getTetris().getGameScreen().getKeyManager().isDown()) {
            handler.getTetris().getGameScreen().getKeyManager().setDown(false);
            for(int row = 0; row < tetromino.getShape().length; row++) {
                for (int col = 0; col < tetromino.shape[row].length; col++) {
                        if (tetromino.shape[row][col] != 0 && tetromino.offset[0] + tetromino.getShape().length <= 17) {
                            if (landed[row + tetromino.offset[0] + 1][col + tetromino.offset[1]].getValue() != 0) {
                                setLanded();
                            }
                        }
                }
            }
            if(!tetromino.isLanded()) {
                wipeUnlanded(tetromino);
                tetromino.offset[0]++;
                delay = lastUpdateTimer;
            }
        }

    }

    public void wipeUnlanded(Tetromino tetromino){
        oldTetromino = tetromino;
        for(int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if(unLanded[row + tetromino.offset[0]][col + tetromino.offset[1]].getValue() != 0){
                    unLanded[row + tetromino.offset[0]][col + tetromino.offset[1]].setValue(0);
                }
            }
        }
    }

    public void setLanded(){
        System.out.println("landed");
        tetromino.setLanded(true);
        for(int row = 0; row < tetromino.getShape().length; row++) {
            for (int col = 0; col < tetromino.getShape()[row].length; col++) {
                if(tetromino.shape[row][col] != 0) {
                    landed[row + tetromino.offset[0]][col + tetromino.offset[1]].setValue(tetromino.shape[row][col]);
                }
            }
        }
    }

    public void handleRotation(){

    }


    public Tetromino getTetromino() {
        return tetromino;
    }

}
