package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFileChooser;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessUIManager;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNMove;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNReader;
import edu.kingsu.SoftwareEngineering.Chess.Players.AIPlayer;
import edu.kingsu.SoftwareEngineering.Chess.Players.AIThread;

/**
 * The class that acts as the middle man between the Board class and the GUI
 * functionality.
 *
 * @author Daniell Buchner
 * @version 0.1
 */
public class GameLoop {

    private final GUIStarter guiStarter;
    private MoveController moveController;
    private Board board;
    private int aiTeam;
    private boolean aiVsAi = false;
    private GameType gameType;

    public GameLoop() {
        this.aiTeam = -1;
        this.moveController = new MoveController();
        guiStarter = new GUIStarter();
        resetGUIAndListeners();
        ChessUIManager.showNewGameFrame();

        UILibrary.WhitePlayer_VS_BlackPlayer_Button.addActionListener(e -> {
            this.gameType = GameType.PLAYER_VS_PLAYER;
            createGame(-1);
            setPlayerClickListeners();
        });

        UILibrary.WhitePlayer_VS_BlackComp_Button.addActionListener(e -> {
            this.gameType = GameType.WHITE_PLAYER_VS_BLACK_AI;
            createGame(Team.BLACK_TEAM);
            setPlayerClickListeners();
        });

        UILibrary.WhiteComp_VS_BlackPlayer_Button.addActionListener(e -> {
            this.gameType = GameType.WHITE_AI_VS_BLACK_PLAYER;
            createGame(Team.WHITE_TEAM);
            setPlayerClickListeners();
        });

        UILibrary.WhiteComp_VS_BlackComp_Button.addActionListener(e -> {
            this.gameType = GameType.AI_VS_AI;
            createGame(Team.WHITE_TEAM);
            aiVsAi = true;
        });

        UILibrary.RDMPlayer_VS_RDMComp_Button.addActionListener(e -> {
            Random random = new Random(System.currentTimeMillis());
            int aiTeam = random.nextInt(2);
            createGame(aiTeam);
            setPlayerClickListeners();
        });

        UILibrary.ShowAIStrengthSlider_JMenuItem.addActionListener(e -> {
            board.setIsPaused(true);
            ChessUIManager.showSliderFrame();
        });

        UILibrary.CancelSliderButton.addActionListener(e -> {
            resumeGame();
        });

        UILibrary.ConfirmSliderButton.addActionListener(e -> {
            resumeGame();
        });

        UILibrary.UpgradeQueenButton.addActionListener(e -> {
            UILibrary.UpgradePieceFrame.setVisible(false);
        });
        UILibrary.UpgradeBishopButton.addActionListener(e -> {
            UILibrary.UpgradePieceFrame.setVisible(false);
        });
        UILibrary.UpgradeRookButton.addActionListener(e -> {
            UILibrary.UpgradePieceFrame.setVisible(false);
        });

        UILibrary.UpgradeKnightButton.addActionListener(e -> {
            UILibrary.UpgradePieceFrame.setVisible(false);
        });

        UILibrary.StepBackwards_Button.addActionListener(e -> {
            if (aiVsAi)
                return;
            this.board.undoMove();
            redrawUI();
        });

        UILibrary.StepForwards_Button.addActionListener(e -> {
            if (aiVsAi)
                return;
            this.board.redoMove();
            redrawUI();
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
                    e1.printStackTrace();
                }
            }
        });

        UILibrary.LoadGame_JMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                createGame(-1);
                board.loadPGNFile(file);
                setPlayerClickListeners();
            }
        });

        UILibrary.EnterMove_TextField.addActionListener(e -> {
            String input = UILibrary.EnterMove_TextField.getText();
            if (aiTeam == board.getTeamTurn())
                return;
            MoveResult result = board.applyMoveAlgebraicNotation(input);
            redrawUI();
        });

        UILibrary.NewGame_JMenuItem.addActionListener(e -> {
            board.setIsPaused(true);
            ChessUIManager.showNewGameFrame();
        });

        UILibrary.RestartGame_JMenuItem.addActionListener(e -> {
            restartGame();
        });

        UILibrary.ResumeGame_JMenuItem.addActionListener(e -> {
            board.setIsPaused(false);
            resumeGame();
        });

        UILibrary.ResumeGame_Button.addActionListener(e -> {
            board.setIsPaused(false);
            resumeGame();
        });

        UILibrary.endNewGameButton.addActionListener(e -> {
            board.setIsPaused(true);
            ChessUIManager.showNewGameFrame();
        });

        UILibrary.endRematchButton.addActionListener(e -> {
            restartGame();
            resetGUIAndListeners();
            setPlayerClickListeners();
        });

        UILibrary.endViewBoardButton.addActionListener(e -> {
            resumeGame();
            resetGUIAndListeners();
            setPlayerClickListeners();
            ChessUIManager.HideEndGameFrame();
        });
    }

    private void restartGame() {
        resetGUIAndListeners();
        if (gameType == GameType.AI_VS_AI) {
            createGame(Team.WHITE_TEAM);
            aiVsAi = true;
        } else if (gameType == GameType.PLAYER_VS_PLAYER) {
            createGame(-1);
            setPlayerClickListeners();
        } else if (gameType == GameType.WHITE_AI_VS_BLACK_PLAYER) {
            createGame(Team.WHITE_TEAM);
            setPlayerClickListeners();
        } else if (gameType == GameType.WHITE_PLAYER_VS_BLACK_AI) {
            createGame(Team.BLACK_TEAM);
            setPlayerClickListeners();
        }
    }

    private void resumeGame() {
        board.setIsPaused(false);
        if (gameType != GameType.AI_VS_AI) {
            resetGUIAndListeners();
            setPlayerClickListeners();
        }
        ChessUIManager.showMainFrame();
        redrawUI();
        if (aiVsAi || aiTeam == board.getTeamTurn()) {
            runAI();
        }
    }

    private void createGame(int aiTeam) {
        ChessUIManager.HideEndGameFrame();
        aiVsAi = false;
        clearChessNotationLabel();
        ChessUIManager.showMainFrame();
        this.board = new Board(this);
        this.aiTeam = aiTeam;
        sendUpdateBoardState();
        resetGUIAndListeners();
    }

    private void setPlayerClickListeners() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                chessTile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (aiTeam == board.getTeamTurn())
                            return;
                        if (moveController.chessTileClick(board, chessTile.row,
                                chessTile.column)) {
                            MoveResult result = moveController.sendMovesToBoard(board);
                        }
                        if (!moveController.getIsFirstClick()) {
                            var moves = moveController.getAllPossibleMoves();
                            for (BoardLocation location : moves) {
                                guiStarter.chessUIManager.boardTiles[location.row][location.column]
                                        .setPossibleMoveCircleVisibility(true);
                            }
                            guiStarter.chessUIManager.boardTiles[moveController
                                    .getFirstClickLocation().row][moveController.getFirstClickLocation().column]
                                    .setPreviousMoveSquareVisibility(true);
                            redrawUI();
                        } else {
                            for (int r = 0; r < 8; r++) {
                                for (int c = 0; c < 8; c++) {
                                    guiStarter.chessUIManager.boardTiles[r][c].setPossibleMoveCircleVisibility(false);
                                }
                            }
                            guiStarter.chessUIManager.boardTiles[moveController
                                    .getFirstClickLocation().row][moveController.getFirstClickLocation().column]
                                    .setPreviousMoveSquareVisibility(false);
                            redrawUI();
                        }
                        redrawUI();
                    }
                });
            }
        }
    }

    private void resetGUIAndListeners() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                for (MouseListener adapter : chessTile.getMouseListeners()) {
                    chessTile.removeMouseListener(adapter);
                }
            }
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
            }
        }
    }

    public String getPromotionPiece() {
        if (aiTeam == board.getTeamTurn())
            return "R";
        else {
            return "R";
        }
    }

    public void updateChessNotationLabel(String value) {
        ChessUIManager.appendMovesLabel(value);
    }

    public void clearChessNotationLabel() {
        ChessUIManager.clearMovesLabel();
    }

    public void sendUpdateBoardState() {
        redrawUI();
        MoveResult result = board.getLastMoveResult();
        if (result != null) {
            if (result.isCheckmate) {
                ChessUIManager.ShowEndGameFrame(
                        ((result.checkmateTeam == Team.WHITE_TEAM) ? "Black" : "White") + " team wins!");
                board.setIsPaused(true);
            } else if (result.isStalemate) {
                ChessUIManager.ShowEndGameFrame("Stalemate!");
                board.setIsPaused(true);
            }
        }
        if (aiVsAi) {
            aiTeam = (aiTeam == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        }
        runAI();
    }

    private void runAI() {
        if (aiTeam == board.getTeamTurn()) {
            AIThread ai = new AIThread(new AIPlayer(2, aiTeam), board, guiStarter);
            Thread runningThread = new Thread(ai);
            runningThread.start();
        }
    }

    private void redrawUI() {
        if (!board.isAtStart()) {
            BoardLocation lastMove = board.getLastMoveLocation();
            BoardLocation currentMove = board.getCurrentMoveLocation();
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if (!moveController.getIsFirstClick()) {
                        if (r == moveController.getFirstClickLocation().row
                                && c == moveController.getFirstClickLocation().column)
                            continue;
                    }
                    guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
                }
            }
            guiStarter.chessUIManager.boardTiles[lastMove.row][lastMove.column]
                    .setPreviousMoveSquareVisibility(true);
            guiStarter.chessUIManager.boardTiles[currentMove.row][currentMove.column]
                    .setPreviousMoveSquareVisibility(true);
        }

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        UILibrary.MainFrame.repaint();
    }

    //    /**
    //     * Checks if the game is in a non-playable state.
    //     * Checkmate or stalemate is checked.
    //     * @param result The MoveResult to check.
    //     */
    //    public void checkGameState(MoveResult result) {
    //        lastMoveResult = result;
    //        if (result.wasSuccessful) {
    //            sendUpdateBoardState();
    //            if (result.isCheckmate) {
    //                ChessUIManager.ShowEndGameFrame(
    //                        ((result.checkmateTeam == Team.WHITE_TEAM) ? "Black" : "White") + " team wins!");
    //                UILibrary.StepBackwards_Button.removeActionListener(this);
    //                UILibrary.StepForwards_Button.removeActionListener(this);
    //            } else if (result.isStalemate) {
    //                ChessUIManager.ShowEndGameFrame("Stalemate!");
    //                UILibrary.StepBackwards_Button.removeActionListener(this);
    //                UILibrary.StepForwards_Button.removeActionListener(this);
    //            } else if (result.isPromotion) {
    //                guiStarter.chessUIManager.showUpgradeFrame(result.promoteTeam == Team.WHITE_TEAM);
    //            }
    //            // gameMode.switchTeam();
    //        }
    //    }

    //        UILibrary.StepBackwards_Button.addActionListener(e -> {
    //            if (board.undoMove()) {
    //                gameMode.switchTeam();
    //            }
    //            BoardLocation lastMove = board.getLastMoveLocation();
    //            BoardLocation currentMove = board.getCurrentMoveLocation();
    //            for (int r = 0; r < 8; r++) {
    //                for (int c = 0; c < 8; c++) {
    //                    guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
    //                }
    //            }
    //            if (!board.isAtStart()) {
    //                guiStarter.chessUIManager.boardTiles[lastMove.row][lastMove.column]
    //                        .setPreviousMoveSquareVisibility(true);
    //                guiStarter.chessUIManager.boardTiles[currentMove.row][currentMove.column]
    //                        .setPreviousMoveSquareVisibility(true);
    //            }
    //            sendUpdateBoardState();
    //        });
    //
    //        UILibrary.StepForwards_Button.addActionListener(e -> {
    //            if (board.redoMove()) {
    //                gameMode.switchTeam();
    //            }
    //            BoardLocation lastMove = board.getLastMoveLocation();
    //            BoardLocation currentMove = board.getCurrentMoveLocation();
    //            for (int r = 0; r < 8; r++) {
    //                for (int c = 0; c < 8; c++) {
    //                    guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
    //                }
    //            }
    //            if (!board.isAtStart()) {
    //                guiStarter.chessUIManager.boardTiles[lastMove.row][lastMove.column]
    //                        .setPreviousMoveSquareVisibility(true);
    //                guiStarter.chessUIManager.boardTiles[currentMove.row][currentMove.column]
    //                        .setPreviousMoveSquareVisibility(true);
    //            }
    //            sendUpdateBoardState();
    //        });

    //    @Override
    //    public void actionPerformed(ActionEvent e) {
    //        if (e.getSource() == UILibrary.EnterMove_TextField) {
    //            String input = UILibrary.EnterMove_TextField.getText();
    //            MoveResult result = board.applyMoveAlgebraicNotation(input);
    //            BoardLocation lastMove = board.getLastMoveLocation();
    //            BoardLocation currentMove = board.getCurrentMoveLocation();
    //            for (int r = 0; r < 8; r++) {
    //                for (int c = 0; c < 8; c++) {
    //                    guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
    //                }
    //            }
    //            if (!board.isAtStart()) {
    //                guiStarter.chessUIManager.boardTiles[lastMove.row][lastMove.column]
    //                        .setPreviousMoveSquareVisibility(true);
    //                guiStarter.chessUIManager.boardTiles[currentMove.row][currentMove.column]
    //                        .setPreviousMoveSquareVisibility(true);
    //            }
    //            checkGameState(result);
    //            UILibrary.EnterMove_TextField.setText("");
    //        }
    //    }
}