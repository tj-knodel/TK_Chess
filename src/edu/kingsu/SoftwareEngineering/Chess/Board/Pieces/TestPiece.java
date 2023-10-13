package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;

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
    public ArrayList<Move> getPossibleMoves(Piece[][] board, Move startMove) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPossibleMoves'");
    }

}
