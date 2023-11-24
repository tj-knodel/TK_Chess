package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;

/**
 * @author Daniell Buchner
 * @version 1.1.0
 */
public class Pawn extends Piece {

    /**
     * Has the pawn moved once or not.
     */
    private boolean hasMoved = false;

    /**
     * Creates a pawn with a specific team.
     * @param team The team the pawn should be with.
     */
    public Pawn(int team) {
        super(team);
        value = 1;
    }

    /**
     * Creates a pawn with a specific team.
     * @param team The team the pawn should be with.
     * @param hasMoved Has the pawn moved or not.
     */
    public Pawn(int team, boolean hasMoved) {
        super(team);
        this.hasMoved = hasMoved;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece copy(int team) {
        return new Pawn(team, hasMoved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Board boardClass, Piece[][] board, BoardLocation startMove,
            boolean extraCheck) {
        ArrayList<BoardLocation> moves = new ArrayList<>();
        BoardLocation endMove = new BoardLocation(startMove.column, startMove.row);

        // Up/Down
        if (team == 0) {
            endMove.row++;
        } else if (team == 1) {
            endMove.row--;
        }

        MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!moveValid.isOtherTeam && moveValid.isEmptySpace) {
                if (moveValid.isEmptySpace) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }

                if (team == 0) {
                    endMove.row++;
                } else if (team == 1) {
                    endMove.row--;
                }

                moveValid = IsMoveValidWithoutPiece(board, endMove);
                if (moveValid.isInBoard && !hasMoved) {
                    if (!moveValid.isOtherTeam && moveValid.isEmptySpace) {
                        if (moveValid.isEmptySpace) {
                            moves.add(new BoardLocation(endMove.column, endMove.row));
                        }
                        if (moveValid.isOtherTeam) {
                            moves.add(new BoardLocation(endMove.column, endMove.row));
                        }
                    }
                }
            }
        }

        // Special move
        BoardLocation lastPieceMoveLocation = boardClass.getCurrentMoveLocation();
        if (lastPieceMoveLocation != null) {
            Piece lastMovePiece = board[lastPieceMoveLocation.row][lastPieceMoveLocation.column];
            if (lastMovePiece instanceof Pawn
                    && Math.abs(boardClass.getLastMoveLocation().row - lastPieceMoveLocation.row) == 2) {
                endMove = new BoardLocation(startMove.column, startMove.row);
                int targetRow = 0;
                if (team == Team.WHITE_TEAM)
                    targetRow = startMove.row - 1;
                else
                    targetRow = startMove.row + 1;
                if (lastPieceMoveLocation.row == endMove.row) {
                    if (lastPieceMoveLocation.column == endMove.column - 1) {
                        endMove = new BoardLocation(startMove.column - 1, targetRow);
                        moveValid = IsMoveValidWithoutPiece(board, endMove);
                        if (moveValid.isInBoard) {
                            moves.add(new BoardLocation(startMove.column - 1, targetRow));
                        }
                    } else if (lastPieceMoveLocation.column == endMove.column + 1) {
                        endMove = new BoardLocation(startMove.column + 1, targetRow);
                        moveValid = IsMoveValidWithoutPiece(board, endMove);
                        if (moveValid.isInBoard) {
                            moves.add(new BoardLocation(startMove.column + 1, targetRow));
                        }
                    }
                }
            }
        }

        // Up Right/Down Right
        endMove = new BoardLocation(startMove.column, startMove.row);
        if (team == 0) {
            endMove.row++;
            endMove.column++;
        } else if (team == 1) {
            endMove.row--;
            endMove.column++;
        }

        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!moveValid.isEmptySpace && moveValid.isOtherTeam) {
                if (moveValid.isEmptySpace) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }

        // Up Left/Down Left
        endMove = new BoardLocation(startMove.column, startMove.row);
        if (team == 0) {
            endMove.row++;
            endMove.column--;
        } else if (team == 1) {
            endMove.row--;
            endMove.column--;
        }

        moveValid = IsMoveValidWithoutPiece(board, endMove);
        if (moveValid.isInBoard) {
            if (!moveValid.isEmptySpace && moveValid.isOtherTeam) {
                if (moveValid.isEmptySpace) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
                if (moveValid.isOtherTeam) {
                    moves.add(new BoardLocation(endMove.column, endMove.row));
                }
            }
        }
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
