package edu.kingsu.SoftwareEngineering.Chess.Board;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;

/**
 * @author Daniell Buchner
 * @version 0.2.0
 */
public class Board {

    /**
     * The representation of the board as a 2D-array
     * of pieces. This is build from the PGN format or
     * built from scratch based on the board.
     */
    private Piece[][] board;

    /**
     * The representation of the game in chess
     * algebraic notation. The board uses this
     * for the game, as well as for reading files.
     */
    private final StringBuilder algebraicRepresentation;

    public Board() {
        board = new Piece[8][8];
        algebraicRepresentation = new StringBuilder();
        initializeGame();
    }

    /**
     * Initializes the board to play a game and not to
     * read the PGN game.
     */
    public void initializeGame() {
        board = new Piece[][] {
                { new Rook(0), new Knight(0), new Bishop(0), new Queen(0), new King(0), new Bishop(0), new Knight(0),
                        new Rook(0) },
                { new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0),
                        new Pawn(0) },
                { new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                        new EmptyPiece(), new EmptyPiece(), new EmptyPiece() },
                { new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                        new EmptyPiece(), new EmptyPiece(), new EmptyPiece() },
                { new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                        new EmptyPiece(), new EmptyPiece(), new EmptyPiece() },
                { new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                        new EmptyPiece(), new EmptyPiece(), new EmptyPiece() },
                { new Pawn(1), new Pawn(1), new Pawn(1), new Pawn(1), new Pawn(1), new Pawn(1), new Pawn(1),
                        new Pawn(1) },
                { new Rook(1), new Knight(1), new Bishop(1), new King(1), new Queen(1), new Bishop(1), new Knight(1),
                        new Rook(1) }
        };
    }

    /**
     * Gets a deep copy of the board
     * 
     * @return A deep copy of the board
     */
    Piece[][] getBoard() {
        Piece[][] copiedBoard = new Piece[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                copiedBoard[j][i] = board[j][i].copy(board[j][i].getTeam());
            }
        }
        return copiedBoard;
    }

    /**
     * Check if a move can be applied, then do it.
     * 
     * @param pieceMoving The chess piece being moved.
     * @param startMove   The starting move of the piece.
     * @param endMove     The target location of the piece.
     * @return True if the move was successful.
     */
    boolean applyMove(Piece pieceMoving, Move startMove, Move endMove) {
        return false;
    }

    /**
     * Returns all possible moves for the current piece.
     * 
     * @param piece
     * @param location
     * @return
     */
    ArrayList<Move> getPossibleMoves(Piece piece, Location location) {
        return null;
    }

    /**
     * Gets the algebraic notation string.
     * 
     * @return The algebraic notation string of the current board.
     */
    public String getAlgebraicNotation() {
        return algebraicRepresentation.toString();
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                boardString.append(board[row][col].getPieceName()).append(" | ");
            }
            boardString.append(System.lineSeparator());
        }
        return boardString.toString();
    }

}
