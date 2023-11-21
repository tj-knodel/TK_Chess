package edu.kingsu.SoftwareEngineering.Chess.Board;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessUIManager;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNMove;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNReader;

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

    private PGNHelper pgnHelper;

    /**
     * The Board constructor.
     * For now just creates the board and initializes with two player game.
     */
    public Board(GameLoop gameLoop) {
        // board = new Piece[8][8];
        algebraicRepresentation = new StringBuilder();
        this.pgnHelper = new PGNHelper(this);
        algebraicNotationMovesList = new ArrayList<>();
        undoMoveCount = 0;
        firstMove = true;
        moveCount = 1;
        isPaused = false;
        this.gameLoop = gameLoop;
        initializeBoard();
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

    public Piece[][] cloneBoard2DArray(Piece[][] board) {
        Piece[][] r = new Piece[board.length][board[0].length];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                r[i][j] = board[i][j].copy(board[i][j].getTeam());
            }
        }
        return r;
    }

    /**
     * Initializes the board to play a game and not to
     * read the PGN game.
     */
    public void initializeBoard() {
        board = new Piece[][]{
                {new Rook(0), new Knight(0), new Bishop(0), new Queen(0), new King(0), new Bishop(0), new Knight(0),
                        new Rook(0)},
                {new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0),
                        new Pawn(0)},
                {new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                        new EmptyPiece(), new EmptyPiece(), new EmptyPiece()},
                {new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                        new EmptyPiece(), new EmptyPiece(), new EmptyPiece()},
                {new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                        new EmptyPiece(), new EmptyPiece(), new EmptyPiece()},
                {new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                        new EmptyPiece(), new EmptyPiece(), new EmptyPiece()},
                {new Pawn(1), new Pawn(1), new Pawn(1), new Pawn(1), new Pawn(1), new Pawn(1), new Pawn(1),
                        new Pawn(1)},
                {new Rook(1), new Knight(1), new Bishop(1), new Queen(1), new King(1), new Bishop(1), new Knight(1),
                        new Rook(1)}
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
        ArrayList<PGNMove> moves = reader.getMovesFromFile(file.getAbsolutePath());
        algebraicNotationMovesList.clear();
        for (PGNMove m : moves) {
            algebraicNotationMovesList.add(m.getMoveString());
        }
        undoMove();
        redoMove();
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

    /**
     * Undos a move on the board.
     *
     * @return True if successful, false if otherwise.
     */
    public boolean undoMove() {
        if (algebraicNotationMovesList.size() - undoMoveCount <= 0) {
            System.out.println("CAN'T");
            return false;
        }
        return undoOrRedoMove(true);
    }

    /**
     * Redos a move on the board.
     *
     * @return True if successful, false if otherwise.
     */
    public boolean redoMove() {
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
        ArrayList<String> movesList = new ArrayList<>();
        movesList.addAll(algebraicNotationMovesList);
        for (int i = 0; i < movesList.size() - undoMoveCount; i++) {
            // TODO: Use PGNHelper
//            MoveResult result = applyMoveAlgebraicNotation(movesList.get(i), false, true, false);
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
        String moveNotation = pgnHelper.getPGNNotationFromMove(startMove, endMove);
        MoveResult result = new MoveResult();
        if (getIsPaused()) {
            return result;
        }
        int team = pieceMoving.getTeam();
        if (team != getTeamTurn()) {
            return result;
        }

        int piecesMoveToSameLocation = getNumberOfPiecesMovingToSameLocation(board, pieceMoving, endMove, team);
        if (piecesMoveToSameLocation == 0) {
            return result;
        }

        StringBuilder moveString = new StringBuilder();
//        handleMoveCreateBasicNotation(pieceMoving, startMove, endMove, piecesMoveToSameLocation, moveString);

        // Checks if we can castle, apply the correct one and set the chess notation correctly.
        boolean castle = false;
        if (getIsMoveCastling(startMove, endMove)) {
            castle = true;
            moveString = new StringBuilder();
            if (getIsMoveCastlingLongSide(startMove, endMove)) {
                applyCastlingLongSide(board, pieceMoving, startMove, endMove);
                moveString.append("O-O-O");
            } else {
                applyCastlingShortSide(board, pieceMoving, startMove, endMove);
                moveString.append("O-O");
            }
        } else {
            applyMoveNormal(board, pieceMoving, startMove, endMove);
        }

        // Check if the king is in check, or checkmate.
        // Update the PGN notation if necessary and the return result.
        int otherTeam = (pieceMoving.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        if (checkKingInCheck(board, otherTeam)) {
            if (!castle) {
                moveString.append("+");
            }
            if (getPossibleMovesForTeamWithCheckKingInCheck(board, otherTeam, false).isEmpty()) {
                if (!castle) {
                    moveString.append("+");
                }
                result.isCheckmate = true;
                result.checkmateTeam = otherTeam;
            }
        } else {
            if (getPossibleMovesForTeamWithCheckKingInCheck(board, otherTeam, false).isEmpty()) {
                result.isStalemate = true;
                result.checkmateTeam = otherTeam;
            }
        }
        if (getCanPieceBePromoted(pieceMoving, endMove)) {
            String promotedPieceValue = "";
            if (!updateGUI) {
                if (algebraicNotationMovesList.get(algebraicNotationMovesList.size() - undoMoveCount - 1).contains("=")) {
                    promotedPieceValue = algebraicNotationMovesList.get(algebraicNotationMovesList.size() - undoMoveCount - 1).split("=")[1];
                }
                else {
                    promotedPieceValue = gameLoop.getPromotionPiece();
                }
            } else {
                promotedPieceValue = gameLoop.getPromotionPiece();
            }
            moveString.append("=").append(promotedPieceValue);
            int pieceIdFromString = Piece.PIECE_ID_FROM_STRING.get(promotedPieceValue);
            Piece newPiece = Piece.createPieceFromTeam(pieceIdFromString, team);
            board[endMove.row][endMove.column] = newPiece;
        }

        handleMoveDoNotation(moveString);

        if (overrideNotation) {
            handleMoveOverrideNotation(moveString);
        }

        result.wasSuccessful = true;
        currentMoveLocation = new BoardLocation(endMove.column, endMove.row);
        lastMoveLocation = new BoardLocation(startMove.column, startMove.row);
        lastMoveResult = result;
        gameLoop.sendUpdateBoardState(updateGUI);
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
    private static boolean getCanPieceBePromoted(Piece pieceMoving, BoardLocation endMove) {
        return pieceMoving instanceof Pawn && (endMove.row == 0 || endMove.row == 7);
    }

    /**
     * Handle the move normally by just doing the move.
     *
     * @param pieceMoving The piece moving.
     * @param startMove   The start location.
     * @param endMove     The end location.
     */
    private void applyMoveNormal(Piece[][] board, Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
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
    private void applyCastlingShortSide(Piece[][] board, Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
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
    private void applyCastlingLongSide(Piece[][] board, Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
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
    public boolean getIsMoveCastlingLongSide(BoardLocation startMove, BoardLocation endMove) {
        return Math.abs(startMove.column - endMove.column) == 4;
    }

    /**
     * Handles the move part to check if castling is requested.
     *
     * @param startMove The start location of the move.
     * @param endMove   The end location of the move.
     * @return True if castling is requested and valid.
     */
    public boolean getIsMoveCastling(BoardLocation startMove, BoardLocation endMove) {
        return (board[startMove.row][startMove.column] instanceof King
                && board[endMove.row][endMove.column] instanceof Rook)
                && board[startMove.row][startMove.column].getTeam() == board[endMove.row][endMove.column].getTeam();
    }

    /**
     * Gets the number of pieces that can move to the same location.
     *
     * @param pieceMoving The piece that is moving.
     * @param endMove     The location of the target move location.
     * @param team        The team that is associated with the piece.
     * @return The number of pieces on that team of a type that can move to the same location.
     */
    public int getNumberOfPiecesMovingToSameLocation(Piece[][] board, Piece pieceMoving, BoardLocation endMove, int team) {
        int piecesMoveToSameLocation = 0;
        // Get all pieces of type that can move to the "endMove" location
        for (BoardLocation location : getPossibleMovesForTeamForPiece(board, team, pieceMoving.getPieceID(), true)) {
            if (location.isEqual(endMove)) {
                piecesMoveToSameLocation++;
            }
        }
        return piecesMoveToSameLocation;
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
        if (getIsMoveCastling(startMove, endMove)) {
            if (getIsMoveCastlingLongSide(startMove, endMove)) {
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
     * @param board The board to check if the king is in check.
     * @param team  The team to check for their king being in check.
     * @return True if the king is in check, false otherwise.
     */
    private boolean checkKingInCheck(Piece[][] board, int team) {
        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(board, team, Piece.KING).get(0);
        King kingPieceOtherTeam = (King) board[kingLocation.row][kingLocation.column];
        kingPieceOtherTeam.inCheck = false;
        int otherTeam = (team == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        for (BoardLocation teamPossibleMoves : getPossibleMovesForTeamWithoutCheckKingInCheck(board, otherTeam, false)) {
            if (teamPossibleMoves.row == kingLocation.row && teamPossibleMoves.column == kingLocation.column) {
                kingPieceOtherTeam.inCheck = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the possible moves by calling the piece's getPossibleMoves function.
     *
     * @param board The board to get the possible moves on.
     * @param piece The piece to get the possible moves from.
     * @param location The starting location.
     * @param extraCheck If the piece's extraCheck should be on.
     * @return An ArrayList of BoardLocations for all the possible moves a piece can move to.
     */
    private ArrayList<BoardLocation> getPossibleMovesForPiece(Piece[][] board, Piece piece, BoardLocation location, boolean extraCheck) {
        return piece.getPossibleMoves(this, board, location, extraCheck);
    }

    /**
     * Returns all possible moves for the current piece that will
     * not allow the king to be in check.
     *
     * @param board    The board to check for the locations on.
     * @param piece    The piece to get the possible moves for.
     * @param location The location that the piece is at.
     * @return An ArrayList of BoardLocations of where a piece can move to.
     */
    public ArrayList<BoardLocation> getPossibleMoves(Piece[][] board, Piece piece, BoardLocation location,
                                                     boolean extraCheck) {
        int team = piece.getTeam();
        Piece[][] boardCopy = cloneBoard2DArray(board);
        ArrayList<BoardLocation> returnVal = new ArrayList<>();
        ArrayList<BoardLocation> pieceMoves = getPossibleMovesForPiece(board, piece, location, extraCheck);
        for (BoardLocation move : pieceMoves) {
            simulateApplyMove(boardCopy, boardCopy[location.row][location.column], location, move);
            BoardLocation kingLocation = getBoardLocationsForTeamForPiece(boardCopy, team, Piece.KING).get(0);
            King kingPiece = (King) boardCopy[kingLocation.row][kingLocation.column];
            if (kingPiece.inCheck) {
            } else {
                returnVal.add(move);
            }
            boardCopy = cloneBoard2DArray(board);
        }
        return returnVal;
    }

    /**
     * Gets all the possible moves for a specific team, ignoring
     * if the king is in check afterward.
     *
     * @param board The board to check possible moves for the team.
     * @param team  The team to get all possible moves for.
     * @return Arraylist of BoardLocations for the possible moves a team can make.
     */
    public ArrayList<BoardLocation> getPossibleMovesForTeamWithoutCheckKingInCheck(Piece[][] board, int team, boolean extraCheck) {
        ArrayList<BoardLocation> possibleMoves = new ArrayList<>();
        for(BoardLocation loc : getBoardLocationsForTeam(board, team)) {
            for(BoardLocation possibleLoc : board[loc.row][loc.column].getPossibleMoves(this, board, loc, extraCheck)) {
                possibleMoves.add(possibleLoc);
            }
        }
        return possibleMoves;
    }

    /**
     * Gets all the possible moves for a specific team if
     * the king will not be in check afterward.
     *
     * @param board The board to check possible moves for the team.
     * @param team  The team to get all possible moves for.
     * @return Arraylist of BoardLocations for the possible moves a team can make.
     */
    public ArrayList<BoardLocation> getPossibleMovesForTeamWithCheckKingInCheck(Piece[][] board, int team, boolean extraCheck) {
        ArrayList<BoardLocation> possibleMoves = new ArrayList<>();
        for(BoardLocation loc : getBoardLocationsForTeam(board, team)) {
            for(BoardLocation possibleLoc : getPossibleMoves(board, board[loc.row][loc.column], loc, extraCheck)) {
                possibleMoves.add(possibleLoc);
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
    public ArrayList<BoardLocation> getPossibleMovesForTeamForPiece(Piece[][] board, int team, int pieceId, boolean extraCheck) {
        ArrayList<BoardLocation> possibleMoves = new ArrayList<>();
        for(BoardLocation loc : getBoardLocationsForTeamForPiece(board, team, pieceId)) {
            for(BoardLocation possibleMove : getPossibleMoves(board, board[loc.row][loc.column], loc, extraCheck)) {
                possibleMoves.add(possibleMove);
            }
        }
        return possibleMoves;
    }

    public ArrayList<BoardLocation> getPossibleMovesForTeam(Piece[][] board, int team) {
        ArrayList<BoardLocation> possibleMoves = new ArrayList<>();
        for(BoardLocation loc : getBoardLocationsForTeam(board, team)) {
            for(BoardLocation possibleMove : getPossibleMoves(board, board[loc.row][loc.column], loc, false)) {
                possibleMoves.add(possibleMove);
            }
        }
        return possibleMoves;
    }

    /**
     * Gets all the board locations a specific team's pieces are at.
     *
     * @param board The board 2d-array to check.
     * @param team The team to find the pieces for.
     * @return An ArrayList of BoardLocations of the pieces to check.
     */
    public ArrayList<BoardLocation> getBoardLocationsForTeam(Piece[][] board, int team) {
        ArrayList<BoardLocation> locations = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getTeam() == team) {
                    locations.add(new BoardLocation(j, i));
                }
            }
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
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPiece(Piece[][] board, int team, int pieceId) {
        ArrayList<BoardLocation> locations = new ArrayList<>();
        for(BoardLocation loc : getBoardLocationsForTeam(board, team)) {
            if(board[loc.row][loc.column].getPieceID() == pieceId) {
                locations.add(loc);
            }
        }
        return locations;
    }

    /**
     * Gets the locations of pieces for a team on the board that
     * can move to a target location where the piece is on
     * a specific column.
     *
     * @param board   The board to get the pieces locations for.
     * @param team    The team to get the moves for.
     * @param pieceId The piece to get locations for.
     * @return ArrayList of BoardLocations of all locations of the pieces for the team.
     */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPieceToTargetLocationForColumn(Piece[][] board, int team, int pieceId,
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
     * Gets the locations of pieces for a team on the board that
     * can move to a target location where the piece is on
     * a specific column.
     *
     * @param board   The board to get the pieces locations for.
     * @param team    The team to get the moves for.
     * @param pieceId The piece to get locations for.
     * @return ArrayList of BoardLocations of all locations of the pieces for the team.
     */
    public ArrayList<BoardLocation> getBoardLocationsForTeamForPieceToTargetLocationForRow(Piece[][] board, int team, int pieceId,
                                                                                              BoardLocation targetLocation,
                                                                                              int row) {
        ArrayList<BoardLocation> locations = new ArrayList<>();
        for (BoardLocation location : getBoardLocationsForTeamForPieceToTargetLocation(board, team, pieceId,
                targetLocation)) {
            if (location.row == row)
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
        for (BoardLocation loc : getBoardLocationsForTeamForPiece(board, team, pieceId)) {
            for (BoardLocation targetLoc : getPossibleMoves(board, board[loc.row][loc.column], loc, false)) {
                if (targetLoc.isEqual(targetLocation)) {
                    locations.add(loc);
                }
            }
        }
        return locations;
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
        for (BoardLocation loc : getBoardLocationsForTeamForPiece(board, team, pieceId)) {
            if (loc.column == column) {
                locations.add(loc);
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
        for (BoardLocation loc : getBoardLocationsForTeamForPiece(board, team, pieceId)) {
            if (loc.row == row) {
                locations.add(loc);
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
