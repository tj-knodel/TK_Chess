package edu.kingsu.SoftwareEngineering.Chess.Board;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.EmptyPiece;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.King;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Pawn;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class to help with generating moves from PGN,
 * and taking moves and creating the PGN.
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class PGNHelper {

    /**
     * The Board locations in the indexing that the Piece[][] board uses.
     * This way we can get the string representation of the location
     * by using the BoardLocation in the applyMove function.
     */
    private static final String[][] BOARD_LOCATIONS = {
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
     * The board locations from a string. So "a1" would return a BoardLocation
     * with row = 7 and column = 0
     */
    private static final HashMap<String, BoardLocation> BOARD_LOCATIONS_FROM_STRING;

    /**
     * The Board class reference to call helper functions.
     */
    private final Board board;

    /**
     * The promotionPiece value if move is from PGN notation.
     */
    private String promotionPiece;

    /**
     * The constructor to create a PGNHelper object.
     * @param board The board class to reference.
     */
    public PGNHelper(Board board) {
        this.board = board;
    }

    /**
     * Gets the board locations (start move and end move) for a given
     * input PGN notation for a given team.
     * @param notation The notation to apply.
     * @param team The team it get the locations for.
     * @return The BoardLocation array of size 2 containing the start and end move.
     */
    public BoardLocation[] getBoardLocationsFromPGN(String notation, int team) {
        BoardLocation[] locations = new BoardLocation[2];
        notation = notation.replace("+", "");

        if (notation.equalsIgnoreCase("O-O")) {
            BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(board.getBoard(), team, Piece.KING)
                    .get(0);
            locations[0] = kingLocation;
            locations[1] = new BoardLocation(kingLocation.column + 3, kingLocation.row);
            return locations;
        } else if (notation.equalsIgnoreCase("O-O-O")) {
            BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(board.getBoard(), team, Piece.KING)
                    .get(0);
            locations[0] = kingLocation;
            locations[1] = new BoardLocation(kingLocation.column - 4, kingLocation.row);
            return locations;
        }

        // Pawn move
        if (notation.length() == 2 || (notation.contains("=") && notation.length() == 4)) {
            return getBoardLocationsForPawnMove(notation, team, locations);
        } else if (notation.length() == 4 && Character.isLowerCase(notation.charAt(0)) && !notation.contains("="))
            return getBoardLocationsForPawnMoveForColumn(notation, team, locations, notation.charAt(0) - 'a');
        else if (notation.length() == 6 && Character.isLowerCase(notation.charAt(0)) && notation.contains("=")) {
            notation = notation.substring(0, notation.length() - 2);
            return getBoardLocationsForPawnMoveForColumn(notation, team, locations, notation.charAt(0) - 'a');
        }

        // All other pieces move
        if (notation.length() == 3) {
            return getBoardLocationsForPieceMove(notation, team, locations);
        } else if (notation.length() == 4 && !isNotationCapturing(notation)) {
            BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(2));
            if (Character.isDigit(notation.charAt(1))) {
                return getBoardLocationsForPieceMoveRow(notation, team, locations, (8 - (notation.charAt(1) - '0')),
                        targetLocation);
            } else {
                return getBoardLocationsForPieceMoveColumn(notation, team, locations, notation.charAt(1) - 'a');
            }
        } else if (notation.length() == 4) {
            notation = notation.replace("x", "");
            return getBoardLocationsForPieceMove(notation, team, locations);
        } else if (notation.length() == 5 && isNotationCapturing(notation)) {
            notation = notation.replace("x", "");
            BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(2));
            if (Character.isDigit(notation.charAt(1))) {
                return getBoardLocationsForPieceMoveRow(notation, team, locations, (8 - (notation.charAt(1) - '0')),
                        targetLocation);
            } else {
                return getBoardLocationsForPieceMoveColumn(notation, team, locations, notation.charAt(1) - 'a');
            }
        }
        return locations;
    }

    /**
     * Gets the board locations for a piece for a given row and a target location.
     * @param notation The PGN notation inputted (could be modified before this point).
     * @param team The team to check.
     * @param locations The locations out variable to modify.
     * @param row The row to check.
     * @param targetLocation The target location to move to.
     * @return The BoardLocation 2d array.
     */
    private BoardLocation[] getBoardLocationsForPieceMoveRow(String notation, int team, BoardLocation[] locations,
            int row, BoardLocation targetLocation) {
        int pieceId = Piece.PIECE_ID_FROM_STRING.get(String.valueOf(notation.charAt(0)));
        ArrayList<BoardLocation> pieceLoc = board.getBoardLocationsForTeamForPieceToTargetLocationForRow(
                board.getBoard(), team, pieceId, targetLocation, row);
        if (pieceLoc.size() != 1)
            return locations;
        locations[0] = pieceLoc.get(0);
        locations[1] = targetLocation;
        return locations;
    }

    /**
     * Gets the board locations for a piece for a given column.
     * @param notation The PGN notation inputted (could be modified before this point).
     * @param team The team to check.
     * @param locations The locations out variable to modify.
     * @param column The column to check.
     * @return The BoardLocation 2d array.
     */
    private BoardLocation[] getBoardLocationsForPieceMoveColumn(String notation, int team, BoardLocation[] locations,
            int column) {
        BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(2));
        int pieceId = Piece.PIECE_ID_FROM_STRING.get(String.valueOf(notation.charAt(0)));
        ArrayList<BoardLocation> pieceLoc = board.getBoardLocationsForTeamForPieceToTargetLocationForColumn(
                board.getBoard(), team, pieceId, targetLocation, column);
        if (pieceLoc.size() != 1)
            return locations;
        locations[0] = pieceLoc.get(0);
        locations[1] = targetLocation;
        return locations;
    }

    /**
     * Gets the board locations for a piece moving (non pawn).
     * @param notation The PGN notation.
     * @param team The team to check for.
     * @param locations The locations out variable to modify.
     * @return The BoardLocation 2d array.
     */
    private BoardLocation[] getBoardLocationsForPieceMove(String notation, int team, BoardLocation[] locations) {
        BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(1));
        int pieceId = Piece.PIECE_ID_FROM_STRING.get(String.valueOf(notation.charAt(0)));
        ArrayList<BoardLocation> pieceLoc = board.getBoardLocationsForTeamForPieceToTargetLocation(board.getBoard(),
                team, pieceId, targetLocation);
        if (pieceLoc.size() != 1)
            return locations;
        locations[0] = pieceLoc.get(0);
        locations[1] = targetLocation;
        return locations;
    }

    /**
     * Gets the board locations for a pawn moving.
     * @param notation The PGN notation to check.
     * @param team The team to check.
     * @param locations The locations out variable to modify.
     * @return The BoardLocation 2d array.
     */
    private BoardLocation[] getBoardLocationsForPawnMove(String notation, int team, BoardLocation[] locations) {
        ArrayList<BoardLocation> pawnLoc = board.getBoardLocationsForTeamForPieceToTargetLocation(board.getBoard(),
                team, Piece.PAWN, BOARD_LOCATIONS_FROM_STRING.get(notation.substring(0, 2)));
        if (pawnLoc.size() != 1)
            return locations;
        locations[0] = pawnLoc.get(0);
        locations[1] = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(0, 2));
        return locations;
    }

    /**
     * Gets the board locations for a pawn in a specific column.
     * @param notation The PGN notation to check.
     * @param team The team to check.
     * @param locations The locations out variable to modify.
     * @param column The column to check for.
     * @return The BoardLocation 2d array.
     */
    private BoardLocation[] getBoardLocationsForPawnMoveForColumn(String notation, int team, BoardLocation[] locations,
            int column) {
        BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(2));
        ArrayList<BoardLocation> pawnLoc = board.getBoardLocationsForTeamForPieceToTargetLocation(board.getBoard(),
                team, Piece.PAWN, targetLocation);
        if (pawnLoc.size() != 1) {
            pawnLoc = board.getBoardLocationsForTeamForPieceToTargetLocationForColumn(board.getBoard(), team,
                    Piece.PAWN, targetLocation, column);
            if (pawnLoc.size() != 1) {
                return locations;
            }
            locations[0] = pawnLoc.get(0);
            locations[1] = targetLocation;
            return locations;
        }
        locations[0] = pawnLoc.get(0);
        locations[1] = targetLocation;
        return locations;
    }

    /**
     * Is the notation capturing a piece or not.
     * @param notation The notation to check.
     * @return True if it is capturing a piece.
     */
    private boolean isNotationCapturing(String notation) {
        return notation.contains("x");
    }

    /**
     * Given a start move and an end move, give the PGN notation of the move.
     * @param startMove The start move location.
     * @param endMove The end move location.
     * @return A string containing the PGN notation of a move.
     */
    public String getPGNNotationFromMove(BoardLocation startMove, BoardLocation endMove) {
        Piece[][] boardCopy = board.getBoard();
        Piece piece = boardCopy[startMove.row][startMove.column];
        if (board.getIsMoveCastling(startMove, endMove)) {
            if (board.getIsMoveCastlingLongSide(startMove, endMove))
                return "O-O-O";
            return "O-O";
        }
        if (board.getNumberOfPiecesMovingToSameLocation(boardCopy, piece, endMove, piece.getTeam()) > 1)
            return getPGNNotationFromMoveMultiplePieces(boardCopy, piece, startMove, endMove);
        else
            return getPGNNotationFromMoveSinglePiece(boardCopy, piece, startMove, endMove);
    }

    /**
     * Gets the PGN notation from a move where multiple pieces can move
     * to the same location.
     * @param boardCopy The Piece[][] to check against.
     * @param piece The piece that is moving.
     * @param startMove The start location.
     * @param endMove The end location.
     * @return A string of the PGN move.
     */
    private String getPGNNotationFromMoveMultiplePieces(Piece[][] boardCopy, Piece piece, BoardLocation startMove,
            BoardLocation endMove) {
        boolean shouldPutRowInNotation = board.getBoardLocationsForTeamForPieceToTargetLocationForColumn(boardCopy,
                piece.getTeam(), piece.getPieceID(), endMove, startMove.column).size() > 1;
        String moveString = Piece.CHESS_NOTATION_VALUE.get(piece.getPieceID());
        if (shouldPutRowInNotation)
            moveString += BOARD_LOCATIONS[startMove.row][startMove.column].charAt(1);
        else if (!(boardCopy[startMove.row][startMove.column] instanceof Pawn))
            moveString += BOARD_LOCATIONS[startMove.row][startMove.column].charAt(0);
        moveString = getEndPGNNotationFromMove(boardCopy, piece, startMove, endMove, moveString);
        return moveString;
    }

    /**
     * Gets the PGN notation of the end part. Will calculate anything after
     * the piece value and location is put in. Basically does the last two thirds
     * of the notation calculation.
     * @param boardCopy The Piece[][] to check against.
     * @param piece The piece that is moving.
     * @param startMove The start location.
     * @param endMove The end location.
     * @param moveString The current PGN notation to modify.
     * @return A string of the PGN move.
     */
    private String getEndPGNNotationFromMove(Piece[][] boardCopy, Piece piece, BoardLocation startMove,
            BoardLocation endMove, String moveString) {
        if (isCapturing(boardCopy, startMove, endMove)) {
            if (boardCopy[startMove.row][startMove.column] instanceof Pawn)
                moveString += BOARD_LOCATIONS[startMove.row][startMove.column].charAt(0);
            moveString += "x";
        }
        moveString += BOARD_LOCATIONS[endMove.row][endMove.column];
        if (isPromoting(piece, endMove)) {
            if (promotionPiece != null) {
                moveString += "=" + promotionPiece;
            } else {
                moveString += "=" + board.getPromotionPiece();
            }
        }
        moveString = getCheckAndCheckmateFromMove(boardCopy, piece, startMove, endMove, moveString);
        return moveString;
    }

    /**
     * Gets the PGN notation of a single piece move. So a move
     * where only one piece can go to that location.
     * @param boardCopy The Piece[][] to check against.
     * @param piece The piece that is moving.
     * @param startMove The start location.
     * @param endMove The end location.
     * @return A string of the PGN move.
     */
    private String getPGNNotationFromMoveSinglePiece(Piece[][] boardCopy, Piece piece, BoardLocation startMove,
            BoardLocation endMove) {
        String moveString = Piece.CHESS_NOTATION_VALUE.get(piece.getPieceID());
        return getEndPGNNotationFromMove(boardCopy, piece, startMove, endMove, moveString);
    }

    /**
     * Is the move currently in a promoting pawn state.
     * @param piece The piece moving.
     * @param endMove The end location.
     * @return True if the pawn can be promoted.
     */
    private boolean isPromoting(Piece piece, BoardLocation endMove) {
        return (endMove.row == 0 || endMove.row == 7) && piece instanceof Pawn;
    }

    /**
     * Is the move currently capturing another piece.
     * @param board The Piece[][] to check against.
     * @param endMove The end location.
     * @return
     */
    private boolean isCapturing(Piece[][] board, BoardLocation startMove, BoardLocation endMove) {
        return !(board[endMove.row][endMove.column] instanceof EmptyPiece) || isEnPassant(board, startMove, endMove);
    }

    /**
     * Checks if the move is en-passant.
     * @param board The Piece[][] to check.
     * @param startMove The start location.
     * @param endMove The end location.
     * @return True if en-passant was done.
     */
    public boolean isEnPassant(Piece[][] board, BoardLocation startMove, BoardLocation endMove) {
        if (this.board.getLastPieceMovedId() != Piece.PAWN || this.board.getCurrentMoveLocation() == null)
            return false;
        Piece pieceMoving = board[startMove.row][startMove.column];
        if (!(pieceMoving instanceof Pawn))
            return false;
        if (startMove.row != this.board.getCurrentMoveLocation().row)
            return false;
        int targetRow = (pieceMoving.getTeam() == Team.WHITE_TEAM) ? endMove.row + 1 : endMove.row - 1;
        return board[targetRow][endMove.column] instanceof Pawn;
    }

    /**
     * Checks from a given move and simulates to see if this move
     * will cause a check, or checkmate.
     * @param boardCopy The Piece[][] to check against.
     * @param piece The piece that is moving.
     * @param startMove The start location.
     * @param endMove The end location.
     * @param moveString The current PGN notation to modify.
     * @return A string containing the PGN notation.
     */
    private String getCheckAndCheckmateFromMove(Piece[][] boardCopy, Piece piece, BoardLocation startMove,
            BoardLocation endMove, String moveString) {
        if (isOtherTeamInCheck(boardCopy, piece, startMove, endMove)) {
            moveString += "+";
            int otherTeam = (piece.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
            if (board.getPossibleMovesForTeamWithCheckKingInCheck(boardCopy, otherTeam, false).isEmpty())
                moveString += "+";
        }
        return moveString;
    }

    /**
     * Checks if the other team is in check by simulating a move.
     * @param boardCopy The Piece[][] to check against.
     * @param piece The piece moving.
     * @param startMove The start location.
     * @param endMove The end location.
     * @return True if the other team's king is in check.
     */
    private boolean isOtherTeamInCheck(Piece[][] boardCopy, Piece piece, BoardLocation startMove,
            BoardLocation endMove) {
        board.simulateApplyMove(boardCopy, piece, startMove, endMove);
        int otherTeam = (piece.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(boardCopy, otherTeam, Piece.KING).get(0);
        return ((King) (boardCopy[kingLocation.row][kingLocation.column])).getInCheck();
    }

    static {
        BOARD_LOCATIONS_FROM_STRING = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            BOARD_LOCATIONS_FROM_STRING.put("a" + (i + 1), new BoardLocation(0, (7 - i)));
        }
        for (int i = 0; i < 8; i++) {
            BOARD_LOCATIONS_FROM_STRING.put("b" + (i + 1), new BoardLocation(1, (7 - i)));
        }
        for (int i = 0; i < 8; i++) {
            BOARD_LOCATIONS_FROM_STRING.put("c" + (i + 1), new BoardLocation(2, (7 - i)));
        }
        for (int i = 0; i < 8; i++) {
            BOARD_LOCATIONS_FROM_STRING.put("d" + (i + 1), new BoardLocation(3, (7 - i)));
        }
        for (int i = 0; i < 8; i++) {
            BOARD_LOCATIONS_FROM_STRING.put("e" + (i + 1), new BoardLocation(4, (7 - i)));
        }
        for (int i = 0; i < 8; i++) {
            BOARD_LOCATIONS_FROM_STRING.put("f" + (i + 1), new BoardLocation(5, (7 - i)));
        }
        for (int i = 0; i < 8; i++) {
            BOARD_LOCATIONS_FROM_STRING.put("g" + (i + 1), new BoardLocation(6, (7 - i)));
        }
        for (int i = 0; i < 8; i++) {
            BOARD_LOCATIONS_FROM_STRING.put("h" + (i + 1), new BoardLocation(7, (7 - i)));
        }
    }

    public void setPromotionPiece(String promotionPiece) {
        this.promotionPiece = promotionPiece;
    }

    public void resetPromotionPiece() {
        this.promotionPiece = null;
    }
}