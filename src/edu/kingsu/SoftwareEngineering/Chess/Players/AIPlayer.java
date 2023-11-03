package edu.kingsu.SoftwareEngineering.Chess.Players;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;
/**
 * @author Thaler Knodel
 * @version 0.1.0
 */
public class AIPlayer extends Player implements Runnable {

    // just a note
    /*
     * We can make this multithreaded without changing this class by making a special AIThread object
     * that contains an AIPlayer 
     */
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

    /**
     * Helper function that gets the current score of the board
     */
    private int calcScore(Piece[][] board) {
        int score = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int pieceScore = board[i][j].getValue();
                if (board[i][j].getTeam() == 1) {
                    score += pieceScore;
                } else if(board[i][j].getTeam() == 0) {
                    score -= pieceScore;
                }
            }
        }
        return score;
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
            return calcScore(pieces);
        }

        // the board is properly copied here
        Board copy = board.copyBoard();

        // the score of the board is declared here
        int score = 0;
        if (player == Team.WHITE_TEAM) {
            // set score to some negative number more than is possible
            score = -200;
            // iterate over all the pieces of the max player to see what is the best move
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j].getTeam() == player) {
                        ArrayList<BoardLocation> moves = board.getPossibleMoves(pieces[i][j], new BoardLocation(i, j));
                        //score = Math.max(score, minimax(copy,))
                    }
                }
            }
        }
        else {
            // set score to some positive number that is more than is possible in the game
            score = 200;
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
