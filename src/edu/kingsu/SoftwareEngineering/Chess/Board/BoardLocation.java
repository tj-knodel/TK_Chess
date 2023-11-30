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

    /**
     * Creates a board location based on column and row.
     * @param column The column to set to.
     * @param row The row to set to.
     */
    public BoardLocation(int column, int row) {
        this.column = column;
        this.row = row;
    }

    /**
     * Is this class equal to another board location.
     * @param other The other board location to test.
     * @return True if they are the same.
     */
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
