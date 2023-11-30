package edu.kingsu.SoftwareEngineering.Chess.Players;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;

/**
 * A wrapper to store an AIPlayer
 */
public class AIThread implements Runnable {

    /**
     * The AI player to get the move for.
     */
    private Player aiPlayer;

    /**
     * The board class to reference helper functions.
     */
    private Board board;

    /**
     * The current move.
     */
    private Move curMove;

    /**
     * The gameloop to reference for helper functions.
     */
    private GameLoop gameLoop;

    /**
     * Creates a new AI thread to run when requested.
     * @param ai The AIPlayer to set to.
     * @param board The board to reference.
     * @param gameLoop The gameloop to reference.
     */
    public AIThread(AIPlayer ai, Board board, GameLoop gameLoop) {
        aiPlayer = ai;
        this.board = board;
        curMove = null;
        this.gameLoop = gameLoop;
    }

    /**
     * This function will run the ai and get the possible move.
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

    /**
     * Get the current move of the ai's choice.
     * @return The current move.
     */
    public Move getMove() {
        return curMove;
    }
}
