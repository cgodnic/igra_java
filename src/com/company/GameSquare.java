package com.company;

import javax.swing.*;

public abstract class GameSquare extends JButton {
   //x,y lokacija
    protected int xLocation;

    protected int yLocation;

    //okno igre
    protected GameBoard board;

    //ustvari objekt ki bo postavljen v okno igre
    public GameSquare(int x, int y, GameBoard board) {
        this.board = board;
        xLocation = x;
        yLocation = y;
    }

    public abstract void clicked();
}
