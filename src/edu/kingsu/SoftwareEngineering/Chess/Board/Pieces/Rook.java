package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * @author Daniell Buchner
 * @version 1.1.0
 */
public class Rook extends Piece {

    /**
     * Has the rook moved or not.
     */
    private boolean hasMoved;

    /**
     * Creates a rook based on a team.
     * @param team The team to set to.
     */
    public Rook(int team) {
        super(team);
        hasMoved = false;
        value = 5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPieceName() {
        return "R";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPieceID() {
        return Piece.ROOK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece copy(int team) {
        Rook rook = new Rook(team);
        rook.hasMoved = hasMoved;
        return rook;
    }

    /**
     * Gets if the rook has moved or not.
     * @return True if the rook has moved.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Board boardClass, Piece[][] board, BoardLocation startMove) {
        BoardLocation[] moveDirections = {new BoardLocation(-1, 0), new BoardLocation(1, 0), new BoardLocation(0, -1), new BoardLocation(0, 1)};
        ArrayList<BoardLocation> moves = new ArrayList<>();
        for(BoardLocation direction : moveDirections) {
            BoardLocation endMove = new BoardLocation(startMove.column, startMove.row);
            while(IsMoveValid(board, endMove)) {
                endMove.row += direction.row;
                endMove.column += direction.column;
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
