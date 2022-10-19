package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JFrame implements ActionListener, java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    private JPanel boardPanel = new JPanel();

    private int boardHeight;
    private int boardWidth;
    private int numColors;
    private GameSquare[][] board;
    private final Color defaultColor = Color.WHITE;
    private final Color colorChoices[] = {Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.CYAN, Color.magenta, Color.YELLOW, Color.pink};

    private GameField startClick;
    private GameField targetClick;
    private int timesClicked;

    public GameBoard(String title, int width, int height, int numColors)
    {
        super();

        this.boardWidth = width;
        this.boardHeight = height;
        this.numColors = numColors;

        //nardi okno igrice
        this.board = new GameSquare[width][height];

        //nardi novo okno
        setTitle(title);
        setSize(50 + width * 50,50 + height * 50);
        setContentPane(boardPanel);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardPanel.setLayout(new GridLayout(height, width));

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                board[x][y] = new GameField(x, y, this);
                board[x][y].setBackground(defaultColor);
                //board[x][y].setFont(new Font("Arial", Font.PLAIN, 12));
                //board[x][y].setText(x + "," + y);
                board[x][y].addActionListener(this);

                boardPanel.add(board[x][y]);
            }
        }

        timesClicked = 0;
        startClick = null;
        targetClick = null;

        setVisible(true);//okno je vidno
    }

    //način barvanja
    public GameField getStartClick() { return this.startClick; }
    public void setStartClick(GameField startClick) { this.startClick = startClick; }
    public GameField getTargetClick() { return this.targetClick; }
    public void setTargetClick(GameField targetClick) { this.targetClick = targetClick; }
    public int getTimesClicked() { return this.timesClicked; }
    public void incTimesClicked() { this.timesClicked++; }

    //random postavitev barv
    public void Randomize() {
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                int newRandom = (int)(Math.random() * numColors);
                Color newRandomColor = colorChoices[newRandom];
                board[x][y].setBackground(newRandomColor);
            }
        }
    }


    public GameSquare getSquareAt(int x, int y)
    {
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
            return null;

        return board[x][y];
    }

    //preverjanje če se je igra končala
    public void actionPerformed(ActionEvent e)
    {
        GameSquare b = (GameSquare)e.getSource();
        b.clicked();
        boolean gameFinished = true;
        Color color00 = null;
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                if(color00 != null) {
                    if(board[y][x].getBackground() != color00) {
                        gameFinished = false;
                    }
                } else {
                    // se zgodi samo prvic, zato je v else
                    color00 = board[y][x].getBackground();
                    continue;
                }
            }
        }
        if(gameFinished) {
            System.out.println("GAME FINISHED");
        }
    }

    //nastavi barve na neprevirjene da ob novem kliku spet preveri
    public void setFildsToUnchecked() {
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                ((GameField)(board[y][x])).setTraverse(false);
            }
        }
    }

}