package edu.kingsu.SoftwareEngineering.Chess.GameMode;

/**
 * Abstract class to handle different game modes
 * without rewriting the GameLoop.
 * @author Daniell Buchner
 * @version 0.1.0
 */
public abstract class GameMode {
    /**
     * Switch teams. Really only needed for AI.
     */
    public abstract void switchTeam();

    /**
     * Start the game. This is where callbacks and other stuff happens.
     */
    public abstract void startGame();

    /**
     * End the game. Not used right now.
     */
    public abstract void endGame();
}
