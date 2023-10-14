package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * @author Daniell Buchner
 * @version 1.1.0
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
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if(!moveValid.isInBoard)
                break;
            if(!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if(moveValid.isEmptySpace)
                moves.add(new Move(endMove.column, endMove.row));
            if(moveValid.isOtherTeam) {
                moves.add(new Move(endMove.column, endMove.row));
                break;
            }
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
//            else
//                break;
        }
        // Up Right
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row--;
            endMove.column++;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if(!moveValid.isInBoard)
                break;
            if(!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if(moveValid.isEmptySpace)
                moves.add(new Move(endMove.column, endMove.row));
            if(moveValid.isOtherTeam) {
                moves.add(new Move(endMove.column, endMove.row));
                break;
            }
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }
        // Down Left
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row++;
            endMove.column--;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if(!moveValid.isInBoard)
                break;
            if(!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if(moveValid.isEmptySpace)
                moves.add(new Move(endMove.column, endMove.row));
            if(moveValid.isOtherTeam) {
                moves.add(new Move(endMove.column, endMove.row));
                break;
            }
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }
        // Down Right
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row++;
            endMove.column++;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if(!moveValid.isInBoard)
                break;
            if(!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if(moveValid.isEmptySpace)
                moves.add(new Move(endMove.column, endMove.row));
            if(moveValid.isOtherTeam) {
                moves.add(new Move(endMove.column, endMove.row));
                break;
            }
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
        }

        //////////////////////////
        ////////// ROOK //////////
        //////////////////////////
        // Up
        while (IsMoveValid(board, endMove)) {
            endMove.row--;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if(!moveValid.isInBoard)
                break;
            if(!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if(moveValid.isEmptySpace)
                moves.add(new Move(endMove.column, endMove.row));
            if(moveValid.isOtherTeam) {
                moves.add(new Move(endMove.column, endMove.row));
                break;
            }
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
//            else
//                break;
        }
        // Right
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.column++;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if(!moveValid.isInBoard)
                break;
            if(!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if(moveValid.isEmptySpace)
                moves.add(new Move(endMove.column, endMove.row));
            if(moveValid.isOtherTeam) {
                moves.add(new Move(endMove.column, endMove.row));
                break;
            }
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
//            else
//                break;
        }
        // Down
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row++;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if(!moveValid.isInBoard)
                break;
            if(!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if(moveValid.isEmptySpace)
                moves.add(new Move(endMove.column, endMove.row));
            if(moveValid.isOtherTeam) {
                moves.add(new Move(endMove.column, endMove.row));
                break;
            }
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
//            else
//                break;
        }
        // Left
        endMove = new Move(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.column--;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if(!moveValid.isInBoard)
                break;
            if(!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if(moveValid.isEmptySpace)
                moves.add(new Move(endMove.column, endMove.row));
            if(moveValid.isOtherTeam) {
                moves.add(new Move(endMove.column, endMove.row));
                break;
            }
//            if (IsMoveValid(board, endMove))
//                moves.add(new Move(endMove.column, endMove.row));
//            else
//                break;
        }
        return moves;
    }

}
