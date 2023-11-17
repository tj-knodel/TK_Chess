package edu.kingsu.SoftwareEngineering.Chess.GameMode;

import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;

/**
 * Abstract class to handle different game modes
 * without rewriting the GameLoop.
 * @author Daniell Buchner
 * @version 0.1.0
 */
public abstract class GameMode {

    /**
     * The player vs player game mode type.
     */
    public static final int PLAYER_VS_PLAYER_GAME_MODE = 0;

    protected GameLoop gameLoop;

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

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
