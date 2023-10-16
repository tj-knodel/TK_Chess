package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

public class ChessTileUI extends JLayeredPane {
    
    public static enum PIECES_ENUM {
        Pawn, Rook, Knight, Bishop, Queen, King, None
    }

    public char row;
    public char column;

    private static final int TILE_SIZE = 106;
    private JLabel PossibleMoveCircle;
    private JPanel PreviousMoveSquare;
    private JLabel PieceImage;
    
    // -----------------------------------------------------
    // -----------------------------------------------------

    private void assignImage(String imageString) {
        if (imageString.equals("")) {
            PieceImage.setVisible(false);
            return;
        }

        ImageIcon imageToDisplay  = getBoardImage(imageString);

        int maxSize_X = (int)(TILE_SIZE * 0.8); // Square size (106) * 80%
         int maxSize_Y = (int)(TILE_SIZE * 0.8); // Square size (106) * 80%

        // Following Sizing is copy-paste from ImageViewer
        int original_X_size = imageToDisplay.getIconWidth();
        int original_Y_size = imageToDisplay.getIconHeight();
        int newHeight = original_Y_size;
        int newWidth = original_X_size;

        // Maintain Aspect Ratio, Following 2 if statements taken from
        // "https://stackoverflow.com/questions/10245220/"
        if (original_X_size > maxSize_X) {
            newWidth = maxSize_X;
            newHeight = (newWidth * original_Y_size) / original_X_size;
        }
        if (newHeight > maxSize_Y) {
            newHeight = maxSize_Y;
            newWidth = (newHeight * original_X_size) / original_Y_size;
        }

        int xPosition = (TILE_SIZE / 2) - (newWidth / 2);
        int yPosition = TILE_SIZE - 8 - newHeight;

        PieceImage.setVisible(true);
        PieceImage.setIcon(new ImageIcon(getBoardImage(imageString).getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT)));
        PieceImage.setBounds(xPosition, yPosition, newWidth, newHeight);

    }

    public void setPieceImage(PIECES_ENUM piece, boolean isWhite) { // TODO, redo parameters to align with board class
        
            // Many `if` statements to display the correct image
            if (piece == PIECES_ENUM.Pawn) {
                if (isWhite) assignImage("pawn_white.png");
                else assignImage("pawn_black.png");
            } else  if (piece == PIECES_ENUM.Rook) {
                if (isWhite) assignImage("rook_white.png");
                else assignImage("rook_black.png");
            } else  if (piece == PIECES_ENUM.Knight) {
                if (isWhite) assignImage("knight_white.png");
                else assignImage("knight_black.png");
            } else  if (piece == PIECES_ENUM.Bishop) {
                if (isWhite) assignImage("bishop_white.png");
                else assignImage("bishop_black.png");
            } else  if (piece == PIECES_ENUM.Queen) {
                if (isWhite) assignImage("queen_white.png");
                else assignImage("queen_black.png");
            } else  if (piece == PIECES_ENUM.King) {
                if (isWhite) assignImage("king_white.png");
                else assignImage("king_black.png");
            } else {
                assignImage("");
            }

    }

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
        boardSquare.setSize(TILE_SIZE  ,TILE_SIZE); // Numbers from Figma Design
         if (displayWhite) 
             boardSquare.setIcon(new ImageIcon(getBoardImage("square_white.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)));
         else
            boardSquare.setIcon(new ImageIcon(getBoardImage("square_black.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT)));
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
        this.add(PreviousMoveSquare, Integer.valueOf(2));

        PieceImage = new JLabel();
        this.add(PieceImage, Integer.valueOf(4));

    }

    // -----------------------------------------------------
    // -----------------------------------------------------
    private void detectTileClicks() {
        this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    GUI_Events.chessTileWasClicked(row, column);
                }
            });
        }
    // -----------------------------------------------------
    // -----------------------------------------------------

    public ChessTileUI(char row, char column, boolean displayWhite) {
        this.row = row;
        this.column = column;
        createTile( displayWhite);
        detectTileClicks();
    }
}
