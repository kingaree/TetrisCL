package com.alexr.game;

import java.awt.*;

public class MainTile {
    private Handler handler;
    private int x, y, dimension = 40;
    private int value = 0;
    private Color color = Color.black;

    public MainTile(Handler handler,int x, int y){
        this.x = x;
        this.y = y;
        this.handler = handler;
        dimension = 40;
    }

    public MainTile(Handler handler, int x, int y, int dimension){
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.dimension = dimension;
    }

    public void tick(){
        if(value == 0) {
            color = null;
        }else {
            switch (value){
                case 1:
                    color = Color.red;
                    break;
                case 2:
                    color = Color.green;
                    break;
                case 3:
                    color = Color.blue;
                    break;
                case 4:
                    color = Color.CYAN;
                    break;
                case 5:
                    color = Color.magenta;
                    break;
                case 6:
                    color = Color.white;
                    break;
            }
        }
    }

    public void render(Graphics2D g2d){
        g2d.setColor(color);
        g2d.fillRect(x, y, dimension, dimension);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x , y, dimension, dimension);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
