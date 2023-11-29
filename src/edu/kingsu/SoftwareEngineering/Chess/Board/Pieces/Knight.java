package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * The knight piece class.
 * @author Daniell Buchner
 * @version 1.1.0
 */
public class Knight extends Piece {

    /**
     * Creates a new knight based on a team.
     * @param team The team the knight should be associated with.
     */
    public Knight(int team) {
        super(team);
        value = 320;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece copy(int team) {
        return new Knight(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Board boardClass, Piece[][] board, BoardLocation startMove) {
        BoardLocation[] moveDirections = {new BoardLocation(-1, 2), new BoardLocation(-2, 1), new BoardLocation(1, 2), new BoardLocation(2, 1),
                new BoardLocation(-1, -2), new BoardLocation(-2, -1), new BoardLocation(2, -1), new BoardLocation(1, -2)};
        ArrayList<BoardLocation> moves = new ArrayList<>();
        for (BoardLocation direction : moveDirections) {
            BoardLocation endMove = new BoardLocation(startMove.column, startMove.row);
            if (IsMoveValid(board, endMove)) {
                endMove.row += direction.row;
                endMove.column += direction.column;
                MoveValidity moveValid = IsMoveValidWithoutPiece(board, endMove);
                if (!moveValid.isInBoard)
                    continue;
                if (!moveValid.isOtherTeam && !moveValid.isEmptySpace)
                    continue;
                if (moveValid.isEmptySpace)
                    moves.add(new BoardLocation(endMove.column, endMove.row));
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
    }

}
