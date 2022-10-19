package com.company;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;


public class Menu extends JFrame implements ActionListener{
    private JButton start, save_game, load_game;
    private JTextField width, height, colors;
    private GameBoard curGame;

    public Menu(String title)
    {
        //nastavitve menuja (barve širina višina)
        setTitle(title);

        save_game = new JButton("Save Game");
        save_game.setBounds(60,30,140,30);
        add(save_game);

        load_game = new JButton("Load Game");
        load_game.setBounds(60,80,140,30);
        add(load_game);

        JLabel widthLabel = new JLabel("Width (2-50):");
        widthLabel.setBounds(60,230,80,20);
        add(widthLabel);

        width = new JTextField();
        width.setBounds(160,230,40,20);
        add(width);

        JLabel heightLabel = new JLabel("Height (2-50):");
        heightLabel.setBounds(60,250,90,20);
        add(heightLabel);

        height = new JTextField();
        height.setBounds(160,250,40,20);
        add(height);

        JLabel mineLabel = new JLabel("Colors (2 - 7):");
        mineLabel.setBounds(60,270,90,20);
        add(mineLabel);

        colors = new JTextField();
        colors.setBounds(160,270,40,20);
        add(colors);

        //Gumb za start igrce
        start = new JButton("New Game");
        start.setBounds(60,310,140,30);
        add(start);

        //Dodajanje listnerjev za vsak gumb
        save_game.addActionListener(this);
        load_game.addActionListener(this);
        start.addActionListener(this);

        //nastavitve menuja
        setSize(280,400);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

  //implementiranje action listnerjev
    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == start) {
            int boardWidth = 0;
            int boardHeight = 0;
            int num_of_colors = 0;
            boolean errorFlag = false;

            if(!checkValid(width.getText(), height.getText(), colors.getText()))
            {
                //če daš d je use 0 vrže error
                errorFlag = true;
                JOptionPane.showMessageDialog(null, "Please enter correct numbers!");

            } else {
                boardWidth = Integer.parseInt(width.getText());
                boardHeight = Integer.parseInt(height.getText());
                num_of_colors = Integer.parseInt(colors.getText());
            }


            if(!errorFlag)
            {
                //če klikneš 2x new game ti prejšnjo zapre in odpre novo, samo ena igra na enkrat
                if(curGame != null) {
                    curGame.dispose();
                }

                curGame = new GameBoard("Color Game", boardWidth, boardHeight, num_of_colors);
                curGame.Randomize();
            }

        } else if(e.getSource() == save_game) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                try {
                    // Shranjevanje objekta v mapi
                    FileOutputStream file = new FileOutputStream(fileToSave);
                    ObjectOutputStream out = new ObjectOutputStream(file);
                    // Metoda serializacije objekta
                    out.writeObject(curGame);

                    out.close();
                    file.close();
                } catch (IOException ex) {
                    System.out.println("IOException is caught");
                }
            }
        } else if(e.getSource() == load_game) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load game...");
            int userSelection = fileChooser.showOpenDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                System.out.println("Open file: " + fileToLoad.getAbsolutePath());
                try {
                    FileInputStream file = new FileInputStream(fileToLoad);
                    ObjectInputStream in = new ObjectInputStream(file);

                    // Metoda deserializiranje
                    if(curGame != null) {
                        // če je igra že odprta jo najprej zbriši
                        curGame.dispose();
                    }
                   curGame = (GameBoard)in.readObject();//preberi
                    curGame.setVisible(true);             //pokaži

                    in.close();
                    file.close();
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("IOException is caught");
                }
            }
        }
    }

    //preveri če je vnos igralca pravilen in vrne rezultat
    private boolean checkValid(String bWidth, String bHeight, String colors)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (bWidth == null || bHeight== null || colors == null)
            return false;
        else if (bWidth.isEmpty() || bHeight.isEmpty() || colors.isEmpty())
            return false;
        else if (!pattern.matcher(bWidth).matches() || !pattern.matcher(bHeight).matches() || !pattern.matcher(colors).matches())
            return false;
        else if (Integer.parseInt(bWidth) < 1 || Integer.parseInt(bWidth) > 50 || Integer.parseInt(bHeight) < 1 || Integer.parseInt(bHeight) > 50
                || Integer.parseInt(colors) < 2 || Integer.parseInt(colors) > 7)
            return false;
        else
            return Integer.parseInt(bWidth) * Integer.parseInt(bHeight) >= Integer.parseInt(colors);
    }
}
