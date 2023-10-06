package edu.kingsu.SoftwareEngineering.Chess.Board;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;

/**
 * @author Daniell Buchner
 * @version 0.1.0
 */
public interface BoardFunctions {

    /**
     * Gets a deep copy of the board
     * 
     * @return A deep copy of the board
     */
    Piece[][] getBoard();

    /**
     * Check if a move can be applied, then do it.
     * 
     * @param pieceMoving The chess piece being moved.
     * @param startMove   The starting move of the piece.
     * @param endMove     The target location of the piece.
     * @return True if the move was successful.
     */
    boolean applyMove(Piece pieceMoving, Move startMove, Move endMove);

    /**
     * Returns all possible moves for the current piece.
     * 
     * @param piece
     * @param location
     * @return
     */
    ArrayList<Move> getPossibleMoves(Piece piece, Location location);

}