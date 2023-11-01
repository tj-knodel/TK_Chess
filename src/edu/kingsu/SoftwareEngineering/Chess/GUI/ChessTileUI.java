package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;

public class ChessTileUI extends JLayeredPane {

    /**
     * Used to specify pieces to display
     */
    public static enum PIECES_ENUM {
        Pawn, Rook, Knight, Bishop, Queen, King, None
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
    private static final int TILE_SIZE = 106;

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
     * Gets the image from the source chess appearance folder.
     * 
     * @param imageToGet Name of the image + type
     * @return The image
     */
    private ImageIcon getBoardImage(String imageToGet) {
        return new ImageIcon(getClass().getClassLoader().getResource(UILibrary.boardAppearanceFolder + imageToGet));
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Show the specific piece in the tile
     * Resizes and positions the image to fit perfectly within the tile
     * 
     * @param imageString
     */
    private void assignImage(String imageString) {
        if (imageString.equals("")) {
            PieceImage.setVisible(false);
            return;
        }

        ImageIcon imageToDisplay = getBoardImage(imageString);

        int maxSize_X = (int) (TILE_SIZE * 0.8); // Square size (106) * 80%
        int maxSize_Y = (int) (TILE_SIZE * 0.8); // Square size (106) * 80%

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
        JLabel boardSquare = new JLabel();
        boardSquare.setSize(TILE_SIZE, TILE_SIZE); // Numbers from Figma Design
        if (displayWhite)
            boardSquare.setIcon(new ImageIcon(getBoardImage("square_white.png").getImage().getScaledInstance(TILE_SIZE,
                    TILE_SIZE, Image.SCALE_DEFAULT)));
        else
            boardSquare.setIcon(new ImageIcon(getBoardImage("square_black.png").getImage().getScaledInstance(TILE_SIZE,
                    TILE_SIZE, Image.SCALE_DEFAULT)));
        this.add(boardSquare, Integer.valueOf(1));

        // Yellow circle which indicates a possible move when selecting a piece.
        // maybe consider switching to a Graphics2D drawing instead of a image
        PossibleMoveCircle = new JLabel();
        PossibleMoveCircle.setBounds(33, 33, 40, 40);
        PossibleMoveCircle.setIcon(new ImageIcon(
                getBoardImage("PossibleMoveCircle.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        PossibleMoveCircle.setVisible(false);
        this.add(PossibleMoveCircle, Integer.valueOf(5));

        // Yellow Square which indicated previous move
        PreviousMoveSquare = new JPanel();
        PreviousMoveSquare.setBounds(0, 0, 105, 105); // This cant be 106x106 or else java doesn't render whats
                                                      // underneath
        PreviousMoveSquare.setOpaque(true);
        PreviousMoveSquare.setBackground(UILibrary.ForegroundTileColor);
        PreviousMoveSquare.setVisible(true);
        this.add(PreviousMoveSquare, Integer.valueOf(2));

        // Piece Image
        PieceImage = new JLabel();
        this.add(PieceImage, Integer.valueOf(4));
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Detects when the tile is clicked, sends event to GUI_Events
     */
    private void detectTileClicks() { // This click thing works but not super reliable,
        // see Commit Tile Click for Version 2, which has better clicking but visual bug
        // after click
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI_Events.chessTileWasClicked(row, column);
            }
        });
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Constructor for a chess UI Tile
     * 
     * @param row          Which row the tile is in (only used to send data to mouse
     *                     click function)
     * @param column       Which column the tile is in (only used to send data to
     *                     mouse click function)
     * @param displayWhite Is it a light color tile or a dark color tile
     */
    public ChessTileUI(char row, char column, boolean displayWhite) {
        this.row = row;
        this.column = column;
        createTile(displayWhite);
        detectTileClicks();

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