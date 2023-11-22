package edu.kingsu.SoftwareEngineering.Chess.Board;

/**
 * The move result to handle what a move was.
 * @author Daniell Buchner
 * @version 0.2.0
 */
public class MoveResult {
    /**
     * Was a move successful or not.
     */
    private boolean isSuccessful;

    /**
     * If the move results in checkmate.
     */
    private boolean isCheckmate;

    /**
     * If the move results in stalemate.
     */
    private boolean isStalemate;

    /**
     * If the move results in promotion.
     */
    private boolean isPromotion;

    /**
     * The location for the piece being promoted.
     * @see BoardLocation
     */
    private BoardLocation promotionLocation;

    /**
     * The notation of the current move in PGN format.
     */
    private String notation;

    /**
     * The team that is in checkmate.
     */
    private int checkmateTeam;

    /**
     * The team that is in stalemate.
     */
    private int stalemateTeam;

    /**
     * Constructor to set defaults.
     */
    public MoveResult() {
        setSuccessful(false);
        setPromotion(false);
        setPromotionLocation(new BoardLocation(-1, -1));
        setCheckmate(false);
        setStalemate(false);
        setCheckmateTeam(-1);
        setStalemateTeam(-1);
        setNotation("");
    }

    /**
     * Gets if the move is successful.
     * @return True if successful.
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

    /**
     * Set a move to be successful or not.
     * @param successful The value to set the success to.
     */
    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    /**
     * Gets if the move results in checkmate.
     * @return True if resulting in checkmate.
     */
    public boolean isCheckmate() {
        return isCheckmate;
    }

    /**
     * Set a move to be resulting in checkmate.
     * @param checkmate the value to set the checkmate to.
     */
    public void setCheckmate(boolean checkmate) {
        isCheckmate = checkmate;
    }

    /**
     * Gets if the move results in stalemate.
     * @return True if resulting in stalemate.
     */
    public boolean isStalemate() {
        return isStalemate;
    }

    /**
     * Set a move to be resulting in stalemate.
     * @param stalemate The value to set the stalemate to.
     */
    public void setStalemate(boolean stalemate) {
        isStalemate = stalemate;
    }

    /**
     * Gets if the move results in promotion.
     * @return True if resulting in promotion.
     */
    public boolean isPromotion() {
        return isPromotion;
    }

    /**
     * Set a move to be resulting in promotion.
     * @param promotion The value to set the isPromotion to.
     */
    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }

    /**
     * Gets the promotion location of a move.
     * @see BoardLocation
     * @return The promotion location.
     */
    public BoardLocation getPromotionLocation() {
        return promotionLocation;
    }

    /**
     * Sets the promotion location for a move.
     * @see BoardLocation
     * @param promotionLocation The promotion location to set for the move.
     */
    public void setPromotionLocation(BoardLocation promotionLocation) {
        this.promotionLocation = promotionLocation;
    }

    /**
     * Gets a move's PGN notation.
     * @return The PGN notation of a move.
     */
    public String getNotation() {
        return notation;
    }

    /**
     * Sets a move's PGN notation.
     * @param notation The PGN notation to set for the current move.
     */
    public void setNotation(String notation) {
        this.notation = notation;
    }

    /**
     * Gets the team in checkmate for the move.
     * @return The team in checkmate.
     */
    public int getCheckmateTeam() {
        return checkmateTeam;
    }

    /**
     * Sets a move's team to be in checkmate.
     * @param checkmateTeam The team to be in checkmate.
     */
    public void setCheckmateTeam(int checkmateTeam) {
        this.checkmateTeam = checkmateTeam;
    }

    /**
     * Gets the team in stalemate for the move.
     * @return The team in stalemate.
     */
    public int getStalemateTeam() {
        return stalemateTeam;
    }

    /**
     * Sets a move's team to be in stalemate.
     * @param stalemateTeam The team to be in stalemate.
     */
    public void setStalemateTeam(int stalemateTeam) {
        this.stalemateTeam = stalemateTeam;
    }
}
