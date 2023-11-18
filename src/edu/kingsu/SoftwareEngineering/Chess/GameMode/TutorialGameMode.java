package edu.kingsu.SoftwareEngineering.Chess.GameMode;

import java.io.File;
import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNMove;

public class TutorialGameMode extends GameMode {

    private ArrayList<PGNMove> moves;
    private int index = 0;

    public TutorialGameMode(File file, Board board) {
//        moves = board.loadPGNFileFromStart(file);
    }

    public String getComment() {
        return moves.get(index).getComment();
    }

    @Override
    public void undoMove() {
        index++;
    }

    @Override
    public void redoMove() {
        index--;
    }

    @Override
    public void initialize(Board board, GUIStarter guiStarter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @Override
    protected void setClickListeners(GUIStarter guiStarter, Board board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setClickListeners'");
    }

    @Override
    public void switchTeam() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'switchTeam'");
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
