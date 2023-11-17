package edu.kingsu.SoftwareEngineering.Chess.GameMode;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.MoveController;
import edu.kingsu.SoftwareEngineering.Chess.Players.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * {@inheritDoc}
 */
public class PlayerVSAIGameMode extends GameMode {

    private MoveController moveController;
    private AIThread ai;
    private Thread runningThread;
    private int difficulty;
    private int aiTeam;
    private int playerTeam;
    private int teamTurn;
    private Board board;
    private GUIStarter guiStarter;

    /**
     * Creates a new player vs ai game mode and sets the AI to black
     * @param aiDifficulty
     */
    public PlayerVSAIGameMode(int aiDifficulty, int aiTeam) {
        this.moveController = new MoveController();
        teamTurn = Team.WHITE_TEAM;
        playerTeam = (aiTeam == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        this.aiTeam = aiTeam;
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void switchTeam() {
        teamTurn = (teamTurn == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        runAI();
        System.out.println("Team is now: " + teamTurn);
    }

    /**
     * Sets the click listeners to a GUI so that moves can be registered.
     * @param guiStarter The GUIStarted to listen for clicks to.
     * @param board The board to play on.
     */
    protected void setClickListeners(GUIStarter guiStarter, Board board) {
        // super hacky but I'm lazy

        if (aiTeam == Team.WHITE_TEAM) {
            runAI();
            gameLoop.sendUpdateBoardState();
        }

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
                        if (moveController.chessTileClick(board, teamTurn, chessTile.row,
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

    /**
     * {inheritDoc}
     */
    @Override
    public void startGame() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void endGame() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }

    private void runAI() {
        if (teamTurn == aiTeam) {

            ai = new AIThread(new AIPlayer(difficulty, aiTeam), board);
            runningThread = new Thread(ai);
            runningThread.start();
            // try {
            //     runningThread.sleep(1000);
            // } catch (InterruptedException e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            // }
            try {
                runningThread.join();
                Move aiMove = ai.getMove();
                board.applyMove(aiMove.piece, aiMove.start, aiMove.end, true, true);
            } catch (Exception e) {
                System.err.println("oopsies with the AIThread");
            }
            switchTeam();
        }
    }

    @Override
    public void initialize(Board board, GUIStarter guiStarter) {
        this.board = board;
        this.guiStarter = guiStarter;
        setClickListeners(guiStarter, board);
    }

}
