package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * @author Daniell Buchner
 * @version 1.1.0
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
        return "P";
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
    public ArrayList<BoardLocation> getPossibleMoves(Piece[][] board, BoardLocation startMove) {
        ArrayList<BoardLocation> moves = new ArrayList<>();
        BoardLocation endMove = new BoardLocation(startMove.column, startMove.row);

        if (team == 0) {
            endMove.row++;
        }
        else if (team == 1) {
            endMove.row--;
        }

        MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }

        if (!hasMoved) {
            endMove = new BoardLocation(startMove.column, startMove.row);
            if (team == 0)
                endMove.row += 2;
            else if (team == 1)
                endMove.row -= 2;

            moveValid = IsMoveValidWithoutPiece(board, endMove);
            if (moveValid.isInBoard) {
                if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                    if (moveValid.isEmptySpace) {
                        moves.add(new BoardLocation(endMove.column, endMove.row));
                    }
                    if (moveValid.isOtherTeam) {
                        moves.add(new BoardLocation(endMove.column, endMove.row));
                    }
                }
            }
        }
//
//        if (endMove.row < board.length && endMove.row >= 0) {
//            moves.add(endMove);
//        }
//
//        if (!hasMoved) {
//            Move endMove2 = new Move(startMove.column, startMove.row);
//            if (team == 0)
//                endMove2.row += 2;
//            else if (team == 1)
//                endMove2.row -= 2;
//
//            if (endMove2.row < board.length && endMove2.row >= 0) {
//                moves.add(endMove2);
//            }
//        }
        return moves;
    }

}
