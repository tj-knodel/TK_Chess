package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;

/**
 * The class that acts as the middle man between the Board class and the GUI
 * functionality.
 * 
 * @author Daniell Buchner
 * @version 0.1
 */
public class GameLoop {

    private GUIStarter guiStarter;
    private Board board;
    private MoveController moveController;

    /**
     * Some temporary code to test the .drawBoard function correctly calling UI to
     * update.
     */
    public GameLoop() {
        guiStarter = new GUIStarter();
        board = new Board();
        guiStarter.chessUIManager.drawBoard(board.getBoard());
        moveController = new MoveController();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                chessTile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (moveController.chessTileClick(chessTile.row, chessTile.column)) {
                            moveController.sendMovesToBoard(board);
                            guiStarter.chessUIManager.drawBoard(board.getBoard());
                        }
                    }
                });
            }
        }
    }

    public void sendUpdateBoardState() {
        guiStarter.chessUIManager.drawBoard(board.getBoard());
    }
}