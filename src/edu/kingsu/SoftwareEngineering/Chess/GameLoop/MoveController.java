package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;

public class MoveController {
    // TODO: Just a hacky way to do things now. Make this work without being static
    private BoardLocation firstClick = new BoardLocation(-1, -1);
    private BoardLocation secondClick = new BoardLocation(-1, -1);
    private boolean isFirstClick = true;
    private ArrayList<BoardLocation> arr;

    public MoveController() {
        this.arr = new ArrayList<>();
    }

    /*
     * The user will select a piece from the board. The GUI will send a message to
     * the board asking for the possible
     */
    public boolean sendMovesToBoard(Board board) {
        Piece pieceMoving = board.getBoard()[firstClick.row][firstClick.column];
        board.applyMove(pieceMoving, firstClick, secondClick);
        System.out.println("SENT");
        return false;
    }

    /* From the */
    public ArrayList<BoardLocation> getAllPossibleMoves(BoardLocation location) {

        return arr;
    }

    public boolean chessTileClick(char row, char column) {
        if (isFirstClick) {
            firstClick = new BoardLocation(column, row);
            isFirstClick = false;
            return false;
        } else if (!isFirstClick) {
            secondClick = new BoardLocation(column, row);
            isFirstClick = true;
            // board.applyMove(board.getBoard()[firstClick.row][firstClick.column],
            // firstClick, secondClick);
            // gameLoop.sendUpdateBoardState();
            System.out.println("Move from: " + firstClick.column + " " + firstClick.row + " to " + secondClick.column
                    + " " + secondClick.row);
            return true;
        }
        return false;
    }
}
