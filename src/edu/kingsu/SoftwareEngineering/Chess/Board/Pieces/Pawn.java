package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class Pawn extends Piece {

    private boolean hasMoved = false;

    public Pawn(int team) {
        super(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "Pawn";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.PAWN;
    }

    @Override
    public Piece copy(int team) {
        return new Pawn(team);
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Piece[][] board, Move startMove) {
        ArrayList<Move> moves = new ArrayList<>();
        Move endMove = new Move(startMove.column, startMove.row);
        if (team == 0)
            endMove.row++;
        else if (team == 1)
            endMove.row--;

        if (endMove.row < board.length && endMove.row >= 0) {
            moves.add(endMove);
        }

        if (!hasMoved) {
            Move endMove2 = new Move(startMove.column, startMove.row);
            if (team == 0)
                endMove2.row += 2;
            else if (team == 1)
                endMove2.row -= 2;

            if (endMove2.row < board.length && endMove2.row >= 0) {
                moves.add(endMove2);
            }
        }
        return moves;
    }

}
