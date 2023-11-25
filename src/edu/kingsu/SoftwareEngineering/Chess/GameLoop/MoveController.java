package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI;
import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIStarter;

/**
 * The class that handles controlling the moves.
 */
public class MoveController {
    /**
     * Location of the first click.
     */
    private BoardLocation firstClick = new BoardLocation(-1, -1);
    /**
     * Location of the second click.
     */
    private BoardLocation secondClick = new BoardLocation(-1, -1);
    /**
     * If the click was the first click or not.
     */
    private boolean isFirstClick = true;

    /**
     * Possible moves that can be moved to for a piece clicked on.
     */
    private ArrayList<BoardLocation> possibleMoves;

    public MoveController() {
        this.possibleMoves = new ArrayList<>();
    }

    /**
     * Sends the desired move to the board to be applied.
     * @param board The board to send the move to.
     * @return False.
     */
    public MoveResult sendMovesToBoard(Board board) {
        Piece pieceMoving = board.getBoard()[firstClick.row][firstClick.column];
        return board.applyMoveUpdateGUI(pieceMoving, firstClick, secondClick);
    }

    /**
     * Gets the location of the first click on the board.
     * @return The location of the first click.
     */
    public BoardLocation getFirstClickLocation() {
        return firstClick;
    }

    /**
     * Gets all possible moves for this click.
     * @return Possible moves for the click.
     */
    public ArrayList<BoardLocation> getAllPossibleMoves() {
        return possibleMoves;
    }

    /**
     * If the click was the first click or not.
     * @return True if first click, False otherwise.
     */
    public boolean getIsFirstClick() {
        return isFirstClick;
    }

    public void handleClick(Board board, ChessTileUI chessTile, GUIStarter guiStarter, GameLoop gameLoop) {
        if (chessTileClick(board, chessTile.row,
                chessTile.column)) {
            sendMovesToBoard(board);
        }
        if (!getIsFirstClick()) {
            ArrayList<BoardLocation> moves = getAllPossibleMoves();
            for (BoardLocation location : moves) {
                guiStarter.chessUIManager.boardTiles[location.row][location.column]
                        .setPossibleMoveCircleVisibility(true);
            }
            guiStarter.chessUIManager.boardTiles[getFirstClickLocation().row][getFirstClickLocation().column]
                    .setPreviousMoveSquareVisibility(true);
            gameLoop.redrawUI();
        } else {
            resetCircleVisibility(guiStarter);
            guiStarter.chessUIManager.boardTiles[getFirstClickLocation().row][getFirstClickLocation().column]
                    .setPreviousMoveSquareVisibility(false);
            gameLoop.redrawUI();
        }
    }

    private void resetCircleVisibility(GUIStarter guiStarter) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                guiStarter.chessUIManager.boardTiles[r][c].setPossibleMoveCircleVisibility(false);
            }
        }
    }

    /**
     * Does all the clicking logic.
     * @param board The board to check against.
     * @param row The row that is clicked.
     * @param column The column that is clicked.
     * @return True if successful, otherwise false.
     */
    public boolean chessTileClick(Board board, char row, char column) {
        if (isFirstClick) {
            firstClick = new BoardLocation(column, row);
            Piece piece = board.getBoard()[firstClick.row][firstClick.column];
            if (piece.getTeam() != board.getTeamTurn())
                return false;
            possibleMoves = board.getPossibleMoves(board.getBoard(), piece, firstClick);
            isFirstClick = false;
            return false;
        } else if (!isFirstClick) {
            isFirstClick = true;
            secondClick = new BoardLocation(column, row);
            if (secondClick.isEqual(firstClick)) {
                return false;
            }
            if (clickedOnPossibleMove())
                return true;
            return false;
        }
        return false;
    }

    private boolean clickedOnPossibleMove() {
        for (BoardLocation move : possibleMoves) {
            if (move.isEqual(secondClick)) {
                return true;
            }
        }
        return false;
    }
}
