package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class Rook extends Piece {

    public Rook(int team) {
        super(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "Rook";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.ROOK;
    }

    @Override
    public Piece copy(int team) {
        return new Rook(team);
    }

}
