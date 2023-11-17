package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;

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

    private int playerTeam;
    /**
     * Possible moves that can be moved to for a piece clicked on.
     */
    private ArrayList<BoardLocation> possibleMoves;

    public MoveController(int playerTeam) {
        this.possibleMoves = new ArrayList<>();
        this.playerTeam = playerTeam;
    }

    /**
     * Sends the desired move to the board to be applied.
     * @param board The board to send the move to.
     * @return False.
     */
    public MoveResult sendMovesToBoard(Board board) {
        Piece pieceMoving = board.getBoard()[firstClick.row][firstClick.column];
        return board.applyMove(pieceMoving, firstClick, secondClick, true, true);
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

    public boolean chessTileClick(Board board, char row, char column) {
        return chessTileClick(board, playerTeam, row, column);
    }

    /**
     * Does all the clicking logic.
     * @param board The board to check against.
     * @param teamTurn The team that has the current turn.
     * @param row The row that is clicked.
     * @param column The column that is clicked.
     * @return True if successful, otherwise false.
     */
    public boolean chessTileClick(Board board, int teamTurn, char row, char column) {
        if (isFirstClick) {
            firstClick = new BoardLocation(column, row);
            Piece piece = board.getBoard()[firstClick.row][firstClick.column];
            if (piece.getTeam() != board.getTeamTurn())
                return false;
            possibleMoves = board.getPossibleMoves(piece, firstClick, true);
            isFirstClick = false;
            return false;
        } else if (!isFirstClick) {
            secondClick = new BoardLocation(column, row);
            if (secondClick.row == firstClick.row && secondClick.column == firstClick.column) {
                isFirstClick = true;
                return false;
            }
            for (BoardLocation move : possibleMoves) {
                if (move.row == secondClick.row && move.column == secondClick.column) {
                    isFirstClick = true;
                    return true;
                }
            }
            isFirstClick = true;
            return false;
        }
        return false;
    }
}
