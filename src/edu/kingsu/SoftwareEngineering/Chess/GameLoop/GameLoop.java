package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Bishop;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Knight;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Queen;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Rook;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessUIManager;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;
import edu.kingsu.SoftwareEngineering.Chess.GameMode.AIVSAIGameMode;
import edu.kingsu.SoftwareEngineering.Chess.GameMode.GameMode;
import edu.kingsu.SoftwareEngineering.Chess.GameMode.PlayerVSAIGameMode;
import edu.kingsu.SoftwareEngineering.Chess.GameMode.PlayerVSPlayerGameMode;

/**
 * The class that acts as the middle man between the Board class and the GUI
 * functionality.
 * 
 * @author Daniell Buchner
 * @version 0.1
 */
public class GameLoop implements ActionListener {

    private GUIStarter guiStarter;
    private Board board;
    private GameMode gameMode;
    private int currentGameMode;
    private MoveResult lastMoveResult;

    /**
     * Some temporary code to test the .drawBoard function correctly calling UI to
     * update.
     */
    public GameLoop() {
        guiStarter = new GUIStarter();
        board = new Board();
        board.loadPGNFile(file);
        ChessUIManager.showMainFrame();

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        gameMode = new PlayerVSAIGameMode(2);
        //gameMode = new AIVSAIGameMode(4);
        gameMode.setGameLoop(this);
        ((PlayerVSAIGameMode) gameMode).setClickListeners(guiStarter, board);
        // ((AIVSAIGameMode) gameMode).setClickListeners(guiStarter, board);
        gameMode.startGame();
    }

    /**
     * Checks if the game is in a non-playable state.
     * Checkmate or stalemate is checked.
     * @param result The MoveResult to check.
     */
    public void checkGameState(MoveResult result) {
        lastMoveResult = result;
        if (result.wasSuccessful) {
            sendUpdateBoardState();
            if (result.isCheckmate) {
                ChessUIManager.ShowEndGameFrame(
                        ((result.checkmateTeam == Team.WHITE_TEAM) ? "Black" : "White") + " team wins!");
                UILibrary.StepBackwards_Button.removeActionListener(this);
                UILibrary.StepForwards_Button.removeActionListener(this);
            } else if (result.isStalemate) {
                ChessUIManager.ShowEndGameFrame("Stalemate!");
                UILibrary.StepBackwards_Button.removeActionListener(this);
                UILibrary.StepForwards_Button.removeActionListener(this);
            } else if (result.isPromotion) {
                guiStarter.chessUIManager.showUpgradeFrame(result.promoteTeam == Team.WHITE_TEAM);
            }
            gameMode.switchTeam();
        }
    }

    public void sendUpdateBoardState() {
        UILibrary.MainFrame.repaint();
        guiStarter.chessUIManager.drawBoard(board.getBoard());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == UILibrary.EnterMove_TextField) {
            String input = UILibrary.EnterMove_TextField.getText();
            MoveResult result = board.applyMoveAlgebraicNotation(input);
            BoardLocation lastMove = board.getLastMoveLocation();
            BoardLocation currentMove = board.getCurrentMoveLocation();
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
                }
            }
            if (!board.isAtStart()) {
                guiStarter.chessUIManager.boardTiles[lastMove.row][lastMove.column]
                        .setPreviousMoveSquareVisibility(true);
                guiStarter.chessUIManager.boardTiles[currentMove.row][currentMove.column]
                        .setPreviousMoveSquareVisibility(true);
            }
            checkGameState(result);
        }

        // Step back a move
        if (e.getSource() == UILibrary.StepBackwards_Button) {
            if (board.undoMove()) {
                gameMode.switchTeam();
            }
            BoardLocation lastMove = board.getLastMoveLocation();
            BoardLocation currentMove = board.getCurrentMoveLocation();
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
                }
            }
            if (!board.isAtStart()) {
                guiStarter.chessUIManager.boardTiles[lastMove.row][lastMove.column]
                        .setPreviousMoveSquareVisibility(true);
                guiStarter.chessUIManager.boardTiles[currentMove.row][currentMove.column]
                        .setPreviousMoveSquareVisibility(true);
            }
            sendUpdateBoardState();
        }

        // Set Forward a move
        if (e.getSource() == UILibrary.StepForwards_Button) {
            if (board.redoMove()) {
                gameMode.switchTeam();
            }
            BoardLocation lastMove = board.getLastMoveLocation();
            BoardLocation currentMove = board.getCurrentMoveLocation();
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
                }
            }
            if (!board.isAtStart()) {
                guiStarter.chessUIManager.boardTiles[lastMove.row][lastMove.column]
                        .setPreviousMoveSquareVisibility(true);
                guiStarter.chessUIManager.boardTiles[currentMove.row][currentMove.column]
                        .setPreviousMoveSquareVisibility(true);
            }
            sendUpdateBoardState();
        }
    }
}