package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * The Bishop chess piece.
 * @author Daniell Buchner
 * @version 1.1.0
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
        return "B";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.BISHOP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece copy(int team) {
        return new Bishop(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Piece[][] board, BoardLocation startMove) {
        ArrayList<BoardLocation> moves = new ArrayList<>();
        BoardLocation endMove = new BoardLocation(startMove.column, startMove.row);
        // Up Left
        while (IsMoveValid(board, endMove)) {
            endMove.row--;
            endMove.column--;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if (!moveValid.isInBoard)
                break;
            if (!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if (moveValid.isEmptySpace)
                moves.add(new BoardLocation(endMove.column, endMove.row));
            if (moveValid.isOtherTeam) {
                moves.add(new BoardLocation(endMove.column, endMove.row));
                break;
            }
            // if (IsMoveValid(board, endMove))
            // moves.add(new Move(endMove.column, endMove.row));
            // else
            // break;
        }
        // Up Right
        endMove = new BoardLocation(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row--;
            endMove.column++;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if (!moveValid.isInBoard)
                break;
            if (!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if (moveValid.isEmptySpace)
                moves.add(new BoardLocation(endMove.column, endMove.row));
            if (moveValid.isOtherTeam) {
                moves.add(new BoardLocation(endMove.column, endMove.row));
                break;
            }
            // if (IsMoveValid(board, endMove))
            // moves.add(new Move(endMove.column, endMove.row));
        }
        // Down Left
        endMove = new BoardLocation(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row++;
            endMove.column--;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if (!moveValid.isInBoard)
                break;
            if (!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if (moveValid.isEmptySpace)
                moves.add(new BoardLocation(endMove.column, endMove.row));
            if (moveValid.isOtherTeam) {
                moves.add(new BoardLocation(endMove.column, endMove.row));
                break;
            }
            // if (IsMoveValid(board, endMove))
            // moves.add(new Move(endMove.column, endMove.row));
        }
        // Down Right
        endMove = new BoardLocation(startMove.column, startMove.row);
        while (IsMoveValid(board, endMove)) {
            endMove.row++;
            endMove.column++;
            // Keep adding moves as long as we have empty space, otherwise add
            // the first enemy player move and then break out of the loop.
            MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
            if (!moveValid.isInBoard)
                break;
            if (!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                break;
            if (moveValid.isEmptySpace)
                moves.add(new BoardLocation(endMove.column, endMove.row));
            if (moveValid.isOtherTeam) {
                moves.add(new BoardLocation(endMove.column, endMove.row));
                break;
            }
            // if (IsMoveValid(board, endMove))
            // moves.add(new Move(endMove.column, endMove.row));
        }
        return moves;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moved() {
    }

}
