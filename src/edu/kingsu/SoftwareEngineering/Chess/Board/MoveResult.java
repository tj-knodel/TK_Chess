package edu.kingsu.SoftwareEngineering.Chess.Board;

public class MoveResult {
    public boolean wasSuccessful;
    public boolean isCheckmate;
    public boolean isStalemate;
    public boolean isPromotion;
    public BoardLocation promotionLocation;
    public String notation;
    public int checkmateTeam;
    public int stalemateTeam;

    public MoveResult() {
        this.wasSuccessful = false;
        this.isPromotion = false;
        this.promotionLocation = new BoardLocation(-1, -1);
        this.isCheckmate = false;
        this.isStalemate = false;
        this.checkmateTeam = -1;
        this.stalemateTeam = -1;
        this.notation = "";
    }
}
