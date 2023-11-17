package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
import edu.kingsu.SoftwareEngineering.Chess.GameMode.NewPlayerVSAIGameMode;
import edu.kingsu.SoftwareEngineering.Chess.GameMode.PlayerVSAIGameMode;
import edu.kingsu.SoftwareEngineering.Chess.GameMode.PlayerVSPlayerGameMode;
import edu.kingsu.SoftwareEngineering.Chess.GameMode.TutorialGameMode;

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
    private int aiTeam;
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
            resetGUIAndListeners();
            currentGameMode = GameMode.PLAYER_VS_PLAYER_GAME_MODE;
            startPlayerVSPlayerGame();
        });

        UILibrary.WhitePlayer_VS_BlackComp_Button.addActionListener(e -> {
            resetGUIAndListeners();
            currentGameMode = GameMode.PLAYER_VS_AI_GAME_MODE;
            aiTeam = Team.BLACK_TEAM;
            // startPlayerVSAIBlackGame();
            startPlayerVSAIGame();
        });

        UILibrary.WhiteComp_VS_BlackPlayer_Button.addActionListener(e -> {
            resetGUIAndListeners();
            currentGameMode = GameMode.PLAYER_VS_AI_GAME_MODE;
            aiTeam = Team.WHITE_TEAM;
            startPlayerVSAIWhiteGame();
        });

        UILibrary.WhiteComp_VS_BlackComp_Button.addActionListener(e -> {
            resetGUIAndListeners();
            currentGameMode = GameMode.PLAYER_VS_AI_GAME_MODE;
            startAIVSAIGameMode();
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

        UILibrary.EnterMove_TextField.addActionListener(this);

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

        UILibrary.LoadGame_JMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                resetGUIAndListeners();
                loadGamePlayerVSPlayer(file);
            }
        });

        UILibrary.LearnChessButton.addActionListener(e -> {

        });

        // Used https://stackoverflow.com/questions/14589386/how-to-save-file-using-jfilechooser-in-java
        UILibrary.SaveGame_JMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileWriter fw = new FileWriter(file);
                    fw.write(board.getAlgebraicNotation());
                    fw.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        UILibrary.ResumeGame_JMenuItem.addActionListener(e -> {
            ChessUIManager.showMainFrame();
        });

        UILibrary.StepBackwards_Button.addActionListener(e -> {
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
        });

        UILibrary.StepForwards_Button.addActionListener(e -> {
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
        });
    }

    private void startMainMenuScreen() {
        ChessUIManager.HideEndGameFrame();
        ChessUIManager.showNewGameFrame();
    }

    private void restartGame() {
        switch (currentGameMode) {
            case GameMode.PLAYER_VS_PLAYER_GAME_MODE:
                resetGUIAndListeners();
                startPlayerVSPlayerGame();
                break;
            case GameMode.PLAYER_VS_AI_GAME_MODE:
                resetGUIAndListeners();
                startPlayerVSAIBlackGame();
                break;
            default:
                break;
        }
    }

    private void resetGUIAndListeners() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
            }
        }
        ChessUIManager.HideEndGameFrame();
        // UILibrary.StepBackwards_Button.removeActionListener(this);
        // for (ActionListener l : UILibrary.StepBackwards_Button.getActionListeners()) {
        //     UILibrary.StepBackwards_Button.removeActionListener(l);
        // }
        // for (ActionListener l : UILibrary.StepForwards_Button.getActionListeners()) {
        //     UILibrary.StepBackwards_Button.removeActionListener(l);
        // }
        // // UILibrary.StepForwards_Button.removeActionListener(this);
        // UILibrary.StepBackwards_Button.addActionListener(this);
        // UILibrary.StepForwards_Button.addActionListener(this);
    }

    private void startAIVSAIGameMode() {
        ChessUIManager.clearMovesLabel();
        board = new Board(this);
        ChessUIManager.showMainFrame();

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        gameMode = new AIVSAIGameMode(2);
        gameMode.setGameLoop(this);
        gameMode.initialize(board, guiStarter);
        gameMode.startGame();
    }

    private void startPlayerVSAIGame() {
        ChessUIManager.clearMovesLabel();
        board = new Board(this);
        ChessUIManager.showMainFrame();

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        gameMode = new NewPlayerVSAIGameMode();
        gameMode.setGameLoop(this);
        gameMode.initialize(board, guiStarter);
        gameMode.startGame();
    }

    private void startPlayerVSPlayerGame() {
        ChessUIManager.clearMovesLabel();
        board = new Board(this);
        ChessUIManager.showMainFrame();

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        gameMode = new PlayerVSPlayerGameMode();
        gameMode.setGameLoop(this);
        gameMode.initialize(board, guiStarter);
        gameMode.startGame();
    }

    private void startPlayerVSAIWhiteGame() {
        ChessUIManager.clearMovesLabel();
        board = new Board(this);
        ChessUIManager.showMainFrame();

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        gameMode = new PlayerVSAIGameMode(2, Team.WHITE_TEAM);
        gameMode.setGameLoop(this);
        gameMode.initialize(board, guiStarter);
        gameMode.startGame();
    }

    private void startPlayerVSAIBlackGame() {
        ChessUIManager.clearMovesLabel();
        board = new Board(this);
        ChessUIManager.showMainFrame();

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        gameMode = new PlayerVSAIGameMode(2, Team.BLACK_TEAM);
        gameMode.setGameLoop(this);
        gameMode.initialize(board, guiStarter);
        gameMode.startGame();
    }

    private void loadGamePlayerVSPlayer(File file) {
        ChessUIManager.clearMovesLabel();
        board = new Board(this);
        board.loadPGNFile(file);
        ChessUIManager.showMainFrame();

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        gameMode = new PlayerVSPlayerGameMode();
        //gameMode = new AIVSAIGameMode(4);
        gameMode.setGameLoop(this);
        gameMode.initialize(board, guiStarter);
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
            // gameMode.switchTeam();
        }
    }

    public void sendUpdateBoardState() {
        guiStarter.chessUIManager.drawBoard(board.getBoard());
        UILibrary.MainFrame.repaint();

        gameMode.getNextTurn();
        // JOptionPane.showConfirmDialog(null, "Updated");

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
            UILibrary.EnterMove_TextField.setText("");
        }
    }
}