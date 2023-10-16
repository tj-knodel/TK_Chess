package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

public class ChessTileUI extends JLayeredPane {
    
    public char row;
    public char column;

    private JLabel PossibleMoveCircle;
    private JPanel PreviousMoveSquare;
    
    // -----------------------------------------------------
    // -----------------------------------------------------

    public void setPieceImage() {}

    // -----------------------------------------------------
    // -----------------------------------------------------

    public void setPossibleMoveCircleVisibility(boolean visibility) {
        PossibleMoveCircle.setVisible(visibility);
    }
    public void setPreviousMoveSquareVisibility(boolean visibility) {
        PreviousMoveSquare.setVisible(visibility);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
         * Gets the image from the local jar File
         * @param imageToGet Name of the image + type
         * @return The image
    */
    private  ImageIcon getBoardImage(String imageToGet) {
        return new ImageIcon(getClass().getClassLoader().getResource("BoardImages_Clash/" + imageToGet));
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    private void createTile(boolean displayWhite) {
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0, 0));
        
        // Background
        JLabel boardSquare = new JLabel();
        boardSquare.setSize(106,106); // Numbers from Figma Design
         if (displayWhite) 
             boardSquare.setIcon(new ImageIcon(getBoardImage("square_white.png").getImage().getScaledInstance(106, 106, Image.SCALE_DEFAULT)));
         else
            boardSquare.setIcon(new ImageIcon(getBoardImage("square_black.png").getImage().getScaledInstance(106, 106, Image.SCALE_DEFAULT)));
        this.add(boardSquare, Integer.valueOf(1));
        

        // Foreground
        PossibleMoveCircle = new JLabel();
        PossibleMoveCircle.setBounds(33, 33, 40, 40);
        PossibleMoveCircle.setIcon(new ImageIcon(getBoardImage("PossibleMoveCircle.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        PossibleMoveCircle.setVisible(false);
        this.add(PossibleMoveCircle, Integer.valueOf(5));

        PreviousMoveSquare = new JPanel();
        PreviousMoveSquare.setBounds(0,0, 105,105); // This cant be 106x106 or else java doesn't render whats underneath
        PreviousMoveSquare.setOpaque(true);
        PreviousMoveSquare.setBackground(UILibrary.ForegroundTileColor);
        PreviousMoveSquare.setVisible(true);
        this.add(PreviousMoveSquare, Integer.valueOf(4));

        }

    // -----------------------------------------------------
    // -----------------------------------------------------

    public ChessTileUI(char row, char column, boolean displayWhite) {
        this.row = row;
        this.column = column;
        createTile( displayWhite);

    }
}
