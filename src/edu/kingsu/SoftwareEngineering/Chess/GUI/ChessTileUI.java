package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;

import edu.kingsu.SoftwareEngineering.Chess.GUI.ResizeManager.UIImage_Label;


/**
 * Individual ChessTileUI square,
 * Manages elements shown on each square
 */
public class ChessTileUI extends JLayeredPane {

    /**
     * Used to specify pieces to display
     */
    public static enum PIECES_ENUM {
        /**
         * Pawn
         */
        Pawn, 
        /**
         * Rook
         */
        Rook, 
        /**
         * Knight
         */
        Knight, 
        /**
         * Bishop
         */
        Bishop, 
        /**
         * Queen
         */
        Queen, 
        /**
         * King
         */
        King, 
        /**
         * No Piece, Blank Square
         */
        None
    }

    /**
     * Set by the JMenuEvent in the static constructor.
     * Toggles whether possible move circles are shown on the board or not.
     */
    private boolean showPossibleMoves = true;

    /**
     * Set by the JMenuEvent in the static constructor.
     * Toggles whether the last move yellow square will be shown on the board or
     * not.
     */
    private boolean showPreviousMoves = true;

    /**
     * Row of the Tile
     */
    public char row;

    /**
     * Column of the tile
     */
    public char column;

    /**
     * How big the tile is in pixels
     */
    private static int tileSize = 106;

    /**
     * The UI element which displays the tile as a possible move
     */
    private JLabel PossibleMoveCircle;

    /**
     * UI element which displays the tile as a previous move
     */
    private JPanel PreviousMoveSquare;

    /**
     * UI element which holds the piece image
     */
    private JLabel PieceImage;

    /**
     * true if the tile is white
     */
    private boolean isTileWhite;
    
    /**
     * true if the current piece on the tile is white
     */
    private boolean isPieceWhite = true;

    /**
     *  Whatever the current piece is
     */
    private PIECES_ENUM currentPiece = PIECES_ENUM.None;
    
    /**
     * Background tile image
     */
    private JLabel boardSquare ;
    
    /**
     * Used for dynamic image sizing
     * @see ResizeManager
     */
    private UIImage_Label tileLabel;

        /**
     * Used for dynamic image sizing
     * @see ResizeManager
     */
    private UIImage_Label PossibleMoveCircle_Label;

    /**
     * Gets the image from the source chess appearance folder.
     * 
     * @param imageToGet Name of the image + type
     * @return The image
     */
    private ImageIcon getBoardImage(String imageToGet) {
        if (!UILibrary.isAbsoluteFilePath)
            return new ImageIcon(getClass().getClassLoader().getResource(UILibrary.boardAppearanceFolder + imageToGet));
         else 
            return new ImageIcon(UILibrary.boardAppearanceFolder + imageToGet);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Show the specific piece in the tile
     * Resizes and positions the image to fit perfectly within the tile
     * 
     * @param imageString Name of the image
     */
    private void assignImage(String imageString) {
        if (imageString.equals("")) {
            PieceImage.setVisible(false);
            return;
        }

        ImageIcon imageToDisplay = getBoardImage(imageString);

        int maxSize_X = (int) (106 * 0.8); // Square size (106) * 80%
        int maxSize_Y = (int) (106 * 0.8); // Square size (106) * 80%

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

        int xPosition = UILibrary.resizeModule.scale_X((106 / 2) - (newWidth / 2));
        int yPosition = UILibrary.resizeModule.scale_Y(106 - 8 - newHeight);
        newWidth = UILibrary.resizeModule.scale_X(newWidth);
        newHeight = UILibrary.resizeModule.scale_Y(newHeight);

        PieceImage.setVisible(true);
        PieceImage.setIcon(new ImageIcon(
                getBoardImage(imageString).getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT)));
        PieceImage.setBounds(xPosition, yPosition, newWidth, newHeight);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Sets the piece to be displayed on the tile
     * Maybe consider changing parameters later
     * 
     * @param piece   Piece type to be shown,
     * @param isWhite true = white piece, false = black piece
     */
    public void setPieceImage(PIECES_ENUM piece, boolean isWhite) {

        currentPiece = piece;
        isPieceWhite = isWhite;

        // Many `if` statements to display the correct image
        if (piece == PIECES_ENUM.Pawn) {
            if (isWhite)
                assignImage("pawn_white.png");
            else
                assignImage("pawn_black.png");
        } else if (piece == PIECES_ENUM.Rook) {
            if (isWhite)
                assignImage("rook_white.png");
            else
                assignImage("rook_black.png");
        } else if (piece == PIECES_ENUM.Knight) {
            if (isWhite)
                assignImage("knight_white.png");
            else
                assignImage("knight_black.png");
        } else if (piece == PIECES_ENUM.Bishop) {
            if (isWhite)
                assignImage("bishop_white.png");
            else
                assignImage("bishop_black.png");
        } else if (piece == PIECES_ENUM.Queen) {
            if (isWhite)
                assignImage("queen_white.png");
            else
                assignImage("queen_black.png");
        } else if (piece == PIECES_ENUM.King) {
            if (isWhite)
                assignImage("king_white.png");
            else
                assignImage("king_black.png");
        } else {
            assignImage("");
        }

    }

    // -----------------------------------------------------
    // -----------------------------------------------------
    /**
     * Set the visibility of this tile being a possible position to be moved to
     * 
     * @param visibility true = show tile, false = hide tile
     */
    public void setPossibleMoveCircleVisibility(boolean visibility) {
        if (showPossibleMoves)
            PossibleMoveCircle.setVisible(visibility);
    }

    /**
     * Set the visibility of this tile displaying the previous move
     * 
     * @param visibility true = show tile, false = hide tile
     */
    public void setPreviousMoveSquareVisibility(boolean visibility) {
        if (showPreviousMoves)
            PreviousMoveSquare.setVisible(visibility);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates the UI Layers for a tile
     * 
     * @param displayWhite Is it a light color tile or a dark color tile
     */
    private void createTile(boolean displayWhite) {
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0, 0));

        // Background
        boardSquare = new JLabel();
         UILibrary.resizeModule.setVariableBounds(boardSquare, null,  0, 0, tileSize, tileSize); // Numbers from Figma Design
        if (displayWhite)
            tileLabel = UILibrary.resizeModule.setVariableBounds(boardSquare,getBoardImage("square_white.png"));
        else
            tileLabel = UILibrary.resizeModule.setVariableBounds(boardSquare,getBoardImage("square_black.png"));
        this.add(boardSquare, Integer.valueOf(1));

        // Yellow circle which indicates a possible move when selecting a piece.
        // maybe consider switching to a Graphics2D drawing instead of a image
        PossibleMoveCircle = new JLabel();
        UILibrary.resizeModule.setVariableBounds(PossibleMoveCircle, null, 33, 33, 40, 40);
        PossibleMoveCircle_Label = UILibrary.resizeModule.setVariableBounds(PossibleMoveCircle, getBoardImage("PossibleMoveCircle.png"));
        PossibleMoveCircle.setVisible(false);
        this.add(PossibleMoveCircle, Integer.valueOf(5));

        // Yellow Square which indicated previous move
        PreviousMoveSquare = new JPanel();
        UILibrary.resizeModule.setVariableBounds(PreviousMoveSquare, null, 0, 0, tileSize - 1, tileSize - 1); // This cant be 106x106 or else java doesn't render whats underneath
        PreviousMoveSquare.setOpaque(true);
        PreviousMoveSquare.setBackground(UILibrary.ForegroundTileColor);
        PreviousMoveSquare.setVisible(false);
        this.add(PreviousMoveSquare, Integer.valueOf(2));

        // Piece Image
        PieceImage = new JLabel();
        this.add(PieceImage, Integer.valueOf(4));
    }

    // -----------------------------------------------------
    // -----------------------------------------------------
    
    /**
     * Redraws the tile with new images
     */
    public void redrawTile() {
         if (isTileWhite)
            tileLabel.image = getBoardImage("square_white.png");
        else
            tileLabel.image = getBoardImage("square_black.png");
        PossibleMoveCircle_Label.image = getBoardImage("PossibleMoveCircle.png");
        setPieceImage(currentPiece, isPieceWhite);
        this.repaint();
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Constructor for a chess UI Tile
     * 
     * @param row          Which row the tile is in (only used to send data to mouse click function)
     * @param column       Which column the tile is in (only used to send data to mouse click function)
     * @param displayWhite Is it a light color tile or a dark color tile
     */
    public ChessTileUI(char row, char column, boolean displayWhite) {
        this.row = row;
        this.column = column;
        this.isTileWhite = displayWhite;
        createTile(displayWhite);

        // JMenu events
        UILibrary.TogglePossibleMoves_JMenuItem.addActionListener(e -> {
            showPossibleMoves = !showPossibleMoves;
            PossibleMoveCircle.setVisible(false);
        });
        UILibrary.TogglePreviousMoves_JMenuItem.addActionListener(e -> {
            showPreviousMoves = !showPreviousMoves;
            PreviousMoveSquare.setVisible(false);
        });
    }



}