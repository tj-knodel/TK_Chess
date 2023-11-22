package edu.kingsu.SoftwareEngineering.Chess;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

import org.junit.Assert;

import edu.kingsu.SoftwareEngineering.Chess.Board.*;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;

public class BoardTest {
    private Board testBoard;
    private Piece[][] startState;
    private Piece[][] kingPawnStart;
    @Before
    public void setup() {
        testBoard = new Board(null);
        startState = new Piece[][]{
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

        kingPawnStart = new Piece[][]{
            {new Rook(0), new Knight(0), new Bishop(0), new Queen(0), new King(0), new Bishop(0), new Knight(0),
                    new Rook(0)},
            {new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0), new Pawn(0),
                    new Pawn(0)},
            {new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                    new EmptyPiece(), new EmptyPiece(), new EmptyPiece()},
            {new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                    new EmptyPiece(), new EmptyPiece(), new EmptyPiece()},
            {new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new Pawn(1),
                    new EmptyPiece(), new EmptyPiece(), new EmptyPiece()},
            {new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(), new EmptyPiece(),
                    new EmptyPiece(), new EmptyPiece(), new EmptyPiece()},
            {new Pawn(1), new Pawn(1), new Pawn(1), new Pawn(1), new EmptyPiece(), new Pawn(1), new Pawn(1),
                    new Pawn(1)},
            {new Rook(1), new Knight(1), new Bishop(1), new Queen(1), new King(1), new Bishop(1), new Knight(1),
                    new Rook(1)}
        };
    }

    /**
     * Tests if the board iss initialized to the correct start state
     */
    @Test
    public void testBoardStartState() {
        Piece[][] boardPieces = testBoard.getBoard();
        for (int i = 0; i < boardPieces.length; i++) {
            for (int j = 0; j < boardPieces[i].length; j++) {
                if (!((boardPieces[i][j].getPieceID() == startState[i][j].getPieceID()) &&
                     boardPieces[i][j].getTeam() == startState[i][j].getTeam())) {
                        // return early since the pieces are not equal
                        Assert.assertTrue(false);
                     }
            }
        }
        // if we've made it this far, then the two states are equal
        Assert.assertTrue(true);
    }

    @Test
    public void testBoardDeepCopy() {
        Board copy = testBoard.copy();
        Piece[][] boardPieces = copy.getBoard();
        for (int i = 0; i < boardPieces.length; i++) {
            for (int j = 0; j < boardPieces[i].length; j++) {
                if (!((boardPieces[i][j].getPieceID() == startState[i][j].getPieceID()) &&
                     boardPieces[i][j].getTeam() == startState[i][j].getTeam())) {
                        // return early since the pieces are not equal
                        Assert.assertTrue(false);
                     }
            }
        }
        // if we've made it this far, then the two states are equal
        Assert.assertTrue(true);
    }

//    /**
//     * Tests if the white start move of e4 will correctly be applied
//     */
//     @Test
//     public void testBoardKingStart() {
//         //testBoard.applyMove(new Pawn(1), null, null)
//         Piece[][] boardPieces = testBoard.getBoard();
//         for (int i = 0; i < boardPieces.length; i++) {
//             for (int j = 0; j < boardPieces[i].length; j++) {
//                 if (!((boardPieces[i][j].getPieceID() == kingPawnStart[i][j].getPieceID()) &&
//                      boardPieces[i][j].getTeam() == kingPawnStart[i][j].getTeam())) {
//                         // return early since the pieces are not equal
//                         Assert.assertTrue(false);
//                      }
//             }
//         }
//         Assert.assertTrue(true);
//     }

    @Test
    public void testWhitePieceCount() {
        Piece[][] pieces = testBoard.getBoard();
        ArrayList<BoardLocation> team_pieces = new ArrayList<BoardLocation>();
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j].getTeam() == Team.WHITE_TEAM) {
                    team_pieces.add(new BoardLocation(i, j));
                }
            }
        }
        Assert.assertEquals(16, team_pieces.size());
    }

//    @Test
//    public void testGetPossibleMovesForWhite() {
//        ArrayList<BoardLocation> possibleMoves = testBoard.getPossibleMovesForTeam(1);
//        Assert.assertTrue(possibleMoves.size() > 0);
//    }

    @Test
    public void testGetPieceLocation() {
        int team = Team.WHITE_TEAM;
        //ArrayList<BoardLocation> kingLocationList = testBoard.getBoardLocationsForTeamForPiece(team, Piece.KING);
        //BoardLocation kingLocation = kingLocationList.getFirst();
        BoardLocation kingLocation = testBoard.getBoardLocationsForTeamForPiece(startState, team, Piece.KING).get(0);
        //Assert.assertTrue(kingLocationList.size() > 0);
        Assert.assertTrue(kingLocation.column == 4 && kingLocation.row == 7);
    }

    @Test
    public void testGetPossibleMovesForPiece() {
        ArrayList<BoardLocation> possibleMoves = testBoard.getPossibleMoves(startState, startState[6][4], new BoardLocation(4,6), false);
        Assert.assertEquals(2, possibleMoves.size());
    }

    @Test
    public void testBoardKingStart() {
        BoardLocation start_loc = new BoardLocation(4, 6);
        BoardLocation end_loc = new BoardLocation(4, 4);
        Piece piece = testBoard.getBoard()[6][4];
        testBoard.applyMove(piece, start_loc, end_loc);
        Piece[][] boardPieces = testBoard.getBoard();
        for (int i = 0; i < boardPieces.length; i++) {
            for (int j = 0; j < boardPieces[i].length; j++) {
                if (!((boardPieces[i][j].getPieceID() == kingPawnStart[i][j].getPieceID()) &&
                     boardPieces[i][j].getTeam() == kingPawnStart[i][j].getTeam())) {
                        // return early since the pieces are not equal
                        Assert.assertTrue(false);
                     }
            }
        }
        Assert.assertTrue(true);
    }

    /**
     * Tests if the board accepts E4 as a valid move
     */
    @Test
    public void testE4PawnMove() {
        BoardLocation start_loc = new BoardLocation(4, 6);
        BoardLocation end_loc = new BoardLocation(4, 4);
        Piece piece = testBoard.getBoard()[6][4];
        Assert.assertTrue(testBoard.applyMove(piece, start_loc, end_loc).isSuccessful);
    }
}
