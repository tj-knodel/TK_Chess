package edu.kingsu.SoftwareEngineering.Chess.Players;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;

import java.util.ArrayList;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;
/**
 * @author Thaler Knodel
 * @version 0.1.0
 */
public class AIPlayer extends Player {

    private int difficulty;

    /**
     * Creates a new AIPlayer with the given difficulty level
     * @param difficulty the level of depth for the AI algorithm
     * @param colour the colour of the players pieces
     */
    public AIPlayer(int difficulty, int colour) {
        // chain constructor with the default name of "Computer"
        this(difficulty, colour, "Computer");
    }

    /**
     * Creates a new AIPlayer with the given difficulty level and gives it a specified name
     * @param difficulty the level of depth for the AI algorithm
     * @param colour the colour of the players pieces
     * @param name the name of the player
     */
    public AIPlayer(int difficulty, int colour, String name) {
        this.colour = colour;
        this.difficulty = difficulty;
        this.name = name;
    }
    /**
     * Gets a move from the AI
     * @return a move
     */
    public BoardLocation getMove() {
        return null;
    }

    private int randomMove(Board board) {
        Piece[][] pieces = board.getBoard();

    }

    /**
     * The minimax algorithm for the AI player
     * @return
     */
    private int minimax(Board board, int depth, int player) {
        Piece[][] pieces = board.getBoard();
        if (depth == 0) {
            return 0;
        }

        // the score of the board is declared here
        
        int score = 0;
        if (player == 1) {
            // set score to some negative number more than is possible
            score = -128;
            // iterate over all the pieces of the max player to see what is the best move
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j].getTeam() == player) {
                        ArrayList<BoardLocation> moves = board.getPossibleMoves(pieces[i][j], new BoardLocation(i, j));
                    }
                }
            }
        }
        else {
            // set score to some positive number that is more than is possible in the game
            score = 128;
            // iterate over all the pieces of the min player to find the best move
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j].getTeam() == player) {
                        ArrayList<BoardLocation> moves = board.getPossibleMoves(pieces[i][j], new BoardLocation(i, j));
                    }
                }
            }
        }
        return score;
    }
}
