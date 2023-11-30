package edu.kingsu.SoftwareEngineering.Chess.Players;

import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;

/**
 * A move class to hold start and end locations as well as the piece to move
 */
public class Move {

    /**
     * The score value of the move.
     */
    public final double score;

    /**
     * The piece that is doing the move.
     */
    public final Piece piece;

    /**
     * The start location of the board.
     */
    public final BoardLocation start;

    /**
     * The end location of the board.
     */
    public final BoardLocation end;

    /**
     * Creates a new move class to hold some information about a chess move.
     * @param piece The piece that is moving.
     * @param start The start location of the move.
     * @param end The end location of the move.
     * @param score The score value of the move.
     */
    public Move(Piece piece, BoardLocation start, BoardLocation end, double score) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.score = score;
    }
}
