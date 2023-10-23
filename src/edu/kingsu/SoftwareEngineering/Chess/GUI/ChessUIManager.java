package edu.kingsu.SoftwareEngineering.Chess.GUI;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI.PIECES_ENUM;

import javax.swing.ImageIcon;
import java.awt.Image;
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

    // ----------------------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------------------
    // -----------------------------------Drawing UI Board----------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

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
                /**
                 
                TEMPLATE IF-ELSE Chain to display board, if conditions can change, the function calls should stay the same
                // Many `if` statements to display the correct image
                if (piece == PIECES_ENUM.Pawn) {
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Pawn, isWhite);
                } else if (piece == PIECES_ENUM.Rook) {
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Rook, isWhite);
                } else if (piece == PIECES_ENUM.Knight) {
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Knight, isWhite);
                } else if (piece == PIECES_ENUM.Bishop) {
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Bishop, isWhite);
                } else if (piece == PIECES_ENUM.Queen) {
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.Queen, isWhite);
                } else if (piece == PIECES_ENUM.King) {
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.King, isWhite);
                } else {
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.None, true);
                }
            } 
            */

        }
    }

    }

    // TEMPORARY, To be implemented
    public void setPossibleMovesConfiguration() {
    }

    // TEMPORARY, To be implemented
    public void setPreviousMovesConfiguration() {
    }

    // ----------------------------------------------------------------------------------------------------------
    // ---------------------------------Managing Upgrading Pieces-----------------------------------
    // ----------------------------------------------------------------------------------------------------------
    private CreateAccessoryUIs accessoryUI; // Variable cant be static because of how images are retrieved

    /**
     * Shows the upgrade frame for when a player is upgrading a piece
     * 
     * @param isWhite true if the upgrading player is playing white, false otherwise
     */
    public void showUpgradeFrame(boolean isWhite) {
        accessoryUI.showUpgradeFrame(isWhite);
    }

    /**
     * Hides the upgrade frame for when a player is upgrading a piece
     */
    public void hideUpgradeFrame() {
        UILibrary.UpgradePieceFrame.setVisible(false);
    }

    // ----------------------------------------------------------------------------------------------------------
    // -----------------------------------Managing MovesLabel----------------------------------------
    // ----------------------------------------------------------------------------------------------------------

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

    // ----------------------------------------------------------------------------------------------------------
    // ------------------------------Managing Which Frames are Visible-----------------------------
    // ----------------------------------------------------------------------------------------------------------

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
     * Shows the Slider Frame in the chess JFrame
     */
    public static void showSliderFrame() {
        UILibrary.SetAIStrengthSliderFrame.setVisible(true);
        UILibrary.MainFrame.setVisible(false);
        UILibrary.NewGameFrame.setVisible(false);
    }

    // ----------------------------------------------------------------------------------------------------------
    // -----------------Managing Changing the Computer Difficulty Slider Frame-------------
    // ----------------------------------------------------------------------------------------------------------

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
     * Sets the image description of what color computer Ai your changing in the UI
     * 
     * @param isWhite true if the computer AI is playing white, false if playing
     *                black
     */
    public void setSliderFrameToColor(boolean isWhite) {
        if (isWhite) {
            UILibrary.CurrentSelectedComputer_ImageLabel.setIcon(new ImageIcon(
                    getImage("computer_white.png").getImage().getScaledInstance(112, 105, Image.SCALE_DEFAULT)));
            UILibrary.CurrentSelectedComputer_TextLabel.setText("White Computer");
        } else {
            UILibrary.CurrentSelectedComputer_ImageLabel.setIcon(new ImageIcon(
                    getImage("computer_black.png").getImage().getScaledInstance(112, 105, Image.SCALE_DEFAULT)));
            UILibrary.CurrentSelectedComputer_TextLabel.setText("Black Computer");
        }
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
