package edu.kingsu.SoftwareEngineering.Chess.Board;

/**
 * The location on the "board" that something can have.
 * @author Daniell Buchner
 * @version 0.1.1
 */
public class BoardLocation {
    /**
     * The column position.
     */
    public int column;

    /**
     * The row position.
     */
    public int row;

    public BoardLocation(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public boolean isEqual(BoardLocation other) {
        return other.row == row && other.column == column;
    }

    @Override
    public String toString() {
        return "BoardLocation{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }
}
