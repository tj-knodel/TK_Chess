package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;

public class TestPiece extends Piece {

    public TestPiece() {
        super(-2);
    }

    @Override
    public String getPieceName() {
        return "T";
    }

    @Override
    public int getPieceID() {
        return -2;
    }

    @Override
    public Piece copy(int team) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copy'");
    }

    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Piece[][] board, BoardLocation startMove) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPossibleMoves'");
    }

    @Override
    public void moved() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moved'");
    }

}
