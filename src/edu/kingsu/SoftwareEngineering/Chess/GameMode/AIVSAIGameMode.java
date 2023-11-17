package edu.kingsu.SoftwareEngineering.Chess.GameMode;

import javax.swing.JOptionPane;

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
    private GUIStarter guiStarter;

    private boolean gameOver = false;

    /**
     * Creates a new player vs ai game mode and sets the AI to black
     * @param aiDifficulty
     */
    public AIVSAIGameMode(int aiDifficulty) {
        difficulty = aiDifficulty;
        teamTurn = Team.WHITE_TEAM;
        // this.moveController = new MoveController();
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void switchTeam() {
        teamTurn = (teamTurn == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        // System.out.println("Team is now: " + teamTurn);
        // runAI();
    }

    /**
     * Sets the click listeners to a GUI so that moves can be registered.
     * @param guiStarter The GUIStarted to listen for clicks to.
     * @param board The board to play on.
     */
    protected void setClickListeners(GUIStarter guiStarter, Board board) {
        // super hacky but I'm lazy

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
        ai = new AIThread(new AIPlayer(difficulty, board.getTeamTurn()), board, guiStarter);
        runningThread = new Thread(ai);
        runningThread.start();
    }

    @Override
    public void getNextTurn() {
        runAI();
    }
    // }

    @Override
    public void initialize(Board board, GUIStarter guiStarter) {
        this.board = board;
        this.guiStarter = guiStarter;
        // switchTeam();
        runAI();
    }
}
