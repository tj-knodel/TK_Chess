package edu.kingsu.SoftwareEngineering.Chess.Players;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;

/**
 * A wrapper to store an AIPlayer
 */
public class AIThread implements Runnable {
    private Player aiPlayer;
    private Board board;
    private Move curMove;
    private GameLoop gameLoop;

    public AIThread(AIPlayer ai, Board board, GameLoop gameLoop) {
        aiPlayer = ai;
        this.board = board;
        curMove = null;
        this.gameLoop = gameLoop;
    }

    /**
     * This function will 
     */
    @Override
    public void run() {
        // simple loop to just keep checking if we need a move and update the listener.
        // curMove = aiPlayer.getMove(board);
        gameLoop.setLoadingPawn(true);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Move aiMove = aiPlayer.getMove(board);
        gameLoop.setLoadingPawn(false);
        MoveResult result = board.applyMoveUpdateGUI(aiMove.piece, aiMove.start, aiMove.end);
        if (!result.isSuccessful()) {
            //            JOptionPane.showConfirmDialog(null, "NOT SUCCESSFUL");
        }
    }

    public Move getMove() {
        return curMove;
    }
}
