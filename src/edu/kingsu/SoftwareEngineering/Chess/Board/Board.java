package edu.kingsu.SoftwareEngineering.Chess.Board;

import java.util.ArrayList;
import java.util.HashMap;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessUIManager;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNMove;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNReader;

/**
 * The Board class handles all of the logic in chess. It handles
 * generation of algebraic notation (PGN format), loading algebraic notation (PGN format),
 * and handles game states if the game is won or not.
 *
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

    private static final HashMap<String, BoardLocation> BOARD_LOCATIONS_FROM_STRING;

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
    private StringBuilder algebraicRepresentation;

    private ArrayList<String> algebraicNotationMovesList;

    private int undoMoveCount;

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
        algebraicNotationMovesList = new ArrayList<>();
        undoMoveCount = 0;
        firstMove = true;
        moveCount = 1;
        initializeBoard();

        // System.out.println(algebraicRepresentation);
        // TODO: Example of loading a PGN game.
        /*
         * This can be used for many things, loading a game, recreating a game with a different game mode, etc.
         */
        // PGNReader reader = new PGNReader();
        // for (PGNMove move : reader.getMovesFromFile("my-test/stalemate.pgn")) {
        //     applyPGNMove(move, true);
        //     // applyMoveAlgebraicNotation(move.getMoveString(), true);
        // }
    }

    /**
     * Creates a new board from the data taken from a different board
     *
     * @param pieces       the array of pieces and their position
     * @param moveCount    the amount of moves made
     * @param algebraicRep the current sequence of moves in a StringBuilder
     */
    public Board(Piece[][] pieces, int moveCount, StringBuilder algebraicRep) {
        algebraicRepresentation = algebraicRep;
        if (moveCount >= 1) {
            firstMove = false;
        }
        this.moveCount = moveCount;
        board = pieces;
    }

    /**
     * Returns a copy of the board as a Board object
     *
     * @return a deep copy of the board
     */
    public Board copy() {
        return new Board(board, moveCount, algebraicRepresentation);
    }

    /**
     * Initializes the board to play a game and not to
     * read the PGN game.
     */
    public void initializeBoard() {
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
     * Undos a move on the board.
     * @return True if successful, false if otherwise.
     */
    public boolean undoMove() {
        // This function basically rebuilds the board from scratch,
        // and it will take the current moves that are made, up to
        // a certain index. The undoMoveCount is that index, and when
        // it is incremented, we go to X amount moves less than the moves that
        // were made.
        if (algebraicNotationMovesList.size() - undoMoveCount <= 0)
            return false;
        initializeBoard();
        ChessUIManager.clearMovesLabel();
        firstMove = true;
        moveCount = 1;
        undoMoveCount++;
        algebraicRepresentation = new StringBuilder();
        for (int i = 0; i < algebraicNotationMovesList.size() - undoMoveCount; i++) {
            applyMoveAlgebraicNotation(algebraicNotationMovesList.get(i), false);
        }
        return true;
    }

    /**
     * Redos a move on the board.
     * @return True if successful, false if otherwise.
     */
    public boolean redoMove() {
        // Decrements the undoMoveCount, which makes the array go one further
        // in redoing, which allows us to redo more moves until we reach the limit
        // which is back to the most recent version of the game.
        if (undoMoveCount <= 0)
            return false;
        initializeBoard();
        ChessUIManager.clearMovesLabel();
        firstMove = true;
        moveCount = 1;
        undoMoveCount--;
        algebraicRepresentation = new StringBuilder();
        for (int i = 0; i < algebraicNotationMovesList.size() - undoMoveCount; i++) {
            applyMoveAlgebraicNotation(algebraicNotationMovesList.get(i), false);
        }
        return true;
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
     * Applies a move based on the PGNMove class.
     * @param move The move to do.
     * @param extraCheck Should the algebraicNotation be overriden. Keep false unless you know why to make true.
     * @return True if move as applied, false otherwise.
     */
    public MoveResult applyPGNMove(PGNMove move, boolean extraCheck) {
        // if (move.hasComment()) {
        //     System.out.println("Comment: " + move.getComment() + ", for move: " + move.getMoveString());
        // }
        return applyMoveAlgebraicNotation(move.getMoveString(), extraCheck);
    }

    /**
     * Applys a move based on the algebraic notation.
     * @param notation The notation to apply a move to. Example, Nf3 moves Knight to f3 if possible.
     * @param extraCheck If algebraic notation should be overriden. Make it true if not known how it works.
     * @return True if move was successful.
     */
    public MoveResult applyMoveAlgebraicNotation(String notation, boolean extraCheck) {
        MoveResult result = new MoveResult();
        int team = (firstMove) ? Team.WHITE_TEAM : Team.BLACK_TEAM;
        // Replace the + sign in the PGN notation as the board doesn't care
        // what the notation says as it has it's own rules.
        String noPlus = notation.replace("+", "");
        noPlus = noPlus.replace("x", "");
        if (noPlus.equalsIgnoreCase("O-O")) {
            // Only ever one king
            BoardLocation kingLocation = getBoardLocationsForTeamForPiece(team, Piece.KING).get(0);
            BoardLocation endLocation = new BoardLocation(kingLocation.column + 3, kingLocation.row);
            return applyMove(getBoard()[kingLocation.row][kingLocation.column], kingLocation, endLocation, extraCheck);
        } else if (noPlus.equalsIgnoreCase("O-O-O")) {
            // Only ever one king
            BoardLocation kingLocation = getBoardLocationsForTeamForPiece(team, Piece.KING).get(0);
            BoardLocation endLocation = new BoardLocation(kingLocation.column - 4, kingLocation.row);
            return applyMove(getBoard()[kingLocation.row][kingLocation.column], kingLocation, endLocation, extraCheck);
        } else if (noPlus.contains("=")) {
            String[] moves = noPlus.split("=");
            int pieceId = Piece.PAWN;
            String locationString = moves[0].substring(moves[0].length() - 2);
            BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
            ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                    boardLocation);
            if (pieceLocation.size() != 1)
                return result;
            Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
            if (!(boardLocation.row == 0 || boardLocation.row == 7))
                return result;
            if (!(pieceToMove instanceof Pawn))
                return result;
            return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, false, true, moves[1]);
        }
        // Piece moving without pawn.
        if (noPlus.length() == 2) {
            int pieceId = Piece.PAWN;
            String locationString = noPlus;
            BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
            ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                    boardLocation);
            if (pieceLocation.size() != 1)
                return result;
            Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
            return applyMove(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck);
        } else if (noPlus.length() == 3) {
            if (Character.isUpperCase(noPlus.charAt(0))) {
                int pieceId = Piece.PIECE_ID_FROM_STRING.get(noPlus.subSequence(0, 1));
                String locationString = noPlus.substring(1, noPlus.length());
                BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
                ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                        boardLocation);
                if (pieceLocation.size() != 1)
                    return result;
                Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
                return applyMove(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck);
            } else {
                int pieceId = Piece.PAWN;
                String locationString = noPlus.substring(1, noPlus.length());
                BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
                // ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                //         boardLocation);
                ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceForColumn(team, pieceId,
                        noPlus.charAt(0) - 'a');
                if (pieceLocation.size() != 1)
                    return result;
                Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
                return applyMove(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck);
            }
        } else if (noPlus.length() == 4) {
            if (Character.isUpperCase(noPlus.charAt(0))) {
                int pieceId = Piece.PIECE_ID_FROM_STRING.get(noPlus.subSequence(0, 1));
                String locationString = noPlus.substring(2, noPlus.length());
                BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
                // ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                //         boardLocation);
                ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceForColumn(board, team, pieceId,
                        noPlus.charAt(1) - 'a');
                if (pieceLocation.size() != 1)
                    return result;
                Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
                return applyMove(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck);
            } else {
                int pieceId = Piece.PIECE_ID_FROM_STRING.get(noPlus.subSequence(0, 1));
                String locationString = noPlus.substring(2, noPlus.length());
                BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
                // ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                //         boardLocation);
                ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceForRow(board, team, pieceId,
                        (int) noPlus.charAt(1));
                if (pieceLocation.size() != 1)
                    return result;
                Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
                return applyMove(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck);
            }
        }
        return result;
    }

    /**
     * Applys a move based on the algebraic notation.
     * Will override notation.
     * @param notation The notation to apply a move to. Example, Nf3 moves Knight to f3 if possible.
     * @return True if move was successful.
     */
    public MoveResult applyMoveAlgebraicNotation(String notation) {
        return applyMoveAlgebraicNotation(notation, true);
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
    public MoveResult applyMove(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove, boolean extraCheck) {
        return applyMoveInternal(pieceMoving, startMove, endMove, extraCheck, false, "");
    }

    /**
     * Check if a move can be applied, then do it.
     * Will also generate algebraic notation for the move in here
     * and apply it to the algebraicNotation StringBuilder.
     *
     * @param pieceMoving The chess piece being moved.
     * @param startMove   The starting move of the piece.
     * @param endMove     The target location of the piece.
     * @param extraCheck  If algebraicNotation should be overriden
     * @return True if the move was successful.
     */
    private MoveResult applyMoveInternal(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
            boolean extraCheck, boolean doPromotion, String promotedPieceValue) {
        MoveResult result = new MoveResult();
        int team = pieceMoving.getTeam();
        int piecesMoveToSameLocation = 0;
        // Get all pieces of type that can move to the "endMove" location
        for (BoardLocation location : getPossibleMovesForTeamFromPiece(team, pieceMoving.getPieceID(), true)) {
            if (location.row == endMove.row && location.column == endMove.column) {
                piecesMoveToSameLocation++;
            }
        }
        if (piecesMoveToSameLocation == 0)
            return result;
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
        if ((board[startMove.row][startMove.column] instanceof King
                && board[endMove.row][endMove.column] instanceof Rook)
                && board[startMove.row][startMove.column].getTeam() == board[endMove.row][endMove.column].getTeam()) {
            moveString = new StringBuilder();
            if (Math.abs(startMove.column - endMove.column) == 4) {
                Rook rookCopy = (Rook) board[endMove.row][endMove.column]
                        .copy(board[endMove.row][endMove.column].getTeam());
                board[startMove.row][startMove.column - 2] = pieceMoving;
                board[startMove.row][startMove.column - 2].moved();
                board[startMove.row][startMove.column - 1] = rookCopy;
                board[startMove.row][startMove.column - 1].moved();

                board[endMove.row][endMove.column] = new EmptyPiece();
                board[startMove.row][startMove.column] = new EmptyPiece();
                moveString.append("O-O-O");
            } else {
                Rook rookCopy = (Rook) board[endMove.row][endMove.column]
                        .copy(board[endMove.row][endMove.column].getTeam());
                board[startMove.row][startMove.column + 2] = pieceMoving;
                board[startMove.row][startMove.column + 2].moved();
                board[startMove.row][startMove.column + 1] = rookCopy;
                board[startMove.row][startMove.column + 1].moved();

                board[endMove.row][endMove.column] = new EmptyPiece();
                board[startMove.row][startMove.column] = new EmptyPiece();
                moveString.append("O-O");
            }
        } else {
            board[endMove.row][endMove.column] = pieceMoving;
            board[endMove.row][endMove.column].moved();
            board[startMove.row][startMove.column] = new EmptyPiece();
        }
        int otherTeam = (pieceMoving.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        if (checkKingInCheck(pieceMoving, otherTeam)) {
            moveString.append("+");
            if (getPossibleMovesForTeamWithChecking(otherTeam).size() == 0) {
                moveString.append("+");
                result.isCheckmate = true;
                result.checkmateTeam = otherTeam;
            }
        } else {
            if (getPossibleMovesForTeamWithChecking(otherTeam).size() == 0) {
                result.isStalemate = true;
                result.checkmateTeam = otherTeam;
            }
        }
        checkKingInCheck(pieceMoving, pieceMoving.getTeam());
        if (doPromotion) {
            moveString.append("=" + promotedPieceValue);
            int pieceIdFromString = Piece.PIECE_ID_FROM_STRING.get(promotedPieceValue);
            Piece newPiece = Piece.createPieceFromTeam(pieceIdFromString, team);
            board[endMove.row][endMove.column] = newPiece;
        } else if (pieceMoving instanceof Pawn && (endMove.row == 0 || endMove.row == 7)) {
            result.promotionLocation = new BoardLocation(endMove.column, endMove.row);
            result.isPromotion = true;
            result.promoteTeam = team;
        }
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
        // Reset the array of algebraic notations as we need to do this
        // because if the player undos a move, and then makes a different move
        // we need to delete the future redos as they lose those moves
        // as they are not valid anymore.
        if (extraCheck) {
            ArrayList<String> newList = new ArrayList<>();
            for (int i = 0; i < algebraicNotationMovesList.size() - undoMoveCount; i++) {
                newList.add(algebraicNotationMovesList.get(i));
            }
            // algebraicNotationMovesList.add(moveString.toString());
            newList.add(moveString.toString());
            algebraicNotationMovesList.clear();
            // algebraicNotationMovesList = newList;
            for (int i = 0; i < newList.size(); i++) {
                algebraicNotationMovesList.add(newList.get(i));
            }
            undoMoveCount = 0;
            // moveCount = algebraicNotationMovesList.size();
        }
        result.wasSuccessful = true;
        return result;
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
    public MoveResult applyMove(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
        return applyMove(pieceMoving, startMove, endMove, true);
    }

    /**
     * Promotes a pawn.
     * @param location The location to promote.
     * @param piece The piece to promote too.
     */
    public void promotePawn(BoardLocation location, Piece piece) {
        if (!algebraicNotationMovesList.get(algebraicNotationMovesList.size() - 1).contains("=")) {
            String newNotation = algebraicNotationMovesList.get(algebraicNotationMovesList.size() - undoMoveCount - 1);
            newNotation += "=" + Piece.chessNotationValue.get(piece.getPieceID());
            algebraicNotationMovesList.set(algebraicNotationMovesList.size() - undoMoveCount - 1, newNotation);
        }
        ChessUIManager.clearMovesLabel();
        boolean first = true;
        int moveC = 1;
        algebraicRepresentation = new StringBuilder();
        for (int i = 0; i < algebraicNotationMovesList.size() - undoMoveCount; i++) {
            if (first) {
                first = false;
                algebraicRepresentation.append(moveC + ". " + algebraicNotationMovesList.get(i));
                ChessUIManager.appendMovesLabel(moveC + ". " + algebraicNotationMovesList.get(i));
            } else {
                algebraicRepresentation.append(" " + algebraicNotationMovesList.get(i) + "\n");
                ChessUIManager.appendMovesLabel(" " + algebraicNotationMovesList.get(i) + "\n");
                first = true;
                moveC++;
            }
        }
        board[location.row][location.column] = piece;
    }

    private void promotePawnNoNotation(BoardLocation location, Piece piece) {
        // System.out.println("\n\n\n\n\n\n");
        // for (int i = 0; i < algebraicNotationMovesList.size() - undoMoveCount; i++) {
        //     System.out.println(algebraicNotationMovesList.get(i));
        // }
        // ChessUIManager.clearMovesLabel();
        // boolean first = true;
        // algebraicRepresentation = new StringBuilder();
        // moveCount = 1;
        // for (int i = 0; i < algebraicNotationMovesList.size() - undoMoveCount; i++) {
        //     if (first) {
        //         firstMove = false;
        //         first = false;
        //         algebraicRepresentation.append(moveCount + ". " + algebraicNotationMovesList.get(i));
        //         ChessUIManager.appendMovesLabel(moveCount + ". " + algebraicNotationMovesList.get(i));
        //         continue;
        //     } else {
        //         firstMove = true;
        //         first = true;
        //         algebraicRepresentation.append(" " + algebraicNotationMovesList.get(i) + "\n");
        //         ChessUIManager.appendMovesLabel(" " + algebraicNotationMovesList.get(i) + "\n");
        //         moveCount++;
        //         continue;
        //     }
        // }
        board[location.row][location.column] = piece;
    }

    /**
     * Will "simulate" a move. Essentially it applys the same logic as applyMove
     * but it does it with a board passed in.
     *
     * @param board       The board to apply the move to.
     * @param pieceMoving The piece moving.
     * @param startMove   Starting location of the piece.
     * @param endMove     Desired end location of the piece.
     */
    public void simulateApplyMove(Piece[][] board, Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
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
     * @param team        The team to check for their king being in check.
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
    public ArrayList<BoardLocation> getPossibleMoves(Piece piece, BoardLocation location, boolean extraCheck) {
        return getPossibleMoves(board, piece, location, extraCheck);
        // return piece.getPossibleMoves(board, location);
    }

    /**
     * Returns all possible moves for the current piece.
     *
     * @param board    The board to check for the locations on.
     * @param piece    The piece to get the possible moves for.
     * @param location The location that the piece is at.
     * @return
     */
    public ArrayList<BoardLocation> getPossibleMoves(Piece[][] board, Piece piece, BoardLocation location,
            boolean extraCheck) {
        // Get the current piece's team, and then check if their king is in check
        // If in check, only return possible moves that will make them not in check
        // anymore.
        int team = piece.getTeam();
        Piece[][] boardCopy = getBoard();
        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(team, Piece.KING).get(0);
        King kingPiece = (King) boardCopy[kingLocation.row][kingLocation.column];
        if (kingPiece.inCheck) {
            ArrayList<BoardLocation> returnVal = new ArrayList<>();
            ArrayList<BoardLocation> pieceMoves = piece.getPossibleMoves(this, board, location, extraCheck);
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
            ArrayList<BoardLocation> pieceMoves = piece.getPossibleMoves(this, board, location, extraCheck);
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
    }

    /**
     * Gets all the possible moves for a specific team
     *
     * @param team The team to get all possible moves for.
     * @return ArrayList of BoardLocations for the possible moves a team can make.
     */
    public ArrayList<BoardLocation> getPossibleMovesForTeam(int team) {
        return getPossibleMovesForTeam(board, team);
    }

    /**
     * Gets all the possible moves for a specific team, ignoring
     * if the king is in check afterwards.
     *
     * @param board The board to check possible moves for the team.
     * @param team  The team to get all possible moves for.
     * @return Arraylist of BoardLocations for the possible moves a team can make.
     */
    public ArrayList<BoardLocation> getPossibleMovesForTeam(Piece[][] board, int team) {
        ArrayList<BoardLocation> possibleMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team) {
                    for (BoardLocation m : board[i][j].getPossibleMoves(this, board, new BoardLocation(j, i), false)) {
                        possibleMoves.add(m);
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Gets all the possible moves for a specific team that are valid.
     *
     * @param board The board to check possible moves for the team.
     * @param team  The team to get all possible moves for.
     * @return Arraylist of BoardLocations for the possible moves a team can make.
     */
    public ArrayList<BoardLocation> getPossibleMovesForTeamWithChecking(int team) {
        ArrayList<BoardLocation> possibleMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team) {
                    for (BoardLocation m : getPossibleMoves(board[i][j], new BoardLocation(j, i), false)) {
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
     * @param team    The team to get the moves for.
     * @param pieceId The specific piece to get the moves for.
     * @return ArrayList of BoardLocations of all possible moves of all pieces of certain type for team.
     */
    public ArrayList<BoardLocation> getPossibleMovesForTeamFromPiece(int team, int pieceId, boolean extraCheck) {
        ArrayList<BoardLocation> possibleMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team && board[i][j].getPieceID() == pieceId) {
                    for (BoardLocation m : board[i][j].getPossibleMoves(this, board, new BoardLocation(j, i),
                            extraCheck)) {
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
     * @param team    The team to get the moves for.
     * @param pieceId The piece to get locations for.
     * @return ArrayList of BoardLocations of all locations of the pieces for the team.
     */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPiece(int team, int pieceId) {
        // ArrayList<BoardLocation> locations = new ArrayList<>();
        // for (int i = 0; i < board.length; i++) {
        //     for (int j = 0; j < board[i].length; j++) {
        //         if (board[i][j].getTeam() == team && board[i][j].getPieceID() == pieceId) {
        //             locations.add(new BoardLocation(j, i));
        //         }
        //     }
        // }
        // return locations;
        return getBoardLocationsForTeamForPiece(board, team, pieceId);
    }

    /**
     * Gets the locations of pieces for a team on the board.
     *
     * @param board   The board to get the pieces locations for.
     * @param team    The team to get the moves for.
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
     * Gets the locations of pieces for a team on the board.
     *
     * @param targetLocation The board to get the pieces locations for.
     * @param team           The team to get the moves for.
     * @param pieceId        The piece to get locations for.
     * @return ArrayList of BoardLocations of all locations of the pieces for the team.
                                                                              */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPieceToTargetLocation(int team,
            int pieceId, BoardLocation targetLocation) {
        return getBoardLocationsForTeamForPieceToTargetLocation(board, team, pieceId, targetLocation);
    }

    /**
     * Gets the locations of pieces for a team on the board.
     *
     * @param board   The board to get the pieces locations for.
     * @param team    The team to get the moves for.
     * @param pieceId The piece to get locations for.
     * @return ArrayList of BoardLocations of all locations of the pieces for the team.
                                                                              */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPieceToTargetLocation(Piece[][] board, int team,
            int pieceId, BoardLocation targetLocation) {
        ArrayList<BoardLocation> locations = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team && board[i][j].getPieceID() == pieceId) {
                    for (BoardLocation location : getPossibleMoves(board, board[i][j], new BoardLocation(j, i),
                            false)) {
                        if (location.row == targetLocation.row && location.column == targetLocation.column) {
                            locations.add(new BoardLocation(j, i));
                        }
                    }
                    // locations.add(new BoardLocation(j, i));
                }
            }
        }
        return locations;
    }

    /**
     * Gets the board locations for a team's set of pieces that
     * fall within a specific column.
     *
     * @param team    The team to get the pieces from.
     * @param pieceId The piece id to check.
     * @param column  The column to check against.
     * @return The ArrayList of BoardLocations for the team's piece.
                                                                       */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPieceForColumn(int team, int pieceId,
            int column) {
        return getBoardLocationsForTeamForPieceForColumn(board, team, pieceId, column);
    }

    /**
     * Gets the board locations for a team's set of pieces that
     * fall within a specific row.
     *
     * @param team    The team to get the pieces from.
     * @param pieceId The piece id to check.
     * @param row     The row to check against.
     * @return The ArrayList of BoardLocations for the team's piece.
                                                                    */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPieceForRow(int team, int pieceId,
            int row) {
        return getBoardLocationsForTeamForPieceForRow(board, team, pieceId, row);
    }

    /**
     * Gets the board locations for a team's set of pieces that
     * fall within a specific column.
     *
     * @param board   The board to check.
     * @param team    The team to get the pieces from.
     * @param pieceId The piece id to check.
     * @param column  The column to check against.
     * @return The ArrayList of BoardLocations for the team's piece.
                                                                       */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPieceForColumn(Piece[][] board, int team, int pieceId,
            int column) {
        ArrayList<BoardLocation> locations = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team && board[i][j].getPieceID() == pieceId && j == column) {
                    locations.add(new BoardLocation(j, i));
                }
            }
        }
        return locations;
    }

    /**
     * Gets the board locations for a team's set of pieces that
     * fall within a specific row.
     *
     * @param board   The board to check.
     * @param team    The team to get the pieces from.
     * @param pieceId The piece id to check.
     * @param row     The row to check against.
     * @return The ArrayList of BoardLocations for the team's piece.
                                                                    */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPieceForRow(Piece[][] board, int team, int pieceId,
            int row) {
        ArrayList<BoardLocation> locations = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team && board[i][j].getPieceID() == pieceId && i == row) {
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
