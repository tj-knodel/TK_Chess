package edu.kingsu.SoftwareEngineering.Chess.Board;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessUIManager;

/**
 * The Board class handles all of the logic in chess. It handles
 * generation of algebraic notation (PGN format), loading algebraic notation (PGN format),
 * and handles game states if the game is won or not.
 * @author Daniell Buchner
 * @version 0.2.0
 */
public class Board {

    /**
     * The Board locations in the indexing that the Piece[][] board uses.
     * This way we can get the string representation of the location
     * by using the BoardLocation in the applyMove function.
     */
    private static String[][] BOARD_LOCATIONS = {
            { "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8" },
            { "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7" },
            { "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6" },
            { "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5" },
            { "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4" },
            { "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3" },
            { "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2" },
            { "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1" },
    };

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

    /**
     * The boolean flipping for PGN format string.
     */
    private boolean firstMove;

    /**
     * The move counter for the PGN format.
     */
    private int moveCount;

    /**
     * The Board constructor.
     * For now just creates the board and initializes with two player game.
     */
    // TODO: Add a "settings" or "gamemode" instance here to check how to initialize the board.
    public Board() {
        // board = new Piece[8][8];
        algebraicRepresentation = new StringBuilder();
        firstMove = true;
        moveCount = 1;
        initializeGameTwoPlayersWhiteOnly();
    }

    /**
     * Initializes the board to play a game and not to
     * read the PGN game.
     */
    // TODO: Change this function to take in a "settings" variable to initialize the side based on that
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
     * @return A Piece[][] deep copy.
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
     * Will also generate algebraic notation for the move in here
     * and apply it to the algebraicNotation StringBuilder.
     *
     * @param pieceMoving The chess piece being moved.
     * @param startMove   The starting move of the piece.
     * @param endMove     The target location of the piece.
     * @return True if the move was successful.
     */
    public boolean applyMove(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
        int team = pieceMoving.getTeam();
        int piecesMoveToSameLocation = 0;
        // Get all pieces of type that can move to the "endMove" location
        for (BoardLocation location : getPossibleMovesForTeamFromPiece(team, pieceMoving.getPieceID())) {
            if (location.row == endMove.row && location.column == endMove.column) {
                piecesMoveToSameLocation++;
            }
        }
        StringBuilder moveString = new StringBuilder();
        // If only found one, just do basic chess notation
        if (piecesMoveToSameLocation == 1) {
            if (!(pieceMoving instanceof Pawn)) {
                moveString.append(Piece.chessNotationValue.get(pieceMoving.getPieceID()));
            }
            if (!(board[endMove.row][endMove.column] instanceof EmptyPiece)) {
                if (pieceMoving instanceof Pawn)
                    moveString.append(BOARD_LOCATIONS[startMove.row][startMove.column].substring(0, 1));
                moveString.append("x");
            }
            moveString.append(BOARD_LOCATIONS[endMove.row][endMove.column]);
        } else if (piecesMoveToSameLocation >= 2) {
            // Check if it should put row, or column
            boolean putRow = false;
            ArrayList<BoardLocation> pieceLocations = getBoardLocationsForTeamForPiece(team, pieceMoving.getPieceID());
            for (BoardLocation location : pieceLocations) {
                if (location.column == startMove.column && location.row == startMove.row)
                    continue;
                if (location.column == startMove.column)
                    putRow = true;
            }
            // Add the row or column after the piece id
            String locationIfNeeded = "";
            if (putRow) {
                locationIfNeeded = BOARD_LOCATIONS[startMove.row][startMove.column].substring(1, 2);
            } else {
                locationIfNeeded = BOARD_LOCATIONS[startMove.row][startMove.column].substring(0, 1);
            }
            if (!(pieceMoving instanceof Pawn)) {
                moveString.append(Piece.chessNotationValue.get(pieceMoving.getPieceID()));
                moveString.append(locationIfNeeded);
            }
            if (!(board[endMove.row][endMove.column] instanceof EmptyPiece)) {
                if (pieceMoving instanceof Pawn)
                    moveString.append(BOARD_LOCATIONS[startMove.row][startMove.column].substring(0, 1));
                moveString.append("x");
            }
            moveString.append(BOARD_LOCATIONS[endMove.row][endMove.column]);
        }
        // System.out.println(moveString.toString());
        board[endMove.row][endMove.column] = pieceMoving;
        pieceMoving.moved();
        board[startMove.row][startMove.column] = new EmptyPiece();
        int otherTeam = (pieceMoving.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        if (checkKingInCheck(pieceMoving, otherTeam)) {
            moveString.append("+");
        }
        checkKingInCheck(pieceMoving, pieceMoving.getTeam());
        if (firstMove) {
            firstMove = false;
            algebraicRepresentation.append(moveCount + ". " + moveString.toString());
            // TODO: Maybe move this somewhere else so the board doesn't call UI stuff?
            ChessUIManager.appendMovesLabel(moveCount + ". " + moveString.toString());
        } else {
            moveCount++;
            firstMove = true;
            algebraicRepresentation.append(" " + moveString.toString() + "\n");
            // TODO: Maybe move this somewhere else so the board doesn't call UI stuff?
            ChessUIManager.appendMovesLabel(" " + moveString.toString() + "\n");
        }
        return false;
    }

    /**
     * Will "simulate" a move. Essentially it applys the same logic as applyMove
     * but it does it with a board passed in.
     * @param board The board to apply the move to.
     * @param pieceMoving The piece moving.
     * @param startMove Starting location of the piece.
     * @param endMove Desired end location of the piece.
     */
    private void simulateApplyMove(Piece[][] board, Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
        board[endMove.row][endMove.column] = pieceMoving;
        pieceMoving.moved();
        board[startMove.row][startMove.column] = new EmptyPiece();
        int otherTeam = (pieceMoving.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        checkKingInCheck(board, pieceMoving, otherTeam);
        checkKingInCheck(board, pieceMoving, pieceMoving.getTeam());
    }

    /**
     * This checks if the king is in check for a board that is passed in as a parameter.
     * It takes the piece that is "moving" and checking any piece from the other team
     * results in a team being in check.
     * 
     * @param pieceMoving The piece that is "moving".
     * @param team The team to check for their king being in check.
     * @return True if the king is in check, false otherwise.
     */
    private boolean checkKingInCheck(Piece pieceMoving, int team) {
        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(team, Piece.KING).get(0);
        King kingPieceOtherTeam = (King) board[kingLocation.row][kingLocation.column];
        kingPieceOtherTeam.inCheck = false;
        int otherTeam = (team == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        for (BoardLocation teamPossibleMoves : getPossibleMovesForTeam(otherTeam)) {
            if (teamPossibleMoves.row == kingLocation.row && teamPossibleMoves.column == kingLocation.column) {
                kingPieceOtherTeam.inCheck = true;
                return true;
            }
        }
        return false;
    }

    /**
     * This checks if the king is in check for a board that is passed in as a parameter.
     * It takes the piece that is "moving" and checking any piece from the other team
     * results in a team being in check.
     * 
     * @param board       The board to check if the king is in check.
     * @param pieceMoving The piece that is "moving".
     * @param team        The team to check for their king being in check.
     * @return True if the king is in check, false otherwise.
     */
    private boolean checkKingInCheck(Piece[][] board, Piece pieceMoving, int team) {
        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(board, team, Piece.KING).get(0);
        King kingPieceOtherTeam = (King) board[kingLocation.row][kingLocation.column];
        kingPieceOtherTeam.inCheck = false;
        int otherTeam = (team == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        for (BoardLocation teamPossibleMoves : getPossibleMovesForTeam(board, otherTeam)) {
            if (teamPossibleMoves.row == kingLocation.row && teamPossibleMoves.column == kingLocation.column) {
                kingPieceOtherTeam.inCheck = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all possible moves for the current piece.
     *
     * @param piece    The piece to get the possible moves for.
     * @param location The location that the piece is on the board.
     * @return
     */
    public ArrayList<BoardLocation> getPossibleMoves(Piece piece, BoardLocation location) {
        // TODO: Check if you can make a move if in check currently.
        // Get the current piece's team, and then check if their king is in check
        // If in check, only return possible moves that will make them not in check
        // anymore.
        int team = piece.getTeam();
        Piece[][] boardCopy = getBoard();
        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(team, Piece.KING).get(0);
        King kingPiece = (King) boardCopy[kingLocation.row][kingLocation.column];
        if (kingPiece.inCheck) {
            ArrayList<BoardLocation> returnVal = new ArrayList<>();
            ArrayList<BoardLocation> pieceMoves = piece.getPossibleMoves(board, location);
            for (BoardLocation move : pieceMoves) {
                simulateApplyMove(boardCopy, boardCopy[location.row][location.column], location, move);
                kingLocation = getBoardLocationsForTeamForPiece(boardCopy, team, Piece.KING).get(0);
                kingPiece = (King) boardCopy[kingLocation.row][kingLocation.column];
                if (kingPiece.inCheck) {
                } else {
                    returnVal.add(move);
                }
                boardCopy = getBoard();
            }
            return returnVal;
        } else if (!kingPiece.inCheck) {
            ArrayList<BoardLocation> returnVal = new ArrayList<>();
            ArrayList<BoardLocation> pieceMoves = piece.getPossibleMoves(board, location);
            for (BoardLocation move : pieceMoves) {
                simulateApplyMove(boardCopy, boardCopy[location.row][location.column], location, move);
                kingLocation = getBoardLocationsForTeamForPiece(boardCopy, team, Piece.KING).get(0);
                kingPiece = (King) boardCopy[kingLocation.row][kingLocation.column];
                if (kingPiece.inCheck) {
                } else {
                    returnVal.add(move);
                }
                boardCopy = getBoard();
            }
            return returnVal;
        }
        return null;
        // return piece.getPossibleMoves(board, location);
    }

    /**
     * Returns all possible moves for the current piece.
     *
     * @param board The board to check for the locations on.
     * @param piece The piece to get the possible moves for.
     * @param location The location that the piece is at.
     * @return
     */
    public ArrayList<BoardLocation> getPossibleMoves(Piece[][] board, Piece piece, BoardLocation location) {
        // TODO: Check if you can make a move if in check currently.
        // Get the current piece's team, and then check if their king is in check
        // If in check, only return possible moves that will make them not in check
        // anymore.
        ArrayList<BoardLocation> returnVal = new ArrayList<>();
        int team = piece.getTeam();
        Piece[][] boardCopy = getBoard();
        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(team, Piece.KING).get(0);
        King kingPiece = (King) boardCopy[kingLocation.row][kingLocation.column];
        if (kingPiece.inCheck) {
            ArrayList<BoardLocation> pieceMoves = piece.getPossibleMoves(board, location);
            for (BoardLocation move : pieceMoves) {
                simulateApplyMove(boardCopy, boardCopy[location.row][location.column], location, move);
                kingLocation = getBoardLocationsForTeamForPiece(boardCopy, team, Piece.KING).get(0);
                kingPiece = (King) boardCopy[kingLocation.row][kingLocation.column];
                if (kingPiece.inCheck) {
                } else {
                    returnVal.add(move);
                }
                boardCopy = getBoard();
            }
            return returnVal;
        }
        return piece.getPossibleMoves(board, location);
    }

    /**
     * Gets all the possible moves for a specific team
     * 
     * @param team The team to get all possible moves for.
     * @return ArrayList of BoardLocations for the possible moves a team can make.
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
     * Gets all the possible moves for a specific team
     * 
     * @param board The board to check possible moves for the team.
     * @param team The team to get all possible moves for.
     * @return Arraylist of BoardLocations for the possible moves a team can make.
     */
    public ArrayList<BoardLocation> getPossibleMovesForTeam(Piece[][] board, int team) {
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
     * @param team The team to get the moves for.
     * @param pieceId The specific piece to get the moves for.
     * @return ArrayList of BoardLocations of all possible moves of all pieces of certain type for team.
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

    /**
     * Gets the locations of pieces for a team on the board.
     * 
     * @param team The team to get the moves for.
     * @param pieceId The piece to get locations for.
     * @return ArrayList of BoardLocations of all locations of the pieces for the team.
     */
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
     * Gets the locations of pieces for a team on the board.
     * 
     * @param board The board to get the pieces locations for.
     * @param team The team to get the moves for.
     * @param pieceId The piece to get locations for.
     * @return ArrayList of BoardLocations of all locations of the pieces for the team.
     */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPiece(Piece[][] board, int team, int pieceId) {
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

    /**
     * Gets a textual representation of the board.
     */
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
