package edu.kingsu.SoftwareEngineering.Chess.GameMode;

import org.w3c.dom.events.MouseEvent;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.MoveController;
import edu.kingsu.SoftwareEngineering.Chess.Players.AIPlayer;
import edu.kingsu.SoftwareEngineering.Chess.Players.AIThread;
import edu.kingsu.SoftwareEngineering.Chess.Players.Move;

public class AIVSAIGameMode extends GameMode {
    private MoveController moveController;
    private AIThread ai;
    private Thread runningThread;
    private int difficulty;
    private int teamTurn;
    private Board board;

    private boolean gameOver = false;

    /**
     * Creates a new player vs ai game mode and sets the AI to black
     * @param aiDifficulty
     */
    public AIVSAIGameMode(int aiDifficulty) {
        this.moveController = new MoveController();
        difficulty = aiDifficulty;
        teamTurn = Team.BLACK_TEAM;
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void switchTeam() {
        teamTurn = (teamTurn == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        // System.out.println("Team is now: " + teamTurn);
        runAI();
    }

    /**
     * Sets the click listeners to a GUI so that moves can be registered.
     * @param guiStarter The GUIStarted to listen for clicks to.
     * @param board The board to play on.
     */
    public void setClickListeners(GUIStarter guiStarter, Board board) {
        // super hacky but I'm lazy
        this.board = board;
        switchTeam();
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
        gameLoop.sendUpdateBoardState();
        // if (teamTurn == aiTeam) {

        ai = new AIThread(new AIPlayer(difficulty, teamTurn), board);
        runningThread = new Thread(ai);
        runningThread.start();
        try {
            runningThread.sleep(1000L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            runningThread.join();
            Move aiMove = ai.getMove();
            if (aiMove == null) {
                gameOver = true;
                // System.out.println("We are done the game");
                // System.exit(0);
            }
            board.applyMove(aiMove.piece, aiMove.start, aiMove.end);
        } catch (Exception e) {
            System.err.println("oopsies with the AIThread");
        }
        switchTeam();
    }
    // }
}
