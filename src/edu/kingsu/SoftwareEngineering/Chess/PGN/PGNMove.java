package edu.kingsu.SoftwareEngineering.Chess.PGN;

/**
 * The class responsible for holding the PGN notation moves
 * to move, and as well holding a comment for useful cases like
 * the tutorial mode.
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class PGNMove {
    private String moveString;
    private String comment;

    /**
     * Create a PGNMove with comment as null.
     * @param moveString The PGN notation of the move to store.
     */
    public PGNMove(String moveString) {
        this.moveString = moveString;
        this.comment = null;
    }

    /**
     * Create a PGNMove with a comment and a move.
     * @param moveString The PGN notation of the move to store.
     * @param comment The comment to store for this specific move.
     */
    public PGNMove(String moveString, String comment) {
        this(moveString);
        this.comment = comment;
    }

    /**
     * Gets the PGN notation move in a string form.
     * @return The PGN notation move.
     */
    public String getMoveString() {
        return moveString;
    }

    /**
     * Gets the comment associated with the PGN notation move.
     * @return The comment with this current move.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment. Useful for the PGNReader.
     * @param comment The comment to set for this move.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Checks if comment is valid.
     * @return True if comment is not null, false otherwise.
     */
    public boolean hasComment() {
        return comment != null;
    }
}
