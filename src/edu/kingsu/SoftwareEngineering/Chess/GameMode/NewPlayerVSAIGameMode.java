package edu.kingsu.SoftwareEngineering.Chess.GameMode;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.MoveController;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.Timer;
import edu.kingsu.SoftwareEngineering.Chess.Players.AIPlayer;
import edu.kingsu.SoftwareEngineering.Chess.Players.AIThread;
import edu.kingsu.SoftwareEngineering.Chess.Players.Move;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NewPlayerVSAIGameMode extends GameMode {

    private MoveController moveController;
    private AIThread aiThread;
    private Thread aiThreadRunner;
    private Timer timer;
    private Board board;
    private int teamTurn;
    private GUIStarter guiStarter;
    private int aiTeam;

    public NewPlayerVSAIGameMode() {
        this.teamTurn = Team.WHITE_TEAM;
        this.aiTeam = Team.BLACK_TEAM;
        this.moveController = new MoveController(Team.WHITE_TEAM);
        this.timer = new Timer();
    }

    @Override
    public void initialize(Board board, GUIStarter guiStarter) {
        this.board = board;
        this.guiStarter = guiStarter;
        setClickListeners(guiStarter, board);
    }

    @Override
    protected void setClickListeners(GUIStarter guiStarter, Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                for (MouseListener adapter : chessTile.getMouseListeners()) {
                    chessTile.removeMouseListener(adapter);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                chessTile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (teamTurn == aiTeam)
                            return;
                        if (moveController.chessTileClick(board, chessTile.row,
                                chessTile.column)) {
                            MoveResult result = moveController.sendMovesToBoard(board);
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
                            gameLoop.checkGameState(result);
                            // switchTeam();
                        }
                        if (!moveController.getIsFirstClick()) {
                            var moves = moveController.getAllPossibleMoves();
                            for (BoardLocation location : moves) {
                                guiStarter.chessUIManager.boardTiles[location.row][location.column]
                                        .setPossibleMoveCircleVisibility(true);
                            }
                            gameLoop.sendUpdateBoardState();
                            // UILibrary.MainFrame.repaint();
                        } else if (moveController.getIsFirstClick()) {
                            for (int r = 0; r < 8; r++) {
                                for (int c = 0; c < 8; c++) {
                                    guiStarter.chessUIManager.boardTiles[r][c].setPossibleMoveCircleVisibility(false);
                                }
                            }
                            gameLoop.sendUpdateBoardState();
                            // UILibrary.MainFrame.repaint();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void switchTeam() {
        gameLoop.sendUpdateBoardState();
        teamTurn = (teamTurn == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        runAI();
    }

    @Override
    public void startGame() {
        if (aiTeam == Team.WHITE_TEAM) {
            runAI();
        }
    }

    @Override
    public void endGame() {
    }

    private void runAI() {
        // timer.startTimer();
        // while (timer.getSeconds() < 3) {
        // }
        // timer.stopTimer();
        // timer.resetTimer();
        if (teamTurn == aiTeam) {
            this.aiThread = new AIThread(new AIPlayer(2, aiTeam), board, guiStarter);
            this.aiThreadRunner = new Thread(aiThread);
            aiThreadRunner.start();
            try {
                // aiThreadRunner.sleep(2000);
                aiThreadRunner.join();
                Move aiMove = aiThread.getMove();
                MoveResult result = board.applyMove(aiMove.piece, aiMove.start, aiMove.end, true, true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switchTeam();
        }
    }

}
