package edu.kingsu.SoftwareEngineering.Chess.GameMode;

import org.w3c.dom.events.MouseEvent;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.MoveController;
import edu.kingsu.SoftwareEngineering.Chess.Players.AIPlayer;

public class PlayerVSAIGameMode extends GameMode {
    
    private MoveController moveController;
    private AIPlayer computer;
    private int teamTurn;

    public PlayerVSAIGameMode() {
        this.moveController = new MoveController();
        teamTurn = Team.WHITE_TEAM;

        // what is 0? a random move player
        computer = new AIPlayer(0, Team.BLACK_TEAM);
    }

    @Override
    public void switchTeam() {
        teamTurn = (teamTurn == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
    }

    public void setClickListeners(GUIStarter guiStarter, Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                chessTile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (moveController.chessTileClick(board, teamTurn, chessTile.row,
                                chessTile.column)) {
                            moveController.sendMovesToBoard(board);
                            switchTeam();
                            guiStarter.chessUIManager.drawBoard(board.getBoard());
                        }
                        if (!moveController.getIsFirstClick()) {
                            var moves = moveController.getAllPossibleMoves();
                            for (BoardLocation location : moves) {
                                guiStarter.chessUIManager.boardTiles[location.row][location.column]
                                        .setPossibleMoveCircleVisibility(true);
                            }
                            UILibrary.MainFrame.repaint();
                        } else if (moveController.getIsFirstClick()) {
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
        //throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    @Override
    public void endGame() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }

}
