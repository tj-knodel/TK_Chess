package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;

/**
 * @author Daniell Buchner
 * @version 1.2.0
 */
public class King extends Piece {

    /**
     * Has the king moved yet or not.
     */
    private boolean hasMoved = false;

    /**
     * Is the king currently in check.
     */
    public boolean inCheck = false;

    /**
     * Creates a king for a team.
     * @param team The team the king should be.
     */
    public King(int team) {
        super(team);
        // picking an arbitrarily high value.
        value = 100;
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
     * Gets if the king is in check.
     * @return True if the king is in check.
     */
    public boolean getInCheck() {
        return inCheck;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece copy(int team) {
        King k = new King(team);
        k.inCheck = inCheck;
        k.hasMoved = hasMoved;
        return k;
    }

    /**
     * Checks if you can castle short side.
     * @param board The board to check on.
     * @return True if you can castle short side.
     */
    private boolean canCastleShortSide(Board board) {
        if (hasMoved)
            return false;
        Piece[][] boardCopy = board.getBoard();
        BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(board.getBoard(), team, Piece.KING).get(0);
        BoardLocation loc1 = new BoardLocation(kingLocation.column + 1, kingLocation.row);
        BoardLocation loc2 = new BoardLocation(kingLocation.column + 2, kingLocation.row);
        Piece piece1 = boardCopy[loc1.row][loc1.column];
        Piece piece2 = boardCopy[loc2.row][loc2.column];

        BoardLocation rookPos = new BoardLocation(kingLocation.column + 3, kingLocation.row);
        Piece rookPiece = boardCopy[rookPos.row][rookPos.column];

        if (!(piece1 instanceof EmptyPiece) || !(piece2 instanceof EmptyPiece) || !(rookPiece instanceof Rook))
            return false;

        if (((Rook) rookPiece).hasMoved())
            return false;

        for (BoardLocation teamLoc : board.getBoardLocationsForTeam(boardCopy, Team.getOtherTeam(team))) {
            if (boardCopy[teamLoc.row][teamLoc.column] instanceof King)
                continue;
            for (BoardLocation otherLoc : boardCopy[teamLoc.row][teamLoc.column].getPossibleMoves(board, boardCopy,
                    teamLoc, false)) {
                if (otherLoc.isEqual(loc1) || otherLoc.isEqual(loc2))
                    return false;
            }
        }

        // // Only ever one king per team.
        // BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(board.getBoard(), team, Piece.KING).get(0);
        // Piece pieceOne = boardCopy[kingLocation.row][kingLocation.column + 1];
        // Piece pieceTwo = boardCopy[kingLocation.row][kingLocation.column + 2];
        // if (!(pieceOne instanceof EmptyPiece && pieceTwo instanceof EmptyPiece))
        //     return false;
        // Rook rook = (Rook) (boardCopy[kingLocation.row][kingLocation.column + 3]);
        // if (rook.hasMoved())
        //     return false;
        // // Simulate the moves now.
        // board.simulateApplyMove(boardCopy, boardCopy[kingLocation.row][kingLocation.column], kingLocation,
        //         new BoardLocation(kingLocation.column + 1, kingLocation.row));
        // King king = (King) (boardCopy[kingLocation.row][kingLocation.column + 1]);
        // if (king.inCheck)
        //     return false;
        // kingLocation = new BoardLocation(kingLocation.column + 1, kingLocation.row);
        // board.simulateApplyMove(boardCopy, boardCopy[kingLocation.row][kingLocation.column], kingLocation,
        //         new BoardLocation(kingLocation.column + 1, kingLocation.row));
        // king = (King) (boardCopy[kingLocation.row][kingLocation.column + 1]);
        // if (king.inCheck)
        //     return false;
        return true;
    }

    /**
     * Checks if you can castle long side.
     * @param board The board to check on.
     * @return True if you can casle long side.
     */
    private boolean canCastleLongSide(Board board) {
        if (hasMoved)
            return false;
        Piece[][] boardCopy = board.getBoard();
        BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(board.getBoard(), team, Piece.KING).get(0);
        BoardLocation loc1 = new BoardLocation(kingLocation.column - 1, kingLocation.row);
        BoardLocation loc2 = new BoardLocation(kingLocation.column - 2, kingLocation.row);
        BoardLocation loc3 = new BoardLocation(kingLocation.column - 3, kingLocation.row);
        Piece piece1 = boardCopy[loc1.row][loc1.column];
        Piece piece2 = boardCopy[loc2.row][loc2.column];
        Piece piece3 = boardCopy[loc3.row][loc3.column];

        BoardLocation rookPos = new BoardLocation(kingLocation.column - 4, kingLocation.row);
        Piece rookPiece = boardCopy[rookPos.row][rookPos.column];

        if (!(piece1 instanceof EmptyPiece) || !(piece2 instanceof EmptyPiece) || !(piece3 instanceof EmptyPiece)
                || !(rookPiece instanceof Rook))
            return false;

        if (((Rook) rookPiece).hasMoved())
            return false;

        for (BoardLocation teamLoc : board.getBoardLocationsForTeam(boardCopy, Team.getOtherTeam(team))) {
            if (boardCopy[teamLoc.row][teamLoc.column] instanceof King)
                continue;
            for (BoardLocation otherLoc : boardCopy[teamLoc.row][teamLoc.column].getPossibleMoves(board, boardCopy,
                    teamLoc, false)) {
                if (otherLoc.isEqual(loc1) || otherLoc.isEqual(loc2) || otherLoc.isEqual(loc3))
                    return false;
            }
        }
        // Only ever one king per team.
        // BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(board.getBoard(), team, Piece.KING).get(0);
        // Piece pieceOne = boardCopy[kingLocation.row][kingLocation.column - 1];
        // Piece pieceTwo = boardCopy[kingLocation.row][kingLocation.column - 2];
        // Piece pieceThree = boardCopy[kingLocation.row][kingLocation.column - 3];
        // if (!(pieceOne instanceof EmptyPiece && pieceTwo instanceof EmptyPiece && pieceThree instanceof EmptyPiece))
        //     return false;
        // Rook rook = (Rook) (boardCopy[kingLocation.row][kingLocation.column - 4]);
        // if (rook.hasMoved())
        //     return false;
        // // Simulate the moves now.
        // board.simulateApplyMove(boardCopy, boardCopy[kingLocation.row][kingLocation.column], kingLocation,
        //         new BoardLocation(kingLocation.column - 1, kingLocation.row));
        // King king = (King) (boardCopy[kingLocation.row][kingLocation.column - 1]);
        // if (king.inCheck)
        //     return false;
        // kingLocation = new BoardLocation(kingLocation.column - 1, kingLocation.row);
        // board.simulateApplyMove(boardCopy, boardCopy[kingLocation.row][kingLocation.column], kingLocation,
        //         new BoardLocation(kingLocation.column - 1, kingLocation.row));
        // king = (King) (boardCopy[kingLocation.row][kingLocation.column - 1]);
        // if (king.inCheck)
        //     return false;
        // kingLocation = new BoardLocation(kingLocation.column - 1, kingLocation.row);
        // board.simulateApplyMove(boardCopy, boardCopy[kingLocation.row][kingLocation.column], kingLocation,
        //         new BoardLocation(kingLocation.column - 1, kingLocation.row));
        // king = (King) (boardCopy[kingLocation.row][kingLocation.column - 1]);
        // if (king.inCheck)
        //     return false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Board boardClass, Piece[][] board, BoardLocation startMove,
            boolean extraCheck) {
        ArrayList<BoardLocation> moves = new ArrayList<>();
        BoardLocation endMove = new BoardLocation(startMove.column, startMove.row);
        if (canCastleLongSide(boardClass)) {
            moves.add(new BoardLocation(startMove.column - 4, startMove.row));
        }
        if (canCastleShortSide(boardClass)) {
            moves.add(new BoardLocation(startMove.column + 3, startMove.row));
        }
        if (extraCheck) {
        }

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
