package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * The Bishop chess piece.
 * @author Daniell Buchner
 * @version 1.1.0
 */
public class Bishop extends Piece {

    /**
     * Create a Bishop piece with a team.
     * @param team The team to set.
     */
    public Bishop(int team) {
        super(team);
        value = 3;
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
    public ArrayList<BoardLocation> getPossibleMoves(Board boardClass, Piece[][] board, BoardLocation startMove) {
        BoardLocation[] moveDirections = {new BoardLocation(-1, -1), new BoardLocation(1, -1), new BoardLocation(-1, 1), new BoardLocation(1, 1)};
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
    }

}
