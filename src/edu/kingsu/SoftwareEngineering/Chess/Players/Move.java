package edu.kingsu.SoftwareEngineering.Chess.Players;

import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;

/**
 * A move class to hold start and end locations as well as the piece to move
 */
public class Move {
    public final int score;
    public final Piece piece;
    public final BoardLocation start, end;
    public Move(Piece piece, BoardLocation start, BoardLocation end, int score) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.score = score;
    }    
}
