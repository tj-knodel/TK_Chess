package edu.kingsu.SoftwareEngineering.Chess;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import edu.kingsu.SoftwareEngineering.Chess.Board.*;
import edu.kingsu.SoftwareEngineering.Chess.Players.*;

public class PlayerTest {
    Board testBoard;
    AIPlayer testAI;

    /**
     * Sets up the player and the board
     */
    @Before
    public void setupPlayer() {
        testBoard = new Board();
        // initialize the AI with a difficulty of 1.
        testAI = new AIPlayer(1, Team.BLACK_TEAM);
    }

    /**
     * Tests if the AIPlayer returns a move.
     */
    @Test
    public void testMove() {
        // Test to determine if the AIPlayer returns a move
        Assert.assertNotNull(testAI.getMove(testBoard));
    }

    /**
     * Test if the AIPlayer will return a legal move
     */
    @Test
    public void testValidMove() {
        Move test_move = testAI.getMove(testBoard);
        Assert.assertTrue(testBoard.applyMove(test_move.piece, test_move.start, test_move.end).wasSuccessful);
    }
}