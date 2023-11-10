package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;

public class TestPiece extends Piece {

    public TestPiece() {
        super(-2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "T";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return -2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece copy(int team) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copy'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Board boardClass, Piece[][] board, BoardLocation startMove, boolean extraCheck) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPossibleMoves'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moved() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moved'");
    }

}
