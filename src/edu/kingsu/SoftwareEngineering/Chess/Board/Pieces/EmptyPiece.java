package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class EmptyPiece extends Piece {

    public EmptyPiece() {
        super(-1);
    }

    @Override
    public String getPieceName() {
        return "Empty";
    }

    @Override
    public int getPieceID() {
        return Piece.EMPTY_PIECE;
    }

    @Override
    public Piece copy(int team) {
        return new EmptyPiece();
    }

}
