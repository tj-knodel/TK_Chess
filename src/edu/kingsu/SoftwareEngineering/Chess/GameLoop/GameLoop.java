package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessUIManager;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;
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

    public GameLoop() {
        this.aiTeam = -1;
        this.moveController = new MoveController();
        guiStarter = new GUIStarter();
        resetGUIAndListeners();
        ChessUIManager.showNewGameFrame();

        UILibrary.WhitePlayer_VS_BlackPlayer_Button.addActionListener(e -> {
            ChessUIManager.showMainFrame();
            this.board = new Board(this);
            this.aiTeam = -1;
            sendUpdateBoardState();
            resetGUIAndListeners();
            setPlayerClickListeners();
        });

        UILibrary.WhitePlayer_VS_BlackComp_Button.addActionListener(e -> {
            ChessUIManager.showMainFrame();
            this.board = new Board(this);
            this.aiTeam = Team.BLACK_TEAM;
            sendUpdateBoardState();
            resetGUIAndListeners();
            setPlayerClickListeners();
        });

        UILibrary.WhiteComp_VS_BlackPlayer_Button.addActionListener(e -> {
            ChessUIManager.showMainFrame();
            this.board = new Board(this);
            this.aiTeam = Team.WHITE_TEAM;
            sendUpdateBoardState();
            resetGUIAndListeners();
            setPlayerClickListeners();
        });

        UILibrary.WhiteComp_VS_BlackComp_Button.addActionListener(e -> {
            ChessUIManager.showMainFrame();
            this.board = new Board(this);
            this.aiTeam = Team.WHITE_TEAM;
            sendUpdateBoardState();
            resetGUIAndListeners();
            aiVsAi = true;
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
            if(aiVsAi) return;
            this.board.undoMove();
            redrawUI();
        });

        UILibrary.StepForwards_Button.addActionListener(e -> {
            if(aiVsAi) return;
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

        UILibrary.EnterMove_TextField.addActionListener(e -> {
            String input = UILibrary.EnterMove_TextField.getText();
            if(aiTeam == board.getTeamTurn()) return;
            MoveResult result = board.applyMoveAlgebraicNotation(input);
            redrawUI();
        });
    }

    private void setPlayerClickListeners() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                chessTile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
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
                            redrawUI();
                        } else {
                            for (int r = 0; r < 8; r++) {
                                for (int c = 0; c < 8; c++) {
                                    guiStarter.chessUIManager.boardTiles[r][c].setPossibleMoveCircleVisibility(false);
                                }
                            }
                            redrawUI();
                        }
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
        ChessUIManager.HideEndGameFrame();
    }

    public String getPromotionPiece() {
        if(aiTeam == board.getTeamTurn())
            return "R";
        else {
            return "Q";
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
        if(aiVsAi) {
            aiTeam = (aiTeam == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        }
        runAI();
    }

    private void runAI() {
        if(aiTeam == board.getTeamTurn()) {
            AIThread ai = new AIThread(new AIPlayer(2, aiTeam), board, guiStarter);
            Thread runningThread = new Thread(ai);
            runningThread.start();
        }
    }

    private void redrawUI() {
        if(!board.isAtStart()) {
            BoardLocation lastMove = board.getLastMoveLocation();
            BoardLocation currentMove = board.getCurrentMoveLocation();
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
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