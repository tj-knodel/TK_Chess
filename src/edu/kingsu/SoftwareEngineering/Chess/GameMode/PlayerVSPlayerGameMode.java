package edu.kingsu.SoftwareEngineering.Chess.GameMode;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.MoveController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerVSPlayerGameMode extends GameMode {

    private MoveController[] moveControllers;
    private int teamTurn;

    public PlayerVSPlayerGameMode() {
        this.moveControllers = new MoveController[2];
        this.moveControllers[0] = new MoveController();
        this.moveControllers[1] = new MoveController();
        teamTurn = Team.WHITE_TEAM;
    }

    @Override
    public void switchTeam() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'switchTeam'");
    }

    public void setClickListeners(GUIStarter guiStarter, Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                chessTile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (moveControllers[teamTurn].chessTileClick(board, chessTile.row, chessTile.column)) {
                            moveControllers[teamTurn].sendMovesToBoard(board);
                            guiStarter.chessUIManager.drawBoard(board.getBoard());
                        }
                        if (!moveControllers[teamTurn].getIsFirstClick()) {
                            var moves = moveControllers[teamTurn].getAllPossibleMoves();
                            for (BoardLocation location : moves) {
                                guiStarter.chessUIManager.boardTiles[location.row][location.column]
                                        .setPossibleMoveCircleVisibility(true);
                            }
                            UILibrary.MainFrame.repaint();
                        } else if (moveControllers[teamTurn].getIsFirstClick()) {
                            for (int r = 0; r < 8; r++) {
                                for (int c = 0; c < 8; c++) {
                                    guiStarter.chessUIManager.boardTiles[r][c].setPossibleMoveCircleVisibility(false);
                                }
                            }
                            UILibrary.MainFrame.repaint();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void startGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    @Override
    public void endGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }

}
