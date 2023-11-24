package edu.kingsu.SoftwareEngineering.Chess.GUI;

import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI.PIECES_ENUM;

import javax.swing.ImageIcon;
/*
 
    ChessTileUI functions:
    enum PIECES_ENUM { Pawn, Rook, Knight, Bishop, Queen, King, None }
    
    void setPieceImage(PIECES_ENUM piece, boolean isWhite) 
    void setPossibleMoveCircleVisibility(boolean visibility)
    void setPreviousMoveSquareVisibility(boolean visibility)
 */

/**
 * Connection script between the Chess UI and game loop / board.
 * 
 * @author Noah Bulas
 * @version V1
 */
public class ChessUIManager {

    // ------------------------------------------------------------------
    // ---------------------Drawing UI Board-----------------------------
    // ------------------------------------------------------------------

    /**
     * Array which holds the ChessTileUI squares
     */
    public ChessTileUI boardTiles[][];

    /**
     * Draws the board with the specific pieces in the certain positions
     * 
     * @param pieces 2d array of Piece Objects
     */
    public void drawBoard(Piece[][] pieces) {

        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {

                Piece pieceObject = pieces[row][column];

                if (pieceObject.getPieceID() == Piece.EMPTY_PIECE)
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.None, false);
                else if (pieceObject.getPieceID() == Piece.PAWN)
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Pawn, pieceObject.getTeam() == Team.WHITE_TEAM);
                else if (pieceObject.getPieceID() == Piece.BISHOP)
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Bishop, pieceObject.getTeam() == Team.WHITE_TEAM);
                else if (pieceObject.getPieceID() == Piece.KNIGHT)
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Knight, pieceObject.getTeam() == Team.WHITE_TEAM);
                else if (pieceObject.getPieceID() == Piece.ROOK)
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Rook, pieceObject.getTeam() == Team.WHITE_TEAM);
                else if (pieceObject.getPieceID() == Piece.KING)
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.King, pieceObject.getTeam() == Team.WHITE_TEAM);
                else if (pieceObject.getPieceID() == Piece.QUEEN)
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Queen, pieceObject.getTeam() == Team.WHITE_TEAM);

            }
        }
        UILibrary.MainFrame.repaint();
    }

    // ---------------------------------------------------
    // ------------Managing Upgrading Pieces--------------
    // --------------------------------------------------
    /**
     * File which holds the create AccessoryUI class
     * Variable cant be static because of how images are retrieved
     */
    private CreateAccessoryUIs accessoryUI; // Variable cant be static because of how images are retrieved

    /**
     * Shows the upgrade frame for when a player is upgrading a piece
     * 
     * @param isWhite true if the upgrading player is playing white, false otherwise
     */
    public String showUpgradeFrame(boolean isWhite) {
        return accessoryUI.showUpgradeFrame(isWhite);
    }

    // --------------------------------------------------------
    // ----------------Managing MovesLabel---------------------
    // --------------------------------------------------------

    /**
     * Clears the JTextArea which displays all the moves in chess notation
     */
    public static void clearMovesLabel() {
        UILibrary.MovesLabel.setText("");
    }

    /**
     * Add a string to the end of the Moves Label
     * 
     * @param move Adds string to move label, does not include escape sequences,
     *             must be sent with the string
     */
    public static void appendMovesLabel(String move) {
        UILibrary.MovesLabel.setText(UILibrary.MovesLabel.getText() + move);
   }

   

    // -----------------------------------------------------------
    // --------------Managing Which Frames are Visible------------
    // -----------------------------------------------------------
    
    /**
     * Shows the Main Frame in the chess JFrame
     */
    public static void showMainFrame() {
        UILibrary.MainFrame.setVisible(true);
        UILibrary.NewGameFrame.setVisible(false);
    }

    /**
     * Shows the NewGameFrame in the chess JFrame
     * Button connections can be gotten directly from UILibrary
     */
    public static void showNewGameFrame() {
        UILibrary.NewGameFrame.setVisible(true);
        UILibrary.MainFrame.setVisible(false);
    }


    // -------------------------------------------------------------------------------
    // ------------Managing Changing the Computer Difficulty Slider Frame-------------
    // -------------------------------------------------------------------------------

    /**
     * Gets the image from the local jar File
     * 
     * @param imageToGet Name of the image + type
     * @return The image
     */
    public ImageIcon getImage(String imageToGet) {
        return new ImageIcon(getClass().getClassLoader().getResource(imageToGet));
    }



    /**
    * Show the end game frame with the desired message
    * @param endMessage message to show
    */
    public static void ShowEndGameFrame(String endMessage) {
        CreateAccessoryUIs.endTitle.setText(endMessage);
        CreateAccessoryUIs.endLabel.setVisible(true);
    }

    /**
     * Hide the end game frame
     */
    public static void HideEndGameFrame() {
        CreateAccessoryUIs.endLabel.setVisible(false);
    }


    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Constructor 1
     * For use with board and upgrade frame
     * Initializes connections between board and gui
     */
    public ChessUIManager(CreateAccessoryUIs accessoryUI) {
        boardTiles = CreateMainFrame.createChessBoard();
        this.accessoryUI = accessoryUI;
    }

    /**
     * Constructor 2
     * For use with anything thats not static but doesn't need additional
     * constructing parameters
     * SliderFrame Configuration
     */
    public ChessUIManager() {

    }
}
