package edu.kingsu.SoftwareEngineering.Chess.GameMode;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.MoveController;
import edu.kingsu.SoftwareEngineering.Chess.Players.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * {@inheritDoc}
 */
public class PlayerVSAIGameMode extends GameMode {

    private MoveController moveController;
    private AIThread aithread;
    private int teamTurn;

    /**
     * Creates a new player vs ai game mode and sets the AI to black
     * @param aiDifficulty
     */
    public PlayerVSAIGameMode(int aiDifficulty) {
        this.moveController = new MoveController();
        teamTurn = Team.WHITE_TEAM;
        //aithread = new AIThread(new AIPlayer(aiDifficulty, Team.BLACK_TEAM));
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void switchTeam() {
        teamTurn = (teamTurn == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        System.out.println("Team is now: " + teamTurn);
    }

    /**
     * Sets the click listeners to a GUI so that moves can be registered.
     * @param guiStarter The GUIStarted to listen for clicks to.
     * @param board The board to play on.
     */
    public void setClickListeners(GUIStarter guiStarter, Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                chessTile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (moveController.chessTileClick(board, teamTurn, chessTile.row,
                                chessTile.column)) {
                            moveController.sendMovesToBoard(board);
                            switchTeam();
                            gameLoop.sendUpdateBoardState();
                            // guiStarter.chessUIManager.drawBoard(board.getBoard());
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

}
