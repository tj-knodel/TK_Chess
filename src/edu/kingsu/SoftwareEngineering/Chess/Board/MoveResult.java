package edu.kingsu.SoftwareEngineering.Chess.Board;

public class MoveResult {
    public boolean wasSuccessful;
    public boolean isCheckmate;
    public boolean isStalemate;
    public int checkmateTeam;
    public int stalemateTeam;

    public MoveResult() {
        this.wasSuccessful = false;
        this.isCheckmate = false;
        this.isStalemate = false;
        this.checkmateTeam = -1;
        this.stalemateTeam = -1;
    }
}
