package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

/**
 * The type of game that is currently being played.
 * @author Daniell Buchner
 * @version 0.1.0
 */
public enum GameType {
    /**
     * The Player vs Player was selected.
     */
    PLAYER_VS_PLAYER,

    /**
     * The White Player vs Black AI was selected.
     */
    WHITE_PLAYER_VS_BLACK_AI,

    /**
     * The White AI vs Black Player was selected.
     */
    WHITE_AI_VS_BLACK_PLAYER,

    /**
     * The AI vs AI was selected.
     */
    AI_VS_AI,

    /**
     * The tutorial was selected.
     */
    TUTORIAL
}
