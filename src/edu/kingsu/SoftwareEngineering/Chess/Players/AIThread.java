package edu.kingsu.SoftwareEngineering.Chess.Players;

import javax.swing.JOptionPane;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;

/**
 * A wrapper to store an AIPlayer
 */
public class AIThread implements Runnable {
    private Player aiPlayer;
    private Board board;
    private Move curMove;
    private GUIStarter guiStarter;

    public AIThread(AIPlayer ai, Board board, GUIStarter guiStarter) {
        aiPlayer = ai;
        this.board = board;
        curMove = null;
        this.guiStarter = guiStarter;
    }

    /**
     * This function will 
     */
    @Override
    public void run() {
        // simple loop to just keep checking if we need a move and update the listener.
        // curMove = aiPlayer.getMove(board);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Move aiMove = aiPlayer.getMove(board);
        MoveResult result = board.applyMove(aiMove.piece, aiMove.start, aiMove.end, true, true);
        if(!result.wasSuccessful) {
            JOptionPane.showConfirmDialog(null, "NOT SUCCESSFUL");
        }
    }

    public Move getMove() {
        return curMove;
    }
}
