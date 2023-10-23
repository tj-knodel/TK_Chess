package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;

/**
 * The class that acts as the middle man between the Board class and the GUI functionality.
 * @author Daniell Buchner
 * @version 0.1
 */
public class GameLoop {

    /**
     * Some temporary code to test the .drawBoard function correctly calling UI to update.
     */
    public GameLoop() {
        GUIStarter guiStarter = new GUIStarter();
        Board board = new Board();
        guiStarter.chessUIManager.drawBoard(board.getBoard());

    }
}