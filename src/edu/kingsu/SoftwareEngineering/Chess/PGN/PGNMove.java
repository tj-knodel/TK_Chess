package edu.kingsu.SoftwareEngineering.Chess.PGN;

public class PGNMove {
    private String moveString;
    private String comment;

    public PGNMove(String moveString) {
        this.moveString = moveString;
        this.comment = null;
    }

    public PGNMove(String moveString, String comment) {
        this(moveString);
        this.comment = comment;
    }

    public String getMoveString() {
        return moveString;
    }

    public String getComment() {
        return comment;
    }
}
