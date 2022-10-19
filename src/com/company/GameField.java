package com.company;
import javax.swing.*;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GameField extends GameSquare implements MouseListener {

    private boolean thisSquareHasTraversed;

    private int xLocation;
    private int yLocation;
    private GameBoard ref2parent;

    //Ustvari se nov razred in ga prilepi v okno igre, x in y sta enaka x in y od okna igre
    public GameField(int x, int y, GameBoard board) {
        //med inicializacijo obarvaj kvadratke v sivo
        super(x, y, board);

        xLocation = x;
        yLocation = y;

        //Inacializijske podrobnosti
        thisSquareHasTraversed = false;

        //Listener za desni klik
        addMouseListener(this);

        ref2parent = board;
    }

    public String toString() {
        String clr = this.getBackground().toString();
        return String.format("coords: [%d, %d], bgColor: %s", xLocation, yLocation, clr);
    }


    protected boolean getTraverse() {
        return thisSquareHasTraversed;
    }

    protected void setTraverse(boolean result) {
        thisSquareHasTraversed = result;
    }

    //Implemetacija abstraktnih metod iz gamesquara

    public void clicked() {
        // lihi kliki so za izbirat barvo sodi za barvat
        if (ref2parent.getTimesClicked() % 2 == 0) {
            ref2parent.setStartClick(this);
            ref2parent.incTimesClicked();
            return;
        }
        else {
            ref2parent.setTargetClick(this);
            ref2parent.incTimesClicked();

            Color previouslyClickedColor = ref2parent.getStartClick().getBackground();
            GameLogic gl = new GameLogic(board);

            // nastavi da so vsi elementi neprevirjeni
            ref2parent.setFildsToUnchecked();
            // mark current target as checked


            // preveri kake barve so kvadrati okoli in če so iste pobarva tudi njih
            // ponavljaj dokler niso vsi preverjeni
            gl.colorSurrounding(xLocation, yLocation, this.getBackground(), previouslyClickedColor);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    //Ostalih listnerjev se ne uporablja
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
