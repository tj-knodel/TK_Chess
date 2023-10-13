package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;

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

    @Override
    public ArrayList<Move> getPossibleMoves(Piece[][] board, Move startMove) {
        ArrayList<Move> moves = new ArrayList<>();
        Move endMove = new Move(startMove.column, startMove.row);
        // Up Left
        while (IsMoveValid(board, endMove)) {
            endMove.row--;
            endMove.column--;
            if (IsMoveValid(board, endMove))
                moves.add(new Move(endMove.column, endMove.row));
            else
                break;
        }
        // Up Right
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row--;
            endMove.column++;
            if (IsMoveValid(board, endMove))
                moves.add(new Move(endMove.column, endMove.row));
        }
        // Down Left
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row++;
            endMove.column--;
            if (IsMoveValid(board, endMove))
                moves.add(new Move(endMove.column, endMove.row));
        }
        // Down Right
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row++;
            endMove.column++;
            if (IsMoveValid(board, endMove))
                moves.add(new Move(endMove.column, endMove.row));
        }
        return moves;
    }

}
