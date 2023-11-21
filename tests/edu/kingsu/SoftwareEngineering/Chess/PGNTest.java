package edu.kingsu.SoftwareEngineering.Chess;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.PGNHelper;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PGNTest {

    private Board board;
    private PGNHelper pgnHelper;

    @Before
    public void setup() {
        this.board = new Board(null);
        this.pgnHelper = new PGNHelper(board);

    }

    @Test
    public void testA4Notation() {
        Assert.assertEquals("a4 move notation not correct!", "a4", pgnHelper.getPGNNotationFromMove(new BoardLocation(0, 6), new BoardLocation(0, 4)));
    }

    @Test
    public void testCastleShortSideNotation() {
        Assert.assertEquals("O-O move notation not correct!", "O-O", pgnHelper.getPGNNotationFromMove(new BoardLocation(4, 7), new BoardLocation(7, 7)));
    }

    @Test
    public void testCastleLongSideNotation() {
        Assert.assertEquals("O-O-O move notation not correct!", "O-O-O", pgnHelper.getPGNNotationFromMove(new BoardLocation(4, 7), new BoardLocation(0, 7)));
    }

    @Test
    public void doesNf3GiveCorrectLocations() {
        BoardLocation[] locations = this.pgnHelper.getBoardLocationsFromPGN("Nf3", Team.WHITE_TEAM);
        Assert.assertTrue("Start location must be column 6 row 7", locations[0].isEqual(new BoardLocation(6, 7)));
    }

    @Test
    public void does_e5_GiveCorrectLocations() {
        BoardLocation[] locations = this.pgnHelper.getBoardLocationsFromPGN("e5", Team.BLACK_TEAM);
        Assert.assertTrue("Start location must be column 4 row 1", locations[0].isEqual(new BoardLocation(4, 1)));
    }

    @Test
    public void testKnightTakePawnAtE5() {
        this.board.applyMovePGNNotation("Nf3");
        this.board.applyMovePGNNotation("e5");
        Assert.assertEquals("Nxe5 move notation not correct!", "Nxe5", pgnHelper.getPGNNotationFromMove(new BoardLocation(5, 5), new BoardLocation(4, 3)));
    }

}
