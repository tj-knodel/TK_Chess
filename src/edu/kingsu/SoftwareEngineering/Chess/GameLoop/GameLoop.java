package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessUIManager;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;
import edu.kingsu.SoftwareEngineering.Chess.GameMode.GameMode;
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

    /**
     * Some temporary code to test the .drawBoard function correctly calling UI to
     * update.
     */
    public GameLoop() {
        guiStarter = new GUIStarter();
        ChessUIManager.showNewGameFrame();
        UILibrary.EnterMove_TextField.addActionListener(this);
        UILibrary.StepBackwards_Button.addActionListener(this);
        UILibrary.StepForwards_Button.addActionListener(this);
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.addActionListener(e -> {
            startPlayerVSPlayerGame();
        });
        UILibrary.NewGame_JMenuItem.addActionListener(e -> {
            startMainMenuScreen();
        });
        UILibrary.RestartGame_JMenuItem.addActionListener(e -> {
            startPlayerVSPlayerGame();
        });
    }

    private void startMainMenuScreen() {
        ChessUIManager.showNewGameFrame();
    }

    private void startPlayerVSPlayerGame() {
        ChessUIManager.clearMovesLabel();
        board = new Board();
        ChessUIManager.showMainFrame();

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        gameMode = new PlayerVSPlayerGameMode();
        gameMode.setGameLoop(this);
        ((PlayerVSPlayerGameMode) gameMode).setClickListeners(guiStarter, board);
        gameMode.startGame();

    }

    public void sendUpdateBoardState() {
        UILibrary.MainFrame.repaint();
        guiStarter.chessUIManager.drawBoard(board.getBoard());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == UILibrary.EnterMove_TextField) {
            String input = UILibrary.EnterMove_TextField.getText();
            if (board.applyMoveAlgebraicNotation(input).wasSuccessful) {
                gameMode.switchTeam();
                sendUpdateBoardState();
            }

            // guiStarter.chessUIManager.drawBoard(board.getBoard());
            // System.out.println("The text box detected input: " + input);
        }

        // Step back a move
        if (e.getSource() == UILibrary.StepBackwards_Button) {
            if (board.undoMove()) {
                gameMode.switchTeam();
            }
            sendUpdateBoardState();
        }

        // Set Forward a move
        if (e.getSource() == UILibrary.StepForwards_Button) {
            if (board.redoMove()) {
                gameMode.switchTeam();
            }
            sendUpdateBoardState();
        }
    }
}