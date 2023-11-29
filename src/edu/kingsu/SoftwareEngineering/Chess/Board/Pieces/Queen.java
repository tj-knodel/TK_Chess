package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * The piece class.
 * @author Daniell Buchner
 * @version 1.1.0
 */
public class Queen extends Piece {

    /**
     * Creates a queen for a specific team.
     *
     * @param team The team this piece should be associated with.
     */
    public Queen(int team) {
        super(team);
        value = 900;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece copy(int team) {
        return new Queen(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<BoardLocation> getPossibleMoves(Board boardClass, Piece[][] board, BoardLocation startMove) {
        BoardLocation[] moveDirections = {new BoardLocation(-1, 0), new BoardLocation(1, 0), new BoardLocation(0, -1), new BoardLocation(0, 1),
                new BoardLocation(-1, -1), new BoardLocation(1, -1), new BoardLocation(-1, 1), new BoardLocation(1, 1)};
        ArrayList<BoardLocation> moves = new ArrayList<>();
        for (BoardLocation direction : moveDirections) {
            BoardLocation endMove = new BoardLocation(startMove.column, startMove.row);
            while (IsMoveValid(board, endMove)) {
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
    }

}
