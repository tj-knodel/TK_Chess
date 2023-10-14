package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * @author Daniell Buchner
 * @version 1.1.0
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
        // Keep adding moves as long as we have empty space, otherwise add
        // the first enemy player move and then break out of the loop.
        MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
        if(moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new Move(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new Move(endMove.column, endMove.row));
                }
            }
        }
//        if (IsMoveValid(board, endMove))
//            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);

        // Top
        endMove.row--;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if(moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new Move(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new Move(endMove.column, endMove.row));
                }
            }
        }
//        if (IsMoveValid(board, endMove))
//            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);

        // Top Right
        endMove.column++;
        endMove.row--;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if(moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new Move(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new Move(endMove.column, endMove.row));
                }
            }
        }
//        if (IsMoveValid(board, endMove))
//            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);

        // Right
        endMove.column++;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if(moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new Move(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new Move(endMove.column, endMove.row));
                }
            }
        }
//        if (IsMoveValid(board, endMove))
//            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);

        // Bottom Right
        endMove.column++;
        endMove.row++;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if(moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new Move(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new Move(endMove.column, endMove.row));
                }
            }
        }
//        if (IsMoveValid(board, endMove))
//            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);

        // Bottom
        endMove.row++;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if(moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new Move(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new Move(endMove.column, endMove.row));
                }
            }
        }
//        if (IsMoveValid(board, endMove))
//            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);

        // Bottom Left
        endMove.column--;
        endMove.row++;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if(moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new Move(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new Move(endMove.column, endMove.row));
                }
            }
        }
//        if (IsMoveValid(board, endMove))
//            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);

        // Left
        endMove.column--;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if(moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new Move(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new Move(endMove.column, endMove.row));
                }
            }
        }
//        if (IsMoveValid(board, endMove))
//            moves.add(new Move(endMove.column, endMove.row));
        endMove = new Move(startMove.column, startMove.row);

        return moves;
    }

}
