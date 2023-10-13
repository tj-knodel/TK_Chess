package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;

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
        return "Q";
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

    @Override
    public ArrayList<Move> getPossibleMoves(Piece[][] board, Move startMove) {
        ArrayList<Move> moves = new ArrayList<>();
        Move endMove = new Move(startMove.column, startMove.row);
        //////////////////////////
        ///////// BISHOP /////////
        //////////////////////////
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

        //////////////////////////
        ////////// ROOK //////////
        //////////////////////////
        // Up
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row--;
            if (IsMoveValid(board, endMove))
                moves.add(new Move(endMove.column, endMove.row));
            else
                break;
        }
        // Right
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.column++;
            if (IsMoveValid(board, endMove))
                moves.add(new Move(endMove.column, endMove.row));
            else
                break;
        }
        // Down
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row++;
            if (IsMoveValid(board, endMove))
                moves.add(new Move(endMove.column, endMove.row));
            else
                break;
        }
        // Left
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.column--;
            if (IsMoveValid(board, endMove))
                moves.add(new Move(endMove.column, endMove.row));
            else
                break;
        }
        return moves;
    }

}
