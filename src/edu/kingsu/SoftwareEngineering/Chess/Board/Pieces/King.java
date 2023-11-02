package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * @author Daniell Buchner
 * @version 1.1.0
 */
public class King extends Piece {

    boolean hasMoved;
    public boolean inCheck = false;

    public King(int team) {
        super(team);
        hasMoved = false;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece copy(int team) {
        King k = new King(team);
        k.inCheck = inCheck;
        return k;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Piece[][] board, BoardLocation startMove) {
        ArrayList<BoardLocation> moves = new ArrayList<>();
        BoardLocation endMove = new BoardLocation(startMove.column, startMove.row);

        // Top Left
        endMove.column--;
        endMove.row--;
        // Keep adding moves as long as we have empty space, otherwise add
        // the first enemy player move and then break out of the loop.
        MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }
        // if (IsMoveValid(board, endMove))
        // moves.add(new Move(endMove.column, endMove.row));
        endMove = new BoardLocation(startMove.column, startMove.row);

        // Top
        endMove.row--;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }
        // if (IsMoveValid(board, endMove))
        // moves.add(new Move(endMove.column, endMove.row));
        endMove = new BoardLocation(startMove.column, startMove.row);

        // Top Right
        endMove.column++;
        endMove.row--;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }
        // if (IsMoveValid(board, endMove))
        // moves.add(new Move(endMove.column, endMove.row));
        endMove = new BoardLocation(startMove.column, startMove.row);

        // Right
        endMove.column++;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }
        // if (IsMoveValid(board, endMove))
        // moves.add(new Move(endMove.column, endMove.row));
        endMove = new BoardLocation(startMove.column, startMove.row);

        // Bottom Right
        endMove.column++;
        endMove.row++;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }
        // if (IsMoveValid(board, endMove))
        // moves.add(new Move(endMove.column, endMove.row));
        endMove = new BoardLocation(startMove.column, startMove.row);

        // Bottom
        endMove.row++;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }
        // if (IsMoveValid(board, endMove))
        // moves.add(new Move(endMove.column, endMove.row));
        endMove = new BoardLocation(startMove.column, startMove.row);

        // Bottom Left
        endMove.column--;
        endMove.row++;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }
        // if (IsMoveValid(board, endMove))
        // moves.add(new Move(endMove.column, endMove.row));
        endMove = new BoardLocation(startMove.column, startMove.row);

        // Left
        endMove.column--;
        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!(!moveValid.isOtherTeam && !moveValid.isEmptySpace)) {
                if (moveValid.isEmptySpace)
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }
        // if (IsMoveValid(board, endMove))
        // moves.add(new Move(endMove.column, endMove.row));
        endMove = new BoardLocation(startMove.column, startMove.row);

        return moves;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moved() {
        hasMoved = true;
    }

}
