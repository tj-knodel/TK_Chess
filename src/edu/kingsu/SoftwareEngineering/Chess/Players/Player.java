package edu.kingsu.SoftwareEngineering.Chess.Players;
import edu.kingsu.SoftwareEngineering.Chess.Board;

public abstract class Player {
    private int colour;
    private String name;
    public abstract Move getMove();
}
