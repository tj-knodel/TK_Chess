package edu.kingsu.SoftwareEngineering.Chess.Players;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
/**
 * @author Thaler Knodel
 * @version 0.1.0
 */
public class AIPlayer extends Player {

    private int difficulty;

    /**
     * Creates a new AIPlayer with the given difficulty level
     * @param difficulty the level of depth for the AI algorithm
     * @param colour the colour of the players pieces
     */
    public AIPlayer(int difficulty, int colour) {
        // chain constructor with the default name of "Computer"
        this(difficulty, colour, "Computer");
    }

    /**
     * Creates a new AIPlayer with the given difficulty level and gives it a specified name
     * @param difficulty the level of depth for the AI algorithm
     * @param colour the colour of the players pieces
     * @param name the name of the player
     */
    public AIPlayer(int difficulty, int colour, String name) {
        this.colour = colour;
        this.difficulty = difficulty;
        this.name = name;
    }
    /**
     * Gets a move from the AI
     * @return a move
     */
    public BoardLocation getMove() {
        return null;
    }

    /**
     * The minimax algorithm for the AI player
     * @return
     */
    private int minimax(Board board, int depth, boolean player) {
        return 0;
    }
}
