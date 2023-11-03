package edu.kingsu.SoftwareEngineering.Chess;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import edu.kingsu.SoftwareEngineering.Chess.Board.*;
import edu.kingsu.SoftwareEngineering.Chess.Players.*;

public class PlayerTest {
    Board testBoard;
    AIPlayer testAI;

    @Before
    public void setupPlayer() {
        testBoard = new Board();
        // initialize the AI with a difficulty of 1.
        testAI = new AIPlayer(1, Team.WHITE_TEAM);
    }

    @Test
    public void testMove() {
        // Test to determine if the AIPlayer returns a move
        Assert.assertNotNull(testAI.getMove());
    }


}