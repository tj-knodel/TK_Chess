package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class King extends Piece {

    public King(int team) {
        super(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "King";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.KING;
    }

    @Override
    public Piece copy(int team) {
        return new King(team);
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Piece[][] board, Move startMove) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPossibleMoves'");
    }

}
