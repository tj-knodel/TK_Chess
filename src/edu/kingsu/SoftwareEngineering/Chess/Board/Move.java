package edu.kingsu.SoftwareEngineering.Chess.Board;
/**
 * A small function that is used to store the x, y coordinates of a move.
 * @author Thaler Knodel
 * @version 0.1.0
 */
public class Move {
    private int x, y;
    /**
     * Creates a new move with the given coordinates
     * @param x the column of the move
     * @param y the row of the move
     */
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Gets the column of the move
     * @return the column number of the move
     */
    public int getX() {
        return x;
    }
    /**
     * Gets the row of the move
     * @return the row number of the move
     */
    public int getY() {
        return y;
    }
}
