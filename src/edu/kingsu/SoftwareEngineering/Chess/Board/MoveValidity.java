package edu.kingsu.SoftwareEngineering.Chess.Board;

/**
 * This class handles move checking to check
 * three specific cases. All three are needed
 * for the pieces to function correctly.
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class MoveValidity {
    /**
     * Was a move in the board?
     */
    public boolean isInBoard;

    /**
     * Was the move on another team's location?
     */
    public boolean isOtherTeam;

    /**
     * Was the move on empty space?
     */
    public boolean isEmptySpace;
}
