package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Bishop;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Knight;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Queen;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Rook;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessUIManager;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUI_Events;
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
    private int currentGameMode;
    private MoveResult lastMoveResult;

    /**
     * Some temporary code to test the .drawBoard function correctly calling UI to
     * update.
     */
    public GameLoop() {
        guiStarter = new GUIStarter();
        resetGUIAndListeners();
        ChessUIManager.showNewGameFrame();
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.addActionListener(e -> {
            currentGameMode = GameMode.PLAYER_VS_PLAYER_GAME_MODE;
            startPlayerVSPlayerGame();
        });
        UILibrary.NewGame_JMenuItem.addActionListener(e -> {
            startMainMenuScreen();
        });
        UILibrary.RestartGame_JMenuItem.addActionListener(e -> {
            restartGame();
        });
        UILibrary.endNewGameButton.addActionListener(e -> {
            startMainMenuScreen();
        });
        UILibrary.endRematchButton.addActionListener(e -> {
            restartGame();
        });
        UILibrary.endViewBoardButton.addActionListener(e -> {
            resetGUIAndListeners();
        });

        UILibrary.UpgradeQueenButton.addActionListener(e -> {
            UILibrary.UpgradePieceFrame.setVisible(false);
            Queen queen = new Queen(lastMoveResult.promoteTeam);
            board.promotePawn(lastMoveResult.promotionLocation, queen);
            sendUpdateBoardState();
        });
        UILibrary.UpgradeBishopButton.addActionListener(e -> {
            UILibrary.UpgradePieceFrame.setVisible(false);
            Bishop bishop = new Bishop(lastMoveResult.promoteTeam);
            board.promotePawn(lastMoveResult.promotionLocation, bishop);
            sendUpdateBoardState();
        });
        UILibrary.UpgradeRookButton.addActionListener(e -> {
            UILibrary.UpgradePieceFrame.setVisible(false);
            Rook rook = new Rook(lastMoveResult.promoteTeam);
            board.promotePawn(lastMoveResult.promotionLocation, rook);
            sendUpdateBoardState();
        });
        UILibrary.UpgradeKnightButton.addActionListener(e -> {
            UILibrary.UpgradePieceFrame.setVisible(false);
            Knight knight = new Knight(lastMoveResult.promoteTeam);
            board.promotePawn(lastMoveResult.promotionLocation, knight);
            sendUpdateBoardState();
        });
    }

    private void startMainMenuScreen() {
        UILibrary.StepBackwards_Button.addActionListener(this);
        UILibrary.StepForwards_Button.addActionListener(this);
        ChessUIManager.HideEndGameFrame();
        ChessUIManager.showNewGameFrame();
    }

    private void restartGame() {
        switch (currentGameMode) {
            case GameMode.PLAYER_VS_PLAYER_GAME_MODE:
                resetGUIAndListeners();
                startPlayerVSPlayerGame();
                break;
            default:
                break;
        }
    }

    private void resetGUIAndListeners() {
        ChessUIManager.HideEndGameFrame();
        UILibrary.StepBackwards_Button.removeActionListener(this);
        UILibrary.StepForwards_Button.removeActionListener(this);
        UILibrary.StepBackwards_Button.addActionListener(this);
        UILibrary.StepForwards_Button.addActionListener(this);
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
            checkGameState(result);
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