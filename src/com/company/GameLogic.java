package com.company;
import java.awt.Color;


public class GameLogic {
    private GameBoard board; //board
    private int boardHeight;    //višina boarda
    private int boardWidth;     //širina boarda

    private static final int[] distantX = {-1, 0, 1};
    private static final int[] distantY = {-1, 0, 1};


    public GameLogic(GameBoard board)
    {
        this.board = board;
        //odštevanje roba od dolžine in širine
        boardHeight = (board.getHeight() - 20) / 20;
        boardWidth = (board.getWidth() - 20) / 20;
    }

    //vrne rezultat preverjanja v polju z določeno lokacijo (x,y)
    private boolean hasKickedBoundary(int x, int y)
    {
        return x < 0 || x >= boardWidth || y < 0 || y >= boardHeight;
    }



    protected void colorSurrounding(int currentX, int currentY, Color targetColor, Color startColor)
    {
        GameField currentObject = (GameField)board.getSquareAt(currentX, currentY);
        try {
            if (hasKickedBoundary(currentX, currentY)) {
                return; // skoči vn iz loopa
            } else if(currentObject.getTraverse()) {
                return;
            }
            else {
                GameField squareObject;
                //Pogleda trenutni objekt
                currentObject.setTraverse(true);


               //preverjanje 8 kvadratov okoli sebe
                for (int x : distantX)
                {
                    for (int y: distantY)
                    {
                        if (hasKickedBoundary(currentX + x, currentY + y)){} // preveri za ¨array out of bounds exception˝, če je ne nardi nč
                        else {
                            squareObject = (GameField)board.getSquareAt(currentX + x, currentY + y);
                            //napiše vseh 8 sosedov in svojo barvo ter ciljno barvo
                            System.out.printf("Checking cell[%d, %d] neighbour [%d,%d] >> color:%s, targetColor: %s\n", currentX, currentY, currentX + x, currentY + y, squareObject.getBackground().toString(), targetColor.toString());
                            //barvanje v vse smeri
                            if(squareObject.getBackground() == targetColor) {
                                squareObject.setBackground(startColor);
                                colorSurrounding (currentX - 1, currentY - 1, targetColor, startColor); // top left.
                                colorSurrounding (currentX    , currentY - 1, targetColor, startColor); // positive.
                                colorSurrounding (currentX + 1, currentY - 1, targetColor, startColor); // top right.
                                colorSurrounding (currentX - 1, currentY    , targetColor, startColor); // positive left.
                                colorSurrounding (currentX + 1, currentY    , targetColor, startColor); // right.
                                colorSurrounding (currentX - 1, currentY + 1, targetColor, startColor); // bottom left.
                                colorSurrounding (currentX    , currentY + 1, targetColor, startColor); // positive down.
                                colorSurrounding (currentX + 1, currentY + 1, targetColor, startColor); // bottom right.
                            }
                            squareObject.setTraverse(true);
                        }
                    }
                }
            }
        } catch(Exception e) {} //ulovi napako

    }
}
