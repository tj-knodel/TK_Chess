package edu.kingsu.SoftwareEngineering.Chess.Board;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessUIManager;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNMove;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNReader;

import javax.swing.*;

/**
 * The Board class handles all the logic in chess. It handles
 * generation of algebraic notation (PGN format), loading algebraic notation (PGN format),
 * and handles game states if the game is won or not.
 *
 * @author Daniell Buchner
 * @version 1.0.0
 */
public class Board {

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

    /**
     * The list of algebraic notation moves that have been applied.
     * Only has the actual move, not the PGN file format.
     */
    private ArrayList<String> algebraicNotationMovesList;

    private int undoMoveCount;

    /**
     * The boolean flipping for PGN format string.
     */
    private boolean firstMove;

    /**
     * The last move location of the last applied move.
     */
    private BoardLocation lastMoveLocation;

    /**
     * The location of the currently applied move.
     */
    private BoardLocation currentMoveLocation;

    /**
     * The move counter for the PGN format.
     */
    private int moveCount;

    /**
     * The GameLoop for the board to keep track of.
     * Used to call methods to follow the MVC pattern.
     */
    private final GameLoop gameLoop;

    /**
     * If the game is paused or not.
     */
    private boolean isPaused;

    private MoveResult lastMoveResult;

    ArrayList<PGNMove> moves;

    /**
     * The Board constructor.
     * For now just creates the board and initializes with two player game.
     */
    public Board(GameLoop gameLoop) {
        // board = new Piece[8][8];
        algebraicRepresentation = new StringBuilder();
        algebraicNotationMovesList = new ArrayList<>();
        undoMoveCount = 0;
        firstMove = true;
        moveCount = 1;
        isPaused = false;
        this.gameLoop = gameLoop;
        initializeBoard();

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
    public Board(Piece[][] pieces, int moveCount, StringBuilder algebraicRep, GameLoop gameLoop) {
        algebraicRepresentation = new StringBuilder();//algebraicRep;
        algebraicNotationMovesList = new ArrayList<>();
        if (moveCount >= 1) {
            firstMove = false;
        }
        this.moveCount = moveCount;
        this.gameLoop = gameLoop;
        board = new Piece[8][8];
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                board[i][j] = pieces[i][j].copy(pieces[i][j].getTeam());
            }
        }
    }

    /**
     * Returns a copy of the board as a Board object
     *
     * @return a deep copy of the board
     */
    public Board copy() {
        return new Board(board, moveCount, algebraicRepresentation, gameLoop);
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
     * Gets the game isPaused value.
     *
     * @return True if the game is paused.
     */
    public boolean getIsPaused() {
        return isPaused;
    }

    /**
     * Sets the game to be paused or not.
     *
     * @param isPaused The value to set the game to.
     */
    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * Gets the last move result.
     *
     * @return The MoveResult of the last move.
     */
    public MoveResult getLastMoveResult() {
        return lastMoveResult;
    }

    /**
     * Load a file of the PGN format.
     *
     * @param file The file to load.
     */
    public void loadPGNFile(File file) {
        PGNReader reader = new PGNReader();
        moves = reader.getMovesFromFile(file.getAbsolutePath());
        for (PGNMove move : reader.getMovesFromFile(file.getAbsolutePath())) {
            //            System.out.println("Applying move: " + move.getMoveString());
            //            JOptionPane.showConfirmDialog(null, move.getMoveString());
            MoveResult result = applyPGNMove(move, true);
            if (!result.wasSuccessful) {
                JOptionPane.showConfirmDialog(null, "Could not apply move: " + move.getMoveString());
            }
        }
    }

    /**
     * Get the last move location.
     *
     * @return The last move location.
     */
    public BoardLocation getLastMoveLocation() {
        return lastMoveLocation;
    }

    /**
     * Get the current move location.
     *
     * @return The current move location.
     */
    public BoardLocation getCurrentMoveLocation() {
        return currentMoveLocation;
    }

    /**
     * Did the player undo until the start of the game.
     *
     * @return If the player undo'ed until the start of the game.
     */
    public boolean isAtStart() {
        return undoMoveCount == algebraicNotationMovesList.size();
    }

    public void resetNotation() {
        this.algebraicNotationMovesList = new ArrayList<>();
        this.algebraicRepresentation = new StringBuilder();
    }

    /**
     * Undos a move on the board.
     *
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
        return undoOrRedoMove(true);
    }

    /**
     * Redos a move on the board.
     *
     * @return True if successful, false if otherwise.
     */
    public boolean redoMove() {
        // Decrements the undoMoveCount, which makes the array go one further
        // in redoing, which allows us to redo more moves until we reach the limit
        // which is back to the most recent version of the game.
        if (undoMoveCount <= 0)
            return false;
        return undoOrRedoMove(false);
    }

    /**
     * Does the undo or redo logic based on the parameter.
     *
     * @param isUndo Is undoing currently.
     * @return True if the undo/redo was successful.
     */
    private boolean undoOrRedoMove(boolean isUndo) {
        if (getIsPaused())
            return false;
        initializeBoard();
        ChessUIManager.clearMovesLabel();
        firstMove = true;
        moveCount = 1;
        if (isUndo)
            undoMoveCount++;
        else
            undoMoveCount--;
        algebraicRepresentation = new StringBuilder();
        if (this.moves != null) {
            for (int i = 0; i < moves.size() - undoMoveCount; i++) {
                MoveResult result = applyMoveAlgebraicNotation(moves.get(i).getMoveString(), false, true, false);
            }
        } else {
            for (int i = 0; i < algebraicNotationMovesList.size() - undoMoveCount; i++) {
                MoveResult result = applyMoveAlgebraicNotation(algebraicNotationMovesList.get(i), false, true, false);
            }
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
     *
     * @param move       The move to do.
     * @param extraCheck Should the algebraicNotation be overriden. Keep false unless you know why to make true.
     * @return MoveResult of the moved that is applied.
     */
    public MoveResult applyPGNMove(PGNMove move, boolean extraCheck) {
        // if (move.hasComment()) {
        //     System.out.println("Comment: " + move.getComment() + ", for move: " + move.getMoveString());
        // }
        return applyMoveAlgebraicNotation(move.getMoveString(), extraCheck, true, true);
    }

    /**
     * Applys a move based on the algebraic notation.
     *
     * @param notation   The notation to apply a move to. Example, Nf3 moves Knight to f3 if possible.
     * @param extraCheck If algebraic notation should be overriden. Make it true if not known how it works.
     * @return True if move was successful.
     */
    public MoveResult applyMoveAlgebraicNotation(String notation, boolean extraCheck, boolean doNotation,
            boolean updateGUI) {
        MoveResult result = new MoveResult();
        int team = (firstMove) ? Team.WHITE_TEAM : Team.BLACK_TEAM;
        // Replace the + sign in the PGN notation as the board doesn't care
        // what the notation says as it has its own rules.
        String notationString = notation.replace("+", "");
        boolean capture = notation.contains("x");
        notationString = notationString.replace("x", "");
        if (notationString.equalsIgnoreCase("O-O")) {
            return applyMoveAlgebraicNotationCastlingShortSide(extraCheck, team, updateGUI);
        } else if (notationString.equalsIgnoreCase("O-O-O")) {
            return applyMoveAlgebraicNotationCastlingLongSide(extraCheck, team, updateGUI);
        } else if (notationString.contains("=")) {
            return applyMoveAlgebraicNotationPromotion(notationString, team, result, updateGUI);
        }
        // Piece moving without pawn.
        if (notationString.length() == 2) {
            return applyMoveAlgebraicNotationPawnBasic(extraCheck, doNotation, notationString, team, result, updateGUI);
        } else if (notationString.length() == 3) {
            if (Character.isUpperCase(notationString.charAt(0))) {
                return applyMoveAlgebraicNotationPiece(extraCheck, doNotation, notationString, team, result, updateGUI);
            } else {
                if (capture) {
                }
                //                System.out.println(notationString);
                return applyMoveAlgebraicNotationCaptureWithPawn(extraCheck, doNotation, notationString, team,
                        result, updateGUI);
                //                return applyMoveAlgebraicNotationPawn(extraCheck, doNotation, notationString, team, result);
            }
        } else if (notationString.length() == 4) {
            if (Character.isUpperCase(notationString.charAt(0))) {
                return applyMoveAlgebraicNotationCaptureWithPiece(extraCheck, doNotation, notationString, team, result,
                        updateGUI);
            } else {
                return applyMoveAlgebraicNotationCaptureWithPawn(extraCheck, doNotation, notationString, team, result,
                        updateGUI);
            }
        }
        return result;
    }

    private MoveResult applyMoveAlgebraicNotationCaptureWithPawn(boolean extraCheck, boolean doNotation,
            String notationString, int team, MoveResult result, boolean updateGUI) {
        //        JOptionPane.showConfirmDialog(null, notationString);
        int pieceId = Piece.PAWN;//Piece.PIECE_ID_FROM_STRING.get(notationString.substring(1, 2));
        String locationString = notationString.substring(1);
        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
        ArrayList<BoardLocation> pieceLocation;
        pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                boardLocation);
        if (pieceLocation.size() != 1) {
            pieceLocation = getBoardLocationsForTeamForPieceToTargetLocationForColumn(team,
                    pieceId, boardLocation,
                    (int) notationString.charAt(0) - 'a');
            System.out.println(notationString + " " + pieceLocation.size());
        }
        if (pieceLocation.size() != 1)
            return result;
        Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation, updateGUI);
    }

    private MoveResult applyMoveAlgebraicNotationCaptureWithPiece(boolean extraCheck, boolean doNotation,
            String notationString, int team, MoveResult result, boolean updateGUI) {
        int pieceId = Piece.PIECE_ID_FROM_STRING.get(notationString.substring(0, 1));
        String locationString = notationString.substring(2);
        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
        // ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
        //         boardLocation);
        ArrayList<BoardLocation> pieceLocation = new ArrayList<>();
        if (Character.isDigit(notationString.charAt(1))) {
            //            JOptionPane.showConfirmDialog(null, 8 - (notationString.charAt(1) - '0'));
            pieceLocation = getBoardLocationsForTeamForPieceForRow(board, team, pieceId,
                    8 - (notationString.charAt(1) - '0'));
        } else {
            pieceLocation = getBoardLocationsForTeamForPieceToTargetLocationForColumn(team, pieceId,
                    boardLocation, notationString.charAt(1) - 'a');
            if (pieceLocation.size() != 1)
                pieceLocation = getBoardLocationsForTeamForPieceForColumn(board, team, pieceId,
                        notationString.charAt(1) - 'a');
        }
        if (pieceLocation.size() != 1)
            return result;
        Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation, updateGUI);
    }

    private MoveResult applyMoveAlgebraicNotationPawn(boolean extraCheck, boolean doNotation, String notationString,
            int team, MoveResult result, boolean updateGUI) {
        int pieceId = Piece.PAWN;
        String locationString = notationString.substring(1);
        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
        ArrayList<BoardLocation> pieceLocationCheck = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                boardLocation);
        if (pieceLocationCheck.size() != 1) {
            ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceForColumn(team, pieceId,
                    notationString.charAt(0) - 'a');
            if (pieceLocation.size() != 1)
                return result;
            Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
            return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation,
                    updateGUI);
        }
        Piece pieceToMove = getBoard()[pieceLocationCheck.get(0).row][pieceLocationCheck.get(0).column];
        return applyMoveInternal(pieceToMove, pieceLocationCheck.get(0), boardLocation, extraCheck, doNotation,
                updateGUI);
    }

    private MoveResult applyMoveAlgebraicNotationPiece(boolean extraCheck, boolean doNotation, String notationString,
            int team, MoveResult result, boolean updateGUI) {
        int pieceId = Piece.PIECE_ID_FROM_STRING.get(notationString.substring(0, 1));
        String locationString = notationString.substring(1);
        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
        ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                boardLocation);
        if (pieceLocation.size() != 1)
            return result;
        Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation, updateGUI);
    }

    private MoveResult applyMoveAlgebraicNotationPawnBasic(boolean extraCheck, boolean doNotation,
            String notationString, int team, MoveResult result, boolean updateGUI) {
        int pieceId = Piece.PAWN;
        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(notationString);
        ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
                boardLocation);
        if (pieceLocation.size() != 1)
            return result;
        Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation, updateGUI);
    }

    private MoveResult applyMoveAlgebraicNotationPromotion(String notationString, int team, MoveResult result,
            boolean updateGUI) {
        String[] moves = notationString.split("=");
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
        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, false, true, updateGUI);
    }

    private MoveResult applyMoveAlgebraicNotationCastlingLongSide(boolean extraCheck, int team, boolean updateGUI) {
        // Only ever one king
        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(team, Piece.KING).get(0);
        BoardLocation endLocation = new BoardLocation(kingLocation.column - 4, kingLocation.row);
        return applyMove(updateGUI, getBoard()[kingLocation.row][kingLocation.column], kingLocation, endLocation,
                extraCheck);
    }

    private MoveResult applyMoveAlgebraicNotationCastlingShortSide(boolean extraCheck, int team, boolean updateGUI) {
        // Only ever one king
        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(team, Piece.KING).get(0);
        BoardLocation endLocation = new BoardLocation(kingLocation.column + 3, kingLocation.row);
        return applyMove(updateGUI, getBoard()[kingLocation.row][kingLocation.column], kingLocation, endLocation,
                extraCheck);
    }

    /**
     * Applies a move based on the algebraic notation.
     * Will override notation.
     *
     * @param notation The notation to apply a move to. Example, Nf3 moves Knight to f3 if possible.
     * @return True if move was successful.
     */
    public MoveResult applyMoveAlgebraicNotation(String notation) {
        return applyMoveAlgebraicNotation(notation, true, true, true);
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
    private MoveResult applyMove(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
            boolean extraCheck) {
        return applyMoveInternal(pieceMoving, startMove, endMove, extraCheck, false, true);
    }

    private MoveResult applyMove(boolean updateGUI, Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
            boolean extraCheck) {
        return applyMoveInternal(pieceMoving, startMove, endMove, extraCheck, false, updateGUI);
    }

    /**
     * Check if a move can be applied, then do it.
     * Will also generate algebraic notation for the move in here
     * and apply it to the algebraicNotation StringBuilder.
     *
     * @param pieceMoving The chess piece being moved.
     * @param startMove   The starting move of the piece.
     * @param endMove     The target location of the piece.
     * @param extraCheck  Should algebraicNotation be overridden.
     * @return True if the move was successful.
     */
    public MoveResult applyMove(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove, boolean extraCheck,
            boolean doNotation) {
        return applyMoveInternal(pieceMoving, startMove, endMove, extraCheck, doNotation, true);
    }

    /**
     * Gets the current team's turn.
     *
     * @return The turn of the current team.
     */
    public int getTeamTurn() {
        return (firstMove) ? Team.WHITE_TEAM : Team.BLACK_TEAM;
    }

    /**
     * Check if a move can be applied, then do it.
     * Will also generate algebraic notation for the move in here
     * and apply it to the algebraicNotation StringBuilder.
     *
     * @param pieceMoving      The chess piece being moved.
     * @param startMove        The starting move of the piece.
     * @param endMove          The target location of the piece.
     * @param overrideNotation If algebraicNotation should be overriden
     * @return True if the move was successful.
     */
    private MoveResult applyMoveInternal(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
            boolean overrideNotation, boolean doNotation, boolean updateGUI) {
        MoveResult result = new MoveResult();
        if (getIsPaused())
            return result;
        int team = pieceMoving.getTeam();
        if (team != getTeamTurn())
            return result;

        int piecesMoveToSameLocation = getPiecesMoveToSameLocation(pieceMoving, endMove, team);
        if (piecesMoveToSameLocation == 0)
            return result;

        StringBuilder moveString = new StringBuilder();
        handleMoveCreateBasicNotation(pieceMoving, startMove, endMove, piecesMoveToSameLocation, moveString);

        // Checks if we can castle, apply the correct one and set the chess notation correctly.
        boolean castle = false;
        if (handleMoveCheckIsCastling(startMove, endMove)) {
            castle = true;
            moveString = new StringBuilder();
            if (handleMoveCheckCastlingLongSide(startMove, endMove)) {
                handleMoveDoCastlingLongSide(pieceMoving, startMove, endMove);
                moveString.append("O-O-O");
            } else {
                handleMoveDoCastlingShortSide(pieceMoving, startMove, endMove);
                moveString.append("O-O");
            }
        } else {
            handleMoveNormal(pieceMoving, startMove, endMove);
        }

        // Check if the king is in check, or checkmate.
        // Update the PGN notation if necessary and the return result.
        int otherTeam = (pieceMoving.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        if (checkKingInCheck(otherTeam)) {
            if (!castle)
                moveString.append("+");
            if (getPossibleMovesForTeamWithChecking(otherTeam).isEmpty()) {
                if (!castle)
                    moveString.append("+");
                result.isCheckmate = true;
                result.checkmateTeam = otherTeam;
            }
        } else {
            if (getPossibleMovesForTeamWithChecking(otherTeam).isEmpty()) {
                result.isStalemate = true;
                result.checkmateTeam = otherTeam;
            }
        }
        if (handleMoveShouldPromote(pieceMoving, endMove)) {
            String promotedPieceValue = gameLoop.getPromotionPiece();
            moveString.append("=").append(promotedPieceValue);
            int pieceIdFromString = Piece.PIECE_ID_FROM_STRING.get(promotedPieceValue);
            Piece newPiece = Piece.createPieceFromTeam(pieceIdFromString, team);
            board[endMove.row][endMove.column] = newPiece;
        }
        //        if (doNotation)
        handleMoveDoNotation(moveString);

        // Reset the array of algebraic notations as we need to do this
        // because if the player undos a move, and then makes a different move
        // we need to delete the future redos as they lose those moves
        // as they are not valid anymore.
        if (overrideNotation) {
            handleMoveOverrideNotation(moveString);
        }
        result.wasSuccessful = true;
        currentMoveLocation = new BoardLocation(endMove.column, endMove.row);
        lastMoveLocation = new BoardLocation(startMove.column, startMove.row);
        lastMoveResult = result;
        if (updateGUI)
            gameLoop.sendUpdateBoardState();
        return result;
    }

    /**
     * Handles the notation updating of the current move.
     *
     * @param moveString The chess notation of the current move.
     */
    private void handleMoveDoNotation(StringBuilder moveString) {
        if (firstMove) {
            firstMove = false;
            algebraicRepresentation.append(moveCount).append(". ").append(moveString.toString());
            gameLoop.updateChessNotationLabel(moveCount + ". " + moveString.toString());
        } else {
            moveCount++;
            firstMove = true;
            algebraicRepresentation.append(" ").append(moveString.toString()).append("\n");
            gameLoop.updateChessNotationLabel(" " + moveString.toString() + "\n");
        }
    }

    /**
     * Handles the move part to create the basic chess notation.
     *
     * @param pieceMoving              The piece moving.
     * @param startMove                The start location.
     * @param endMove                  The end location.
     * @param piecesMoveToSameLocation How many pieces move to the same location.
     * @param moveString               The current move notation to modify.
     */
    private void handleMoveCreateBasicNotation(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
            int piecesMoveToSameLocation, StringBuilder moveString) {
        // If only found one, just do basic chess notation
        if (piecesMoveToSameLocation == 1) {
            handleMoveNotationSinglePiece(pieceMoving, startMove, endMove, moveString);
        } else if (piecesMoveToSameLocation >= 2) {
            handleMoveNotationMultiplePieces(pieceMoving, startMove, endMove, moveString);
        }
    }

    /**
     * Handles the move part to override the chess notation
     * when a move is applied.
     *
     * @param moveString The PGN notation of the current move.
     */
    private void handleMoveOverrideNotation(StringBuilder moveString) {
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < algebraicNotationMovesList.size() - undoMoveCount; i++) {
            newList.add(algebraicNotationMovesList.get(i));
        }
        newList.add(moveString.toString());
        algebraicNotationMovesList.clear();
        algebraicNotationMovesList.addAll(newList);
        undoMoveCount = 0;
    }

    /**
     * Handles the move part to check
     * if promotion should occur.
     *
     * @param pieceMoving The piece moving.
     * @param endMove     The end move location.
     * @return True if promotion should occur.
     */
    private static boolean handleMoveShouldPromote(Piece pieceMoving, BoardLocation endMove) {
        return pieceMoving instanceof Pawn && (endMove.row == 0 || endMove.row == 7);
    }

    /**
     * Handle the move normally by just doing the move.
     *
     * @param pieceMoving The piece moving.
     * @param startMove   The start location.
     * @param endMove     The end location.
     */
    private void handleMoveNormal(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
        board[endMove.row][endMove.column] = pieceMoving;
        board[endMove.row][endMove.column].moved();
        board[startMove.row][startMove.column] = new EmptyPiece();
    }

    /**
     * Handles the move part to actually apply the move
     * to the board and change the pieces for the king
     * and rook.
     *
     * @param pieceMoving The piece that is moving.
     * @param startMove   The start location.
     * @param endMove     The end location.
     */
    private void handleMoveDoCastlingShortSide(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
        Rook rookCopy = (Rook) board[endMove.row][endMove.column]
                .copy(board[endMove.row][endMove.column].getTeam());
        board[startMove.row][startMove.column + 2] = pieceMoving;
        board[startMove.row][startMove.column + 2].moved();
        board[startMove.row][startMove.column + 1] = rookCopy;
        board[startMove.row][startMove.column + 1].moved();

        board[endMove.row][endMove.column] = new EmptyPiece();
        board[startMove.row][startMove.column] = new EmptyPiece();
    }

    /**
     * Handles the move part to actually apply the move
     * to the board and change the pieces for the king
     * and rook.
     *
     * @param pieceMoving The piece that is moving.
     * @param startMove   The start location.
     * @param endMove     The end location.
     */
    private void handleMoveDoCastlingLongSide(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
        Rook rookCopy = (Rook) board[endMove.row][endMove.column]
                .copy(board[endMove.row][endMove.column].getTeam());
        board[startMove.row][startMove.column - 2] = pieceMoving;
        board[startMove.row][startMove.column - 2].moved();
        board[startMove.row][startMove.column - 1] = rookCopy;
        board[startMove.row][startMove.column - 1].moved();

        board[endMove.row][endMove.column] = new EmptyPiece();
        board[startMove.row][startMove.column] = new EmptyPiece();
    }

    /**
     * Handles the move part to check if
     * the move is long side castling O-O-O, or
     * short side castling.
     *
     * @param startMove The start move location.
     * @param endMove   The end move location.
     * @return True if castling long side.
     */
    private static boolean handleMoveCheckCastlingLongSide(BoardLocation startMove, BoardLocation endMove) {
        return Math.abs(startMove.column - endMove.column) == 4;
    }

    /**
     * Handles the move part to check if castling is requested.
     *
     * @param startMove The start location of the move.
     * @param endMove   The end location of the move.
     * @return True if castling is requested and valid.
     */
    private boolean handleMoveCheckIsCastling(BoardLocation startMove, BoardLocation endMove) {
        return (board[startMove.row][startMove.column] instanceof King
                && board[endMove.row][endMove.column] instanceof Rook)
                && board[startMove.row][startMove.column].getTeam() == board[endMove.row][endMove.column].getTeam();
    }

    /**
     * Handles the move part with multiple pieces for the
     * chess notation.
     *
     * @param pieceMoving The piece moving.
     * @param startMove   The start move location.
     * @param endMove     The end move location.
     * @param moveString  The string to modify the notation.
     */
    private void handleMoveNotationMultiplePieces(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
            StringBuilder moveString) {
        // Check if it should put row, or column
        boolean putRow = handleMoveNotationShouldHaveRow(pieceMoving, startMove, endMove);
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
                moveString.append(BOARD_LOCATIONS[startMove.row][startMove.column].charAt(0));
            moveString.append("x");
        }
        moveString.append(BOARD_LOCATIONS[endMove.row][endMove.column]);
    }

    /**
     * Checks if the notation should put the row or column
     * if more than one piece can move to a location.
     *
     * @param pieceMoving The piece moving.
     * @param startMove   The start move.
     * @return True if the row should be put in the notation.
     */
    private boolean handleMoveNotationShouldHaveRow(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
        boolean putRow = false;
        //        ArrayList<BoardLocation> pieceLocations = getBoardLocationsForTeamForPiece(pieceMoving.getTeam(), pieceMoving.getPieceID());
        //        for (BoardLocation location : pieceLocations) {
        //            if (location.column == startMove.column && location.row == startMove.row)
        //                continue;
        //            if (location.column == startMove.column)
        //                putRow = true;
        //        }
        ArrayList<BoardLocation> pieceLocations = getBoardLocationsForTeamForPieceToTargetLocation(
                pieceMoving.getTeam(), pieceMoving.getPieceID(), endMove);
        for (BoardLocation location : pieceLocations) {
            if (location.column == startMove.column && location.row == startMove.row)
                continue;
            if (location.column == startMove.column)
                putRow = true;
        }
        return putRow;
    }

    /**
     * Handles the move part to create the notation for a single piece moving.
     *
     * @param pieceMoving The piece moving.
     * @param startMove   The start location.
     * @param endMove     The end location.
     * @param moveString  The current move notation to modify.
     */
    private void handleMoveNotationSinglePiece(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
            StringBuilder moveString) {
        if (!(pieceMoving instanceof Pawn)) {
            moveString.append(Piece.chessNotationValue.get(pieceMoving.getPieceID()));
        }
        if (!(board[endMove.row][endMove.column] instanceof EmptyPiece)) {
            if (pieceMoving instanceof Pawn)
                moveString.append(BOARD_LOCATIONS[startMove.row][startMove.column].charAt(0));
            moveString.append("x");
        }
        moveString.append(BOARD_LOCATIONS[endMove.row][endMove.column]);
    }

    /**
     * Gets the number of pieces that can move to the same location.
     *
     * @param pieceMoving The piece that is moving.
     * @param endMove     The location of the target move location.
     * @param team        The team that is associated with the piece.
     * @return The number of pieces on that team of a type that can move to the same location.
     */
    private int getPiecesMoveToSameLocation(Piece pieceMoving, BoardLocation endMove, int team) {
        int piecesMoveToSameLocation = 0;
        // Get all pieces of type that can move to the "endMove" location
        for (BoardLocation location : getPossibleMovesForTeamFromPiece(team, pieceMoving.getPieceID(), true)) {
            if (location.row == endMove.row && location.column == endMove.column) {
                piecesMoveToSameLocation++;
            }
        }
        return piecesMoveToSameLocation;
    }

    /**
     * Promotes a pawn and updates the PGN notation.
     *
     * @param location The location to promote.
     * @param piece    The piece to promote too.
     */
    public void promotePawn(BoardLocation location, Piece piece) {
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
        if (handleMoveCheckIsCastling(startMove, endMove)) {
            if (handleMoveCheckCastlingLongSide(startMove, endMove)) {
                Rook rookCopy = (Rook) board[endMove.row][endMove.column]
                        .copy(board[endMove.row][endMove.column].getTeam());
                board[startMove.row][startMove.column - 2] = pieceMoving;
                board[startMove.row][startMove.column - 2].moved();
                board[startMove.row][startMove.column - 1] = rookCopy;
                board[startMove.row][startMove.column - 1].moved();

                board[endMove.row][endMove.column] = new EmptyPiece();
                board[startMove.row][startMove.column] = new EmptyPiece();
            } else {
                Rook rookCopy = (Rook) board[endMove.row][endMove.column]
                        .copy(board[endMove.row][endMove.column].getTeam());
                board[startMove.row][startMove.column + 2] = pieceMoving;
                board[startMove.row][startMove.column + 2].moved();
                board[startMove.row][startMove.column + 1] = rookCopy;
                board[startMove.row][startMove.column + 1].moved();

                board[endMove.row][endMove.column] = new EmptyPiece();
                board[startMove.row][startMove.column] = new EmptyPiece();
            }
        } else {
            board[endMove.row][endMove.column] = pieceMoving;
            board[endMove.row][endMove.column].moved();
            board[startMove.row][startMove.column] = new EmptyPiece();
        }
        int otherTeam = (pieceMoving.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        checkKingInCheck(board, otherTeam);
        checkKingInCheck(board, pieceMoving.getTeam());
    }

    /**
     * This checks if the king is in check for a board that is passed in as a parameter.
     * It takes the piece that is "moving" and checking any piece from the other team
     * results in a team being in check.
     *
     * @param team The team to check for their king being in check.
     * @return True if the king is in check, false otherwise.
     */
    private boolean checkKingInCheck(int team) {
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
     * @param board The board to check if the king is in check.
     * @param team  The team to check for their king being in check.
     * @return True if the king is in check, false otherwise.
     */
    private boolean checkKingInCheck(Piece[][] board, int team) {
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
     * @param team The team to get all possible moves for.
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

    public ArrayList<BoardLocation> getBoardLocationsForTeamForPieceToTargetLocationForColumn(int team, int pieceId,
            BoardLocation targetLocation,
            int column) {
        ArrayList<BoardLocation> locations = new ArrayList<>();
        for (BoardLocation location : getBoardLocationsForTeamForPieceToTargetLocation(board, team, pieceId,
                targetLocation)) {
            if (location.column == column)
                locations.add(new BoardLocation(location.column, location.row));
        }
        return locations;
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
