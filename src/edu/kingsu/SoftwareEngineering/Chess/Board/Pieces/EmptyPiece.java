package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;

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

    @Override
    public ArrayList<Move> getPossibleMoves(Piece[][] board, Move startMove) {
        throw new UnsupportedOperationException("Empty Piece has no possible moves!");
    }

}
