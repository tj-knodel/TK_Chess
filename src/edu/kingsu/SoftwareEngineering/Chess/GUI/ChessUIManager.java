package edu.kingsu.SoftwareEngineering.Chess.GUI;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI.PIECES_ENUM;


/*
 
    ChessTileUI functions:
    enum PIECES_ENUM { Pawn, Rook, Knight, Bishop, Queen, King, None }
    
    void setPieceImage(PIECES_ENUM piece, boolean isWhite) 
    void setPossibleMoveCircleVisibility(boolean visibility)
    void setPreviousMoveSquareVisibility(boolean visibility)
 */

/**
 * Connection script between the Chess UI and game loop / board.
 */
public class ChessUIManager {
    
    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Array which holds the ChessTileUI squares
     */
    public ChessTileUI boardTiles[][];
    
    private CreateAccessoryUIs accessoryUI; // Variable cant be static because of how images are retrieved

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Draws the board with the specific pieces in the certain positions
     * @param pieces 2d array of Piece Objects
     */
    public void drawBoard(Piece[][] pieces) {

        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                
                Piece pieceObject = pieces[row][column];
                if (pieceObject == null) {
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.None, true);
                } // Keep doing if else statements,  for each piece
                
            }
        }

    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Shows the upgrade frame for when a player is upgrading a piece
     * @param isWhite true if the upgrading player is playing white, false otherwise
     */
    public void showUpgradeFrame(boolean isWhite) {
        accessoryUI.showUpgradeFrame( isWhite);
    }

    /**
     * Hides the upgrade frame for when a player is upgrading a piece
     */
    public void hideUpgradeFrame() {
        UILibrary.UpgradePieceFrame.setVisible(false);
    }

     // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Clears the JTextArea which displays all the moves in chess notation
     */
    public void clearMovesLabel() {
        UILibrary.MovesLabel.setText("");
    }

    /**
     * Add a string to the end of the Moves Label
     * @param move Adds string to move label, does not include escape sequences, must be sent with the string
     */
    public void appendMovesLabel(String move) {
        UILibrary.MovesLabel.setText(    UILibrary.MovesLabel.getText() + move   );
    }

    // -----------------------------------------------------
    // -----------------------------------------------------
    
    /**
     * Shows the Main Frame in the chess JFrame
     */
    public static void showMainFrame() {
        UILibrary.MainFrame.setVisible(true);
        UILibrary.NewGameFrame.setVisible(false);
        UILibrary.SetAIStrengthSliderFrame.setVisible(false);
    }

    /**
     * Shows the NewGameFrame in the chess JFrame
     * Button connections can be gotten directly from UILibrary
     */
    public static void showNewGameFrame() {
        UILibrary.NewGameFrame.setVisible(true);
        UILibrary.SetAIStrengthSliderFrame.setVisible(false);
        UILibrary.MainFrame.setVisible(false);
    }

    /**
     * 
     */
    public static void showSliderFrame() {
        UILibrary.SetAIStrengthSliderFrame.setVisible(true);
        UILibrary.MainFrame.setVisible(false);
        UILibrary.NewGameFrame.setVisible(false);
    }

        
    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Initializes connections between board and gui
     */
    public ChessUIManager(CreateAccessoryUIs accessoryUI) {
        boardTiles = CreateMainFrame.createChessBoard();
        this.accessoryUI = accessoryUI;
    }


}
