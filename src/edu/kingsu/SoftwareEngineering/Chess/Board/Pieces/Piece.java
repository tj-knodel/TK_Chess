package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Move;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * The generic piece representation. Does not hold data
 * besides the piece id for the pieces to access.
 *
 * @author Daniell Buchner
 * @version 0.2.0
 */
public abstract class Piece {

    /**
     * The team the piece is on. 1 or 0 for white/black
     */
    protected int team;

    /**
     * Constructor to set the team for the pieces.
     *
     * @param team Which team the piece is on.
     */
    protected Piece(int team) {
        this.team = team;
    }

    /**
     * Get the string representation of the piece name.
     *
     * @return The name of the piece.
     */
    public abstract String getPieceName();

    /**
     * Get the piece id that can be used for comparing and
     * knowing which piece is where.
     *
     * @return The id of the piece.
     */
    public abstract int getPieceID();

    /**
     * Creates a new instance of the piece.
     *
     * @param team The team the piece was on.
     * @return The new piece.
     */
    public abstract Piece copy(int team);

    /**
     * Gets the team the piece is on.
     *
     * @return The team the piece is on.
     */
    public int getTeam() {
        return team;
    }

    // TODO: Split up IsMoveValid to two functions to handle if the move is within the board, and if there is a piece at the location.
    protected boolean IsMoveValid(Piece[][] board, Move move) {
//        if(move.column >= 0 && move.column < board.length && move.row >= 0 && move.row < board.length)
//        {
//            return board[move.row][move.column].getTeam() != team;
//        }
        return move.column >= 0 && move.column < board.length && move.row >= 0 && move.row < board.length;
    }

    /**
     * Check if there is a piece from the other team at that location
     * @param board The board the game is currently on
     * @param move The location to move to
     * @return
     */
    protected MoveValidity IsMoveValidWithoutPiece(Piece[][] board, Move move) {
        MoveValidity moveValidity = new MoveValidity();
        if(!IsMoveValid(board, move)) {
            moveValidity.isInBoard = false;
            moveValidity.isEmptySpace = false;
            moveValidity.isOtherTeam = false;
            return moveValidity;
        }
        Piece pieceAtMove = board[move.row][move.column];
        if(pieceAtMove instanceof EmptyPiece) {
            moveValidity.isInBoard = true;
            moveValidity.isOtherTeam = false;
            moveValidity.isEmptySpace = true;
            return moveValidity;
        }
        if(pieceAtMove.getTeam() == team) {
            moveValidity.isInBoard = true;
            moveValidity.isOtherTeam = false;
            moveValidity.isEmptySpace = false;
            return moveValidity;
        }
        if(pieceAtMove.getTeam() != team) {
            moveValidity.isInBoard = true;
            moveValidity.isOtherTeam = true;
            moveValidity.isEmptySpace = false;
            return moveValidity;
        }
//        if(pieceAtMove.getTeam() == team) {
//            moveValidity.isInBoard = true;
//            moveValidity.isOtherTeam = false;
//            moveValidity.isEmptySpace = false;
//        }
//        else if(pieceAtMove.getTeam() != team) {
//            moveValidity.isInBoard = true;
//            moveValidity.isOtherTeam = true;
//            moveValidity.isEmptySpace = false;
//        } else {
//            moveValidity.isInBoard = true;
//            moveValidity.isOtherTeam = false;
//            moveValidity.isEmptySpace = true;
//        }
        return moveValidity;
    }

    public abstract ArrayList<Move> getPossibleMoves(Piece[][] board, Move startMove);

    protected static int EMPTY_PIECE = -1;
    protected static int PAWN = 0;
    protected static int ROOK = 1;
    protected static int KNIGHT = 2;
    protected static int BISHOP = 3;
    protected static int QUEEN = 4;
    protected static int KING = 5;

}
