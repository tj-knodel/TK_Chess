package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class EmptyPiece extends Piece {

    /**
     * {@inheritDoc}
     */
    public EmptyPiece() {
        super(-1);
        value = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "-";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.EMPTY_PIECE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece copy(int team) {
        return new EmptyPiece();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Board boardClass, Piece[][] board, BoardLocation startMove) {
        throw new UnsupportedOperationException("Empty Piece has no possible moves!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moved() {
    }

}
