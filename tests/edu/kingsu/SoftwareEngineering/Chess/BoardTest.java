package edu.kingsu.SoftwareEngineering.Chess;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;

public class BoardTest {
    Board board;

    @Before
    public void initBoard() {
        board = new Board();
    }

    @Test
    public void testNotPossibleMove() {
        BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(Team.WHITE_TEAM, Piece.KING).get(0);
        Piece kingPiece = board.getBoard()[kingLocation.row][kingLocation.column];
        Assert.assertEquals("King cannot move at start of game!", 0,
                board.getPossibleMoves(kingPiece, kingLocation, false).size());
    }

    @Test
    public void testKingNotInCheckAfterMove() {
        // board.applyMove(board.getBoard()[6][1], new BoardLocation(1, 6), new BoardLocation(1, 5));
        BoardLocation kingLocation = board.getBoardLocationsForTeamForPiece(Team.WHITE_TEAM, Piece.KING).get(0);
        Piece kingPiece = board.getBoard()[kingLocation.row][kingLocation.column];
        King castedPiece = (King) kingPiece;
        Assert.assertFalse("King cannot be in check with 1 move!", castedPiece.inCheck);
    }
}
