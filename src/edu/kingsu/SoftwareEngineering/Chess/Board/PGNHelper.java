package edu.kingsu.SoftwareEngineering.Chess.Board;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.EmptyPiece;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.King;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Pawn;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class PGNHelper {

    /**
     * The Board locations in the indexing that the Piece[][] board uses.
     * This way we can get the string representation of the location
     * by using the BoardLocation in the applyMove function.
     */
    private static final String[][] BOARD_LOCATIONS = {
            {"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"},
            {"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7"},
            {"a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6"},
            {"a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5"},
            {"a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4"},
            {"a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3"},
            {"a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"},
            {"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"},
    };

    /**
     * The board locations from a string. So "a1" would return a BoardLocation
     * with row = 7 and column = 0
     */
    private static final HashMap<String, BoardLocation> BOARD_LOCATIONS_FROM_STRING;

    private final Board board;

    public PGNHelper(Board board) {
        this.board = board;
    }

    public BoardLocation[] getBoardLocationsFromPGN(String notation, int team) {
        BoardLocation[] locations = new BoardLocation[2];
        notation = notation.replace("+", "");
        // Pawn move
        if(notation.length() == 2)
            return getBoardLocationsForPawnMove(notation, team, locations);
        else if(notation.length() == 4 && Character.isLowerCase(notation.charAt(0)))
            return getBoardLocationsForPawnMoveForColumn(notation, team, locations, notation.charAt(0) - 'a');

        // All other pieces move
        if(notation.length() == 3) {
            return getBoardLocationsForPieceMove(notation, team, locations);
        } else if(notation.length() == 4 && !isNotationCapturing(notation)) {
            BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(2));
            if(Character.isDigit(notation.charAt(1))) {
                return getBoardLocationsForPieceMoveRow(notation, team, locations, (8 - (notation.charAt(1) - '0')), targetLocation);
            } else {
                return getBoardLocationsForPieceMoveColumn(notation, team, locations, notation.charAt(1) - 'a');
            }
        } else if(notation.length() == 4) {
            notation = notation.replace("x", "");
            return getBoardLocationsForPieceMove(notation, team, locations);
        } else if(notation.length() == 5 && isNotationCapturing(notation)) {
            BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(2));
            notation = notation.replace("x", "");
            if(Character.isDigit(notation.charAt(1))) {
                return getBoardLocationsForPieceMoveRow(notation, team, locations, (8 - (notation.charAt(1) - '0')), targetLocation);
            } else {
                return getBoardLocationsForPieceMoveColumn(notation, team, locations, notation.charAt(1) - 'a');
            }
        }
        return locations;
    }

    private BoardLocation[] getBoardLocationsForPieceMoveRow(String notation, int team, BoardLocation[] locations, int row, BoardLocation targetLocation) {
        int pieceId = Piece.PIECE_ID_FROM_STRING.get(String.valueOf(notation.charAt(0)));
        ArrayList<BoardLocation> pieceLoc = board.getBoardLocationsForTeamForPieceToTargetLocationForRow(board.getBoard(), team, pieceId, targetLocation, row);
        if(pieceLoc.size() != 1)
            return locations;
        locations[0] = pieceLoc.get(0);
        locations[1] = targetLocation;
        return locations;
    }

    private BoardLocation[] getBoardLocationsForPieceMoveColumn(String notation, int team, BoardLocation[] locations, int column) {
        BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(2));
        int pieceId = Piece.PIECE_ID_FROM_STRING.get(String.valueOf(notation.charAt(0)));
        ArrayList<BoardLocation> pieceLoc = board.getBoardLocationsForTeamForPieceToTargetLocationForColumn(board.getBoard(), team, pieceId, targetLocation, column);
        if(pieceLoc.size() != 1)
            return locations;
        locations[0] = pieceLoc.get(0);
        locations[1] = targetLocation;
        return locations;
    }

    private BoardLocation[] getBoardLocationsForPieceMove(String notation, int team, BoardLocation[] locations) {
        BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(1));
        int pieceId = Piece.PIECE_ID_FROM_STRING.get(String.valueOf(notation.charAt(0)));
        ArrayList<BoardLocation> pieceLoc = board.getBoardLocationsForTeamForPieceToTargetLocation(board.getBoard(), team, pieceId, targetLocation);
        if(pieceLoc.size() != 1)
            return locations;
        locations[0] = pieceLoc.get(0);
        locations[1] = targetLocation;
        return locations;
    }

    private BoardLocation[] getBoardLocationsForPawnMove(String notation, int team, BoardLocation[] locations) {
        ArrayList<BoardLocation> pawnLoc = board.getBoardLocationsForTeamForPieceToTargetLocation(board.getBoard(), team, Piece.PAWN, BOARD_LOCATIONS_FROM_STRING.get(notation));
        if(pawnLoc.size() != 1)
            return locations;
        locations[0] = pawnLoc.get(0);
        locations[1] = BOARD_LOCATIONS_FROM_STRING.get(notation);
        return locations;
    }

    private BoardLocation[] getBoardLocationsForPawnMoveForColumn(String notation, int team, BoardLocation[] locations, int column) {
        BoardLocation targetLocation = BOARD_LOCATIONS_FROM_STRING.get(notation.substring(2));
        ArrayList<BoardLocation> pawnLoc = board.getBoardLocationsForTeamForPieceToTargetLocation(board.getBoard(), team, Piece.PAWN, targetLocation);
        if(pawnLoc.size() != 1) {
            pawnLoc = board.getBoardLocationsForTeamForPieceToTargetLocationForColumn(board.getBoard(), team, Piece.PAWN, targetLocation, column);
            if(pawnLoc.size() != 1) {
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

    private boolean isNotationCapturing(String notation) {
        return notation.contains("x");
    }

    public String getPGNNotationFromMove(BoardLocation startMove, BoardLocation endMove) {
        Piece[][] boardCopy = board.getBoard();
        Piece piece = boardCopy[startMove.row][startMove.column];
        if(board.getIsMoveCastling(startMove, endMove)) {
            if(board.getIsMoveCastlingLongSide(startMove, endMove))
                return "O-O-O";
            return "O-O";
        }
        if(board.getNumberOfPiecesMovingToSameLocation(boardCopy, piece, endMove, piece.getTeam()) > 1)
            return getPGNNotationFromMoveMultiplePieces(boardCopy, piece, startMove, endMove);
        else
            return getPGNNotationFromMoveSinglePiece(boardCopy, piece, startMove, endMove);
    }

    private String getPGNNotationFromMoveMultiplePieces(Piece[][] boardCopy, Piece piece, BoardLocation startMove, BoardLocation endMove) {
        boolean shouldPutRowInNotation = board.getBoardLocationsForTeamForPieceToTargetLocationForColumn(boardCopy, piece.getTeam(), piece.getPieceID(), endMove, startMove.column).size() > 1;
        String moveString = Piece.CHESS_NOTATION_VALUE.get(piece.getPieceID());
        if(shouldPutRowInNotation)
            moveString += BOARD_LOCATIONS[startMove.row][startMove.column].charAt(1);
        else if(!(boardCopy[startMove.row][startMove.column] instanceof Pawn))
            moveString += BOARD_LOCATIONS[startMove.row][startMove.column].charAt(0);
        moveString = getEndPGNNotationFromMove(boardCopy, piece, startMove, endMove, moveString);
        return moveString;
    }

    private String getEndPGNNotationFromMove(Piece[][] boardCopy, Piece piece, BoardLocation startMove, BoardLocation endMove, String moveString) {
        if(isCapturing(boardCopy, endMove)) {
            if(boardCopy[startMove.row][startMove.column] instanceof Pawn)
                moveString += BOARD_LOCATIONS[startMove.row][startMove.column].charAt(0);
            moveString += "x";
        }
        moveString += BOARD_LOCATIONS[endMove.row][endMove.column];
        if(isPromoting(piece, endMove))
            moveString += "=" + board.getPromotionPiece();
        moveString = getCheckAndCheckmateFromMove(boardCopy, piece, startMove, endMove, moveString);
        return moveString;
    }

    private String getPGNNotationFromMoveSinglePiece(Piece[][] boardCopy, Piece piece, BoardLocation startMove, BoardLocation endMove) {
        String moveString = Piece.CHESS_NOTATION_VALUE.get(piece.getPieceID());
        return getEndPGNNotationFromMove(boardCopy, piece, startMove, endMove, moveString);
    }

    private boolean isPromoting(Piece piece, BoardLocation endMove) {
        return (endMove.row == 0 || endMove.row == 7) && piece instanceof Pawn;
    }

    private boolean isCapturing(Piece[][] board, BoardLocation endMove) {
        return !(board[endMove.row][endMove.column] instanceof EmptyPiece);
    }

    private String getCheckAndCheckmateFromMove(Piece[][] boardCopy, Piece piece, BoardLocation startMove, BoardLocation endMove, String moveString) {
        if(isOtherTeamInCheck(boardCopy, piece, startMove, endMove)) {
            moveString += "+";
            int otherTeam = (piece.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
            if(board.getPossibleMovesForTeamWithCheckKingInCheck(boardCopy, otherTeam, false).isEmpty())
                moveString += "+";
        }
        return moveString;
    }

    private boolean isOtherTeamInCheck(Piece[][] boardCopy, Piece piece, BoardLocation startMove, BoardLocation endMove) {
        board.simulateApplyMove(boardCopy, piece, startMove, endMove);
        int otherTeam = (piece.getTeam() == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(boardCopy, otherTeam, Piece.KING).get(0);
        return ((King)(boardCopy[kingLocation.row][kingLocation.column])).getInCheck();
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
}

//    /**
//     * Applys a move based on the algebraic notation.
//     *
//     * @param notation   The notation to apply a move to. Example, Nf3 moves Knight to f3 if possible.
//     * @param extraCheck If algebraic notation should be overriden. Make it true if not known how it works.
//     * @return True if move was successful.
//     */
//    public MoveResult applyMoveAlgebraicNotation(String notation, boolean extraCheck, boolean doNotation,
//                                                 boolean updateGUI) {
//        MoveResult result = new MoveResult();
//        int team = (firstMove) ? Team.WHITE_TEAM : Team.BLACK_TEAM;
//        // Replace the + sign in the PGN notation as the board doesn't care
//        // what the notation says as it has its own rules.
//        String notationString = notation.replace("+", "");
//        boolean capture = notation.contains("x");
//        notationString = notationString.replace("x", "");
//        if (notationString.equalsIgnoreCase("O-O")) {
//            return applyMoveAlgebraicNotationCastlingShortSide(extraCheck, team, updateGUI);
//        } else if (notationString.equalsIgnoreCase("O-O-O")) {
//            return applyMoveAlgebraicNotationCastlingLongSide(extraCheck, team, updateGUI);
//        } else if (notationString.contains("=")) {
//            return applyMoveAlgebraicNotationPromotion(notationString, team, result, updateGUI);
//        }
//        // Piece moving without pawn.
//        if (notationString.length() == 2) {
//            return applyMoveAlgebraicNotationPawnBasic(extraCheck, doNotation, notationString, team, result, updateGUI);
//        } else if (notationString.length() == 3) {
//            if (Character.isUpperCase(notationString.charAt(0))) {
//                return applyMoveAlgebraicNotationPiece(extraCheck, doNotation, notationString, team, result, updateGUI);
//            } else {
//                if (capture) {
//                }
//                //                System.out.println(notationString);
//                return applyMoveAlgebraicNotationCaptureWithPawn(extraCheck, doNotation, notationString, team,
//                        result, updateGUI);
//                //                return applyMoveAlgebraicNotationPawn(extraCheck, doNotation, notationString, team, result);
//            }
//        } else if (notationString.length() == 4) {
//            if (Character.isUpperCase(notationString.charAt(0))) {
//                return applyMoveAlgebraicNotationCaptureWithPiece(extraCheck, doNotation, notationString, team, result,
//                        updateGUI);
//            } else {
//                return applyMoveAlgebraicNotationCaptureWithPawn(extraCheck, doNotation, notationString, team, result,
//                        updateGUI);
//            }
//        }
//        return result;
//    }
//
//    private MoveResult applyMoveAlgebraicNotationCaptureWithPawn(boolean extraCheck, boolean doNotation,
//                                                                 String notationString, int team, MoveResult result, boolean updateGUI) {
//        //        JOptionPane.showConfirmDialog(null, notationString);
//        int pieceId = Piece.PAWN;//Piece.PIECE_ID_FROM_STRING.get(notationString.substring(1, 2));
//        String locationString = notationString.substring(1);
//        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
//        ArrayList<BoardLocation> pieceLocation;
//        pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(board, team, pieceId,
//                boardLocation);
//        if (pieceLocation.size() != 1) {
//            pieceLocation = getBoardLocationsForTeamForPieceToTargetLocationForColumn(board, team,
//                    pieceId, boardLocation,
//                    (int) notationString.charAt(0) - 'a');
////            System.out.println(notationString + " " + pieceLocation.size());
//        }
//        if (pieceLocation.size() != 1)
//            return result;
//        Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
//        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation, updateGUI);
//    }
//
//    private MoveResult applyMoveAlgebraicNotationCaptureWithPiece(boolean extraCheck, boolean doNotation,
//                                                                  String notationString, int team, MoveResult result, boolean updateGUI) {
//        int pieceId = Piece.PIECE_ID_FROM_STRING.get(notationString.substring(0, 1));
//        String locationString = notationString.substring(2);
//        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
//        // ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(team, pieceId,
//        //         boardLocation);
//        ArrayList<BoardLocation> pieceLocation = new ArrayList<>();
//        if (Character.isDigit(notationString.charAt(1))) {
//            //            JOptionPane.showConfirmDialog(null, 8 - (notationString.charAt(1) - '0'));
//            pieceLocation = getBoardLocationsForTeamForPieceForRow(board, team, pieceId,
//                    8 - (notationString.charAt(1) - '0'));
//        } else {
//            pieceLocation = getBoardLocationsForTeamForPieceToTargetLocationForColumn(board, team, pieceId,
//                    boardLocation, notationString.charAt(1) - 'a');
//            if (pieceLocation.size() != 1)
//                pieceLocation = getBoardLocationsForTeamForPieceForColumn(board, team, pieceId,
//                        notationString.charAt(1) - 'a');
//        }
//        if (pieceLocation.size() != 1)
//            return result;
//        Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
//        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation, updateGUI);
//    }
//
//    private MoveResult applyMoveAlgebraicNotationPawn(boolean extraCheck, boolean doNotation, String notationString,
//                                                      int team, MoveResult result, boolean updateGUI) {
//        int pieceId = Piece.PAWN;
//        String locationString = notationString.substring(1);
//        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
//        ArrayList<BoardLocation> pieceLocationCheck = getBoardLocationsForTeamForPieceToTargetLocation(board, team, pieceId,
//                boardLocation);
//        if (pieceLocationCheck.size() != 1) {
//            ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceForColumn(board, team, pieceId,
//                    notationString.charAt(0) - 'a');
//            if (pieceLocation.size() != 1)
//                return result;
//            Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
//            return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation,
//                    updateGUI);
//        }
//        Piece pieceToMove = getBoard()[pieceLocationCheck.get(0).row][pieceLocationCheck.get(0).column];
//        return applyMoveInternal(pieceToMove, pieceLocationCheck.get(0), boardLocation, extraCheck, doNotation,
//                updateGUI);
//    }
//
//    private MoveResult applyMoveAlgebraicNotationPiece(boolean extraCheck, boolean doNotation, String notationString,
//                                                       int team, MoveResult result, boolean updateGUI) {
//        int pieceId = Piece.PIECE_ID_FROM_STRING.get(notationString.substring(0, 1));
//        String locationString = notationString.substring(1);
//        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
//        ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(board, team, pieceId,
//                boardLocation);
//        if (pieceLocation.size() != 1)
//            return result;
//        Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
//        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation, updateGUI);
//    }
//
//    private MoveResult applyMoveAlgebraicNotationPawnBasic(boolean extraCheck, boolean doNotation,
//                                                           String notationString, int team, MoveResult result, boolean updateGUI) {
//        int pieceId = Piece.PAWN;
//        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(notationString);
//        ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(board, team, pieceId,
//                boardLocation);
//        if (pieceLocation.size() != 1)
//            return result;
//        Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
//        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, extraCheck, doNotation, updateGUI);
//    }
//
//    private MoveResult applyMoveAlgebraicNotationPromotion(String notationString, int team, MoveResult result,
//                                                           boolean updateGUI) {
//        String[] moves = notationString.split("=");
//        int pieceId = Piece.PAWN;
//        String locationString = moves[0].substring(moves[0].length() - 2);
//        BoardLocation boardLocation = BOARD_LOCATIONS_FROM_STRING.get(locationString);
//        ArrayList<BoardLocation> pieceLocation = getBoardLocationsForTeamForPieceToTargetLocation(board, team, pieceId,
//                boardLocation);
//        if (pieceLocation.size() != 1)
//            return result;
//        Piece pieceToMove = getBoard()[pieceLocation.get(0).row][pieceLocation.get(0).column];
//        if (!(boardLocation.row == 0 || boardLocation.row == 7))
//            return result;
//        if (!(pieceToMove instanceof Pawn))
//            return result;
//        return applyMoveInternal(pieceToMove, pieceLocation.get(0), boardLocation, false, true, updateGUI);
//    }
//
//    private MoveResult applyMoveAlgebraicNotationCastlingLongSide(boolean extraCheck, int team, boolean updateGUI) {
//        // Only ever one king
//        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(board, team, Piece.KING).get(0);
//        BoardLocation endLocation = new BoardLocation(kingLocation.column - 4, kingLocation.row);
//        return applyMove(updateGUI, getBoard()[kingLocation.row][kingLocation.column], kingLocation, endLocation,
//                extraCheck);
//    }
//
//    private MoveResult applyMoveAlgebraicNotationCastlingShortSide(boolean extraCheck, int team, boolean updateGUI) {
//        // Only ever one king
//        BoardLocation kingLocation = getBoardLocationsForTeamForPiece(board, team, Piece.KING).get(0);
//        BoardLocation endLocation = new BoardLocation(kingLocation.column + 3, kingLocation.row);
//        return applyMove(updateGUI, getBoard()[kingLocation.row][kingLocation.column], kingLocation, endLocation,
//                extraCheck);
//    }

//    /**
//     * Handles the move part to create the basic chess notation.
//     *
//     * @param pieceMoving              The piece moving.
//     * @param startMove                The start location.
//     * @param endMove                  The end location.
//     * @param piecesMoveToSameLocation How many pieces move to the same location.
//     * @param moveString               The current move notation to modify.
//     */
//    private void handleMoveCreateBasicNotation(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
//                                               int piecesMoveToSameLocation, StringBuilder moveString) {
//        // If only found one, just do basic chess notation
//        if (piecesMoveToSameLocation == 1) {
//            handleMoveNotationSinglePiece(pieceMoving, startMove, endMove, moveString);
//        } else if (piecesMoveToSameLocation >= 2) {
//            handleMoveNotationMultiplePieces(pieceMoving, startMove, endMove, moveString);
//        }
//    }

//    /**
//     * Handles the move part with multiple pieces for the
//     * chess notation.
//     *
//     * @param pieceMoving The piece moving.
//     * @param startMove   The start move location.
//     * @param endMove     The end move location.
//     * @param moveString  The string to modify the notation.
//     */
//    private void handleMoveNotationMultiplePieces(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
//                                                  StringBuilder moveString) {
//        // Check if it should put row, or column
//        boolean putRow = handleMoveNotationShouldHaveRow(pieceMoving, startMove, endMove);
//        // Add the row or column after the piece id
//        String locationIfNeeded = "";
//        if (putRow) {
//            locationIfNeeded = BOARD_LOCATIONS[startMove.row][startMove.column].substring(1, 2);
//        } else {
//            locationIfNeeded = BOARD_LOCATIONS[startMove.row][startMove.column].substring(0, 1);
//        }
//        if (!(pieceMoving instanceof Pawn)) {
//            moveString.append(Piece.chessNotationValue.get(pieceMoving.getPieceID()));
//            moveString.append(locationIfNeeded);
//        }
//        if (!(board[endMove.row][endMove.column] instanceof EmptyPiece)) {
//            if (pieceMoving instanceof Pawn)
//                moveString.append(BOARD_LOCATIONS[startMove.row][startMove.column].charAt(0));
//            moveString.append("x");
//        }
//        moveString.append(BOARD_LOCATIONS[endMove.row][endMove.column]);
//    }

//    /**
//     * Checks if the notation should put the row or column
//     * if more than one piece can move to a location.
//     *
//     * @param pieceMoving The piece moving.
//     * @param startMove   The start move.
//     * @return True if the row should be put in the notation.
//     */
//    private boolean handleMoveNotationShouldHaveRow(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove) {
//        boolean putRow = false;
//        //        ArrayList<BoardLocation> pieceLocations = getBoardLocationsForTeamForPiece(pieceMoving.getTeam(), pieceMoving.getPieceID());
//        //        for (BoardLocation location : pieceLocations) {
//        //            if (location.column == startMove.column && location.row == startMove.row)
//        //                continue;
//        //            if (location.column == startMove.column)
//        //                putRow = true;
//        //        }
//        ArrayList<BoardLocation> pieceLocations = getBoardLocationsForTeamForPieceToTargetLocation(board,
//                pieceMoving.getTeam(), pieceMoving.getPieceID(), endMove);
//        for (BoardLocation location : pieceLocations) {
//            if (location.column == startMove.column && location.row == startMove.row)
//                continue;
//            if (location.column == startMove.column)
//                putRow = true;
//        }
//        return putRow;
//    }

//    /**
//     * Handles the move part to create the notation for a single piece moving.
//     *
//     * @param pieceMoving The piece moving.
//     * @param startMove   The start location.
//     * @param endMove     The end location.
//     * @param moveString  The current move notation to modify.
//     */
//    private void handleMoveNotationSinglePiece(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
//                                               StringBuilder moveString) {
//        if (!(pieceMoving instanceof Pawn)) {
//            moveString.append(Piece.chessNotationValue.get(pieceMoving.getPieceID()));
//        }
//        if (!(board[endMove.row][endMove.column] instanceof EmptyPiece)) {
//            if (pieceMoving instanceof Pawn)
//                moveString.append(BOARD_LOCATIONS[startMove.row][startMove.column].charAt(0));
//            moveString.append("x");
//        }
//        moveString.append(BOARD_LOCATIONS[endMove.row][endMove.column]);
//    }

///**
// * Applies a move based on the PGNMove class.
// *
// * @param move       The move to do.
// * @param extraCheck Should the algebraicNotation be overriden. Keep false unless you know why to make true.
// * @return MoveResult of the moved that is applied.
// */
//    public MoveResult applyPGNMove(PGNMove move, boolean extraCheck) {
//        // if (move.hasComment()) {
//        //     System.out.println("Comment: " + move.getComment() + ", for move: " + move.getMoveString());
//        // }
//        return applyMoveAlgebraicNotation(move.getMoveString(), extraCheck, true, false);
//    }

///**
// * Applies a move based on the algebraic notation.
// * Will override notation.
// *
// * @param notation The notation to apply a move to. Example, Nf3 moves Knight to f3 if possible.
// * @return True if move was successful.
// */
//    public MoveResult applyMoveAlgebraicNotation(String notation) {
//        return applyMoveAlgebraicNotation(notation, true, true, true);
//    }

///**
// * Check if a move can be applied, then do it.
// * Will also generate algebraic notation for the move in here
// * and apply it to the algebraicNotation StringBuilder.
// *
// * @param pieceMoving The chess piece being moved.
// * @param startMove   The starting move of the piece.
// * @param endMove     The target location of the piece.
// * @return True if the move was successful.
// */
//    private MoveResult applyMove(Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
//                                 boolean extraCheck) {
//        return applyMoveInternal(pieceMoving, startMove, endMove, extraCheck, false, true);
//    }

//    private MoveResult applyMove(boolean updateGUI, Piece pieceMoving, BoardLocation startMove, BoardLocation endMove,
//                                 boolean extraCheck) {
//        return applyMoveInternal(pieceMoving, startMove, endMove, extraCheck, false, updateGUI);
//    }