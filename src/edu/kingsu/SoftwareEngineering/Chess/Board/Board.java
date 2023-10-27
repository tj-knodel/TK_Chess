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
        // board = new Piece[8][8];
        algebraicRepresentation = new StringBuilder();
        initializeGameTwoPlayersWhiteOnly();
    }

    /**
     * Initializes the board to play a game and not to
     * read the PGN game.
     */
    // TODO: Change this function to take in a "settings" variable to initialize the
    // side based on that
    public void initializeGameTwoPlayersWhiteOnly() {
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
                { new Rook(1), new Knight(1), new Bishop(1), new Queen(1), new King(1), new Bishop(1), new Knight(1),
                        new Rook(1) }
        };

    }

    /**
     * Gets a deep copy of the board
     *
     * @return A deep copy of the board
     */
    public Piece[][] getBoard() {
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
    public boolean applyMove(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
        board[endMove.row][endMove.column] = pieceMoving;
        pieceMoving.moved();
        board[startMove.row][startMove.column] = new EmptyPiece();
        return false;
    }

    /**
     * Returns all possible moves for the current piece.
     *
     * @param piece
     * @param location
     * @return
     */
    public ArrayList<BoardLocation> getPossibleMoves(Piece piece, BoardLocation location) {
        return piece.getPossibleMoves(board, location);
    }

    /**
     * Gets all the possible moves for a specific team
     * 
     * @param team
     * @return
     */
    public ArrayList<BoardLocation> getPossibleMovesForTeam(int team) {
        ArrayList<BoardLocation> possibleMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team) {
                    for (BoardLocation m : board[i][j].getPossibleMoves(board, new BoardLocation(j, i))) {
                        possibleMoves.add(m);
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Gets all possible moves for a specific piece on the board
     * 
     * @param team
     * @param pieceId
     * @return
     */
    public ArrayList<BoardLocation> getPossibleMovesForTeamFromPiece(int team, int pieceId) {
        ArrayList<BoardLocation> possibleMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team && board[i][j].getPieceID() == pieceId) {
                    for (BoardLocation m : board[i][j].getPossibleMoves(board, new BoardLocation(j, i))) {
                        possibleMoves.add(m);
                    }
                }
            }
        }
        return possibleMoves;
    }

    public ArrayList<BoardLocation> getBoardLocationsForTeamForPiece(int team, int pieceId) {
        ArrayList<BoardLocation> locations = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team && board[i][j].getPieceID() == pieceId) {
                    locations.add(new BoardLocation(j, i));
                }
            }
        }
        return locations;
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
        boardString.append("    0   1   2   3   4   5   6   7  ").append(System.lineSeparator());
        for (int row = 0; row < board.length; row++) {
            boardString.append(row + " | ");
            for (int col = 0; col < board[row].length; col++) {
                boardString.append(board[row][col].getPieceName()).append(" | ");
            }
            boardString.append(System.lineSeparator());
        }
        return boardString.toString();
    }

}
