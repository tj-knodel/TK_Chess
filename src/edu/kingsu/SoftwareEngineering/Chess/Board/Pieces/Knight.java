package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class Knight extends Piece {

    public Knight(int team) {
        super(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "Knight";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.KNIGHT;
    }

    @Override
    public Piece copy(int team) {
        return new Knight(team);
    }

}
