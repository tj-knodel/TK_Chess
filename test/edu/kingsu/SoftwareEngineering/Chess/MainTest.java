package edu.kingsu.SoftwareEngineering.Chess;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import edu.kingsu.SoftwareEngineering.Chess.Main;

public class MainTest  {

  @Before
  public void setupChess() {
    // Add any testing methods here
  }

  @After
  public void tearDownChess() {
    // Clean-up anything initialized in the setup method
  }

  @Test
  public void testMove() {

    // Add your tests here!
    // Be sure to make one xxxxTest class for each file in the Chess Model.

    // Make sure we can create a chess Main object
    assertNotNull(new Main());

  }

}
