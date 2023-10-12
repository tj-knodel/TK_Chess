package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class Queen extends Piece {

    public Queen(int team) {
        super(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "Queen";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.QUEEN;
    }

    @Override
    public Piece copy(int team) {
        return new Queen(team);
    }

}
