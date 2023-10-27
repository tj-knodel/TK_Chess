package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;

public class MoveController {
    private BoardLocation firstClick = new BoardLocation(-1, -1);
    private BoardLocation secondClick = new BoardLocation(-1, -1);
    private boolean isFirstClick = true;
    private ArrayList<BoardLocation> possibleMoves;

    public MoveController() {
        this.possibleMoves = new ArrayList<>();
    }

    public boolean sendMovesToBoard(Board board) {
        Piece pieceMoving = board.getBoard()[firstClick.row][firstClick.column];
        board.applyMove(pieceMoving, firstClick, secondClick);
        System.out.println("SENT");
        return false;
    }

    public ArrayList<BoardLocation> getAllPossibleMoves() {
        return possibleMoves;
    }

    public boolean getIsFirstClick() {
        return isFirstClick;
    }

    public boolean chessTileClick(Board board, int team, char row, char column) {
        if (isFirstClick) {
            firstClick = new BoardLocation(column, row);
            Piece piece = board.getBoard()[firstClick.row][firstClick.column];
            if (piece.getTeam() != team)
                return false;
            possibleMoves = board.getPossibleMoves(piece, firstClick);
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
