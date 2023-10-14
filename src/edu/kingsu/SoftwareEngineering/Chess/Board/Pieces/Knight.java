package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class Knight extends Piece {

    public Knight(int team) {
        super(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "N";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.KNIGHT;
    }

    @Override
    public Piece copy(int team) {
        return new Knight(team);
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Piece[][] board, Move startMove) {
        ArrayList<Move> moves = new ArrayList<>();

        // Up Left
        Move endMove = new Move(startMove.column, startMove.row);
        if (IsMoveValid(board, endMove)) {
            endMove.row -= 2;
            endMove.column--;
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
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }

        // Left Up
        endMove = new Move(startMove.column, startMove.row);
        if (IsMoveValid(board, endMove)) {
            endMove.row--;
            endMove.column -= 2;
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
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }

        // Up Right
        endMove = new Move(startMove.column, startMove.row);
        if (IsMoveValid(board, endMove)) {
            endMove.row -= 2;
            endMove.column++;
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
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }

        // Right Up
        endMove = new Move(startMove.column, startMove.row);
        if (IsMoveValid(board, endMove)) {
            endMove.row--;
            endMove.column += 2;
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
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }

        // Down Right
        endMove = new Move(startMove.column, startMove.row);
        if (IsMoveValid(board, endMove)) {
            endMove.row += 2;
            endMove.column++;
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
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }

        // Right Down
        endMove = new Move(startMove.column, startMove.row);
        if (IsMoveValid(board, endMove)) {
            endMove.row++;
            endMove.column += 2;
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
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }

        // Down Left
        endMove = new Move(startMove.column, startMove.row);
        if (IsMoveValid(board, endMove)) {
            endMove.row += 2;
            endMove.column--;
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
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }

        // Left Down
        endMove = new Move(startMove.column, startMove.row);
        if (IsMoveValid(board, endMove)) {
            endMove.row++;
            endMove.column -= 2;
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
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }

        return moves;
    }

}
