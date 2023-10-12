package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

/**
 * The generic piece representation. Does not hold data
 * besides the piece id for the pieces to access.
 * 
 * @author Daniell Buchner
 * @version 0.2.0
 */
public abstract class Piece {

    /**
     * The team the piece is on. 1 or 0 for white/black
     */
    protected int team;

    /**
     * Constructor to set the team for the pieces.
     * 
     * @param team Which team the piece is on.
     */
    protected Piece(int team) {
        this.team = team;
    }

    /**
     * Get the string representation of the piece name.
     * 
     * @return The name of the piece.
     */
    public abstract String getPieceName();

    /**
     * Get the piece id that can be used for comparing and
     * knowing which piece is where.
     * 
     * @return The id of the piece.
     */
    public abstract int getPieceID();

    /**
     * Creates a new instance of the piece.
     * 
     * @param team The team the piece was on.
     * @return The new piece.
     */
    public abstract Piece copy(int team);

    /**
     * Gets the team the piece is on.
     * 
     * @return The team the piece is on.
     */
    public int getTeam() {
        return team;
    }

    protected static int EMPTY_PIECE = -1;
    protected static int PAWN = 0;
    protected static int ROOK = 1;
    protected static int KNIGHT = 2;
    protected static int BISHOP = 3;
    protected static int QUEEN = 4;
    protected static int KING = 5;

}
