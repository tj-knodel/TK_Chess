package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.ArrayList;
import java.util.HashMap;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveValidity;

/**
 * The generic piece representation. Does not hold data
 * besides the piece id for the pieces to access.
 *
 * @author Daniell Buchner
 * @version 1.2.0
 */
public abstract class Piece {

    /**
     * The point value of the piece
     */
    protected int value;

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
     * Returns the point value of the piece
     * @return a point value
     */
    public int getValue() {
        return value;
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
     * Called whenever a piece is moved. This is called
     * for all pieces as some pieces need this functionality.
     */
    public abstract void moved();

    /**
     * Gets the team the piece is on.
     *
     * @return The team the piece is on.
     */
    public int getTeam() {
        return team;
    }

    /**
     * Checks if a move is within the bounds of the board.
     * @param board The board to check in.
     * @param move The location that is desired to be checked.
     * @return True if within the bounds, false otherwise.
     */
    protected boolean IsMoveValid(Piece[][] board, BoardLocation move) {
        return move.column >= 0 && move.column < board.length && move.row >= 0 && move.row < board.length;
    }

    /**
     * Check if there is a piece from the other team at that location,
     * or if you cannot move there. It just validates that the move can
     * be done theoretically.
     * 
     * @param board The board the game is currently on
     * @param move  The location to move to
     * @return MoveValidity class representing the desired move.
     */
    protected MoveValidity IsMoveValidWithoutPiece(Piece[][] board, BoardLocation move) {
        MoveValidity moveValidity = new MoveValidity();
        if (!IsMoveValid(board, move)) {
            moveValidity.isInBoard = false;
            moveValidity.isEmptySpace = false;
            moveValidity.isOtherTeam = false;
            return moveValidity;
        }
        Piece pieceAtMove = board[move.row][move.column];
        if (pieceAtMove instanceof EmptyPiece) {
            moveValidity.isInBoard = true;
            moveValidity.isOtherTeam = false;
            moveValidity.isEmptySpace = true;
            return moveValidity;
        }
        if (pieceAtMove.getTeam() == team) {
            moveValidity.isInBoard = true;
            moveValidity.isOtherTeam = false;
            moveValidity.isEmptySpace = false;
            return moveValidity;
        }
        if (pieceAtMove.getTeam() != team) {
            moveValidity.isInBoard = true;
            moveValidity.isOtherTeam = true;
            moveValidity.isEmptySpace = false;
            return moveValidity;
        }
        return moveValidity;
    }

    /**
     * Gets all possible moves for a piece at any given location.
     *
     * @param boardClass
     * @param board      The board to get possible moves for.
     * @param startMove  The starting location of the piece.
     * @param extraCheck
     * @return ArrayList of BoardLocations of possible moves.
     */
    public abstract ArrayList<BoardLocation> getPossibleMoves(Board boardClass, Piece[][] board,
            BoardLocation startMove, boolean extraCheck);

    /**
     * The EMPTY piece ID.
     */
    public final static int EMPTY_PIECE = -1;
    /**
     * The Pawn piece ID.
     */
    public final static int PAWN = 0;
    /**
     * The Rook piece ID.
     */
    public final static int ROOK = 1;
    /**
     * The Knight piece ID.
     */
    public final static int KNIGHT = 2;
    /**
     * The Bishop piece ID.
     */
    public final static int BISHOP = 3;
    /**
     * The Queen piece ID.
     */
    public final static int QUEEN = 4;
    /**
     * The King piece ID.
     */
    public final static int KING = 5;

    /**
     * Gets the chess notation value of a piece to a string.
     */
    public final static HashMap<Integer, String> CHESS_NOTATION_VALUE;

    public static final HashMap<String, Integer> PIECE_ID_FROM_STRING;

    public static Piece createPieceFromTeam(int pieceId, int team) {
        switch (pieceId) {
            case Piece.BISHOP:
                return new Bishop(team);
            case Piece.ROOK:
                return new Rook(team);
            case Piece.KNIGHT:
                return new Knight(team);
            case Piece.QUEEN:
                return new Queen(team);
            default:
                return new EmptyPiece();
        }
    }

    static {
        CHESS_NOTATION_VALUE = new HashMap<>();
        CHESS_NOTATION_VALUE.put(PAWN, "");
        CHESS_NOTATION_VALUE.put(ROOK, "R");
        CHESS_NOTATION_VALUE.put(KNIGHT, "N");
        CHESS_NOTATION_VALUE.put(BISHOP, "B");
        CHESS_NOTATION_VALUE.put(QUEEN, "Q");
        CHESS_NOTATION_VALUE.put(KING, "K");

        PIECE_ID_FROM_STRING = new HashMap<>();
        PIECE_ID_FROM_STRING.put("N", Piece.KNIGHT);
        PIECE_ID_FROM_STRING.put("K", Piece.KING);
        PIECE_ID_FROM_STRING.put("Q", Piece.QUEEN);
        PIECE_ID_FROM_STRING.put("B", Piece.BISHOP);
        PIECE_ID_FROM_STRING.put("R", Piece.ROOK);
    }

}
