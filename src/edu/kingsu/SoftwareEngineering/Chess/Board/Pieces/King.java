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
        return "K";
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
        ArrayList<Move> moves = new ArrayList<>();
        Move endMove = new Move(startMove.column, startMove.row);
        // Top Left
        endMove.column--;
        endMove.row--;
        if (IsMoveValid(board, endMove))
            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);
        // Top
        endMove.row--;
        if (IsMoveValid(board, endMove))
            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);
        // Top Right
        endMove.column++;
        endMove.row--;
        if (IsMoveValid(board, endMove))
            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);
        // Right
        endMove.column++;
        if (IsMoveValid(board, endMove))
            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);
        // Bottom Right
        endMove.column++;
        endMove.row++;
        if (IsMoveValid(board, endMove))
            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);
        // Bottom
        endMove.row++;
        if (IsMoveValid(board, endMove))
            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);
        // Bottom Left
        endMove.column--;
        endMove.row++;
        if (IsMoveValid(board, endMove))
            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);
        // Left
        endMove.column--;
        if (IsMoveValid(board, endMove))
            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);

        return moves;
    }

}
