package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class Bishop extends Piece {

    public Bishop(int team) {
        super(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "Bishop";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.BISHOP;
    }

    @Override
    public Piece copy(int team) {
        return new Bishop(team);
    }

}
