package com.alexr.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Tetromino {

    public int offset[];
    private boolean landed = false, moved = false;
    private List<int[][]> list;
    private int initialYOffset = 0, initialXOffset = 4, cNum;//colour number
    public int[][] shape = new int[][]{};
    private int[][] square, line, leftL, rightL, zLeft, zRight, t;

    public Tetromino()
    {
        create();
    }

    public void create(){
        setColor();
        setShapes();
        offset = new int[]{initialYOffset, initialXOffset};
        list();
        Random rand = new Random();
        int r = rand.nextInt(list.size());
        shape = list.get(r);

    }

    public void setShapes(){
        square = new int[][]{{cNum ,cNum}
                , {cNum ,cNum}};
        line = new int[][]{{0, 0, 0 , 0}, {cNum, cNum, cNum, cNum}, {0, 0, 0, 0}, {0, 0, 0, 0}};




        leftL = new int[][]{{cNum, 0, 0}, {cNum, cNum, cNum}};
        rightL = new int [][]{{0, 0, cNum}, {cNum, cNum, cNum}};
        zLeft = new int[][]{{cNum, 0}, {cNum, cNum}, {0, cNum}};
        zRight = new int[][]{{0, cNum}, {cNum, cNum}, {cNum, 0}};
        t = new int[][]{{0, cNum, 0}, {cNum, cNum, cNum}};
    }

    public void list(){
        list = new ArrayList();
//        list.add(square);
        list.add(line);
//        list.add(leftL);
//        list.add(rightL);
//        list.add(zLeft);
//        list.add(zRight);
//        list.add(t);

    }

    public void setColor(){
        int i = ThreadLocalRandom.current().nextInt(1, 7);

        cNum = i;

    }


    public void incrementYOffset(){
        initialYOffset = initialYOffset + 1;
    }


    public int[][] getSquare() {
        return square;
    }

    public void setSquare(int[][] square) {
        this.square = square;
    }

    public int[] getOffset() {
        return offset;
    }

    public void setOffset(int[] offset) {
        this.offset = offset;
    }


    public boolean isLanded() {
        return landed;
    }

    public void setLanded(boolean landed) {
        this.landed = landed;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }
}
