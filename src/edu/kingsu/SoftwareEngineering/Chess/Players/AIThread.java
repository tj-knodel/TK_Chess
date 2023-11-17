package edu.kingsu.SoftwareEngineering.Chess.Players;
import edu.kingsu.SoftwareEngineering.Chess.Board.Board;

/**
 * A wrapper to store an AIPlayer
 */
public class AIThread implements Runnable {
    private Player aiPlayer;
    private Board board;
    private Move curMove;

    public AIThread(AIPlayer ai, Board board) {
        aiPlayer = ai;
        this.board = board;
        curMove = null;
    }
    /**
     * This function will 
     */
    @Override
    public void run() {
        // simple loop to just keep checking if we need a move and update the listener.
        curMove = aiPlayer.getMove(board);
        //Thread.sleep(1000);
    }

    public Move getMove() {
        return curMove;
    }
}
