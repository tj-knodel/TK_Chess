package edu.kingsu.SoftwareEngineering.Chess.Players;

import edu.kingsu.SoftwareEngineering.Chess.Board.*;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * @author Thaler Knodel
 * @version 0.1.0
 */
public class AIPlayer extends Player {

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
    public Move getMove(Board board) {
        // return randomMove(board);
        // get the piece locations
        ArrayList<BoardLocation> pieceLocations = board.getBoardLocationsForTeam(board.getBoard(), colour);
        ArrayList<Move> moves = new ArrayList<Move>();
        Piece[][] pieces = board.getBoard();
        for (BoardLocation l : pieceLocations) {
            ArrayList<BoardLocation> possibleDestinations = board.getPossibleMoves(pieces, pieces[l.row][l.column], l, true);
            for (BoardLocation l_prime : possibleDestinations) {
                Piece[][] copy = copyPieces(pieces);
                board.simulateApplyMove(copy, pieces[l.row][l.column], l, l_prime);
                moves.add(new Move(pieces[l.row][l.column], l, l_prime, minimax(board, copy, difficulty - 1, colour)));
            }
        }

        Move bestMove = null;
        int bestScore;
        if (colour == Team.WHITE_TEAM) {
            bestScore = -200;
            for (Move m : moves) {
                if (m.score > bestScore) {
                    bestMove = m;
                    bestScore = m.score;
                }
            }
        } else {
            bestScore = 200;
            for (Move m : moves) {
                if (m.score < bestScore) {
                    bestMove = m;
                    bestScore = m.score;
                }
            }
        }
        System.out.println("the best move is has a score of " + bestScore);
        return bestMove;
    }

    /**
     * Helper function that gets the current score of the board
     */
    private int calcScore(Piece[][] board) {
        int score = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int pieceScore = board[i][j].getValue();
                if (board[i][j].getTeam() == Team.WHITE_TEAM) {
                    score += pieceScore;
                } else if (board[i][j].getTeam() == Team.BLACK_TEAM) {
                    score -= pieceScore;
                }
            }
        }
        // System.out.println("Total score is " + score);
        return score;
    }

    private Move randomMove(Board board) {
        Piece[][] pieces = board.getBoard();
        ArrayList<BoardLocation> team_pieces = new ArrayList<BoardLocation>();
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j].getTeam() == colour) {
                    team_pieces.add(new BoardLocation(j, i));
                }
            }
        }
        // make a random move
        Random random = new Random();
        Piece piece = null;
        BoardLocation start_move = null, end_move = null;
        boolean valid_move = false;
        while (valid_move == false) {
            // pick a random piece
            int idx = random.nextInt(team_pieces.size());
            start_move = team_pieces.get(idx);
            piece = pieces[start_move.row][start_move.column];

            ArrayList<BoardLocation> possible_moves = board.getPossibleMoves(pieces, piece, start_move, true);
            System.out.println(possible_moves.size());
            if (possible_moves.isEmpty()) {
                System.out.println("removing index");
                // remove the invalid move
                team_pieces.remove(idx);
                continue;
            } else {
                end_move = possible_moves.get(random.nextInt(possible_moves.size()));
                valid_move = true;
            }
            // pick a random move
        }
        if (end_move == null || start_move == null) {
            // there were no valid moves
            return null;
        }
        return new Move(piece, start_move, end_move, 0);
    }

    /**
     * Private method to make a deep copy of an array of pieces
     * @param init_pieces
     * @return a copy of init_pieces
     */
    private Piece[][] copyPieces(Piece[][] init_pieces) {
        Piece[][] copy = new Piece[init_pieces.length][init_pieces[0].length];
        for (int i = 0; i < init_pieces.length; i++) {
            for (int j = 0; j < init_pieces.length; j++) {
                copy[i][j] = init_pieces[i][j];
            }
        }
        return copy;
    }

    /**
     * The minimax algorithm for the AI player
     * @return the score for the current iteration of the minimax.
     */
    private int minimax(Board board, Piece[][] pieces, int depth, int player) {
        if (depth <= 0) {
            // System.out.println(calcScore(pieces));
            return calcScore(pieces);
        }
        // JOptionPane.showConfirmDialog(null, "Got here with team: " + board.getTeamTurn());

        // the score of the board is declared here
        int score = 0;
        // System.out.println("Going down a level!");
        if (player == Team.WHITE_TEAM) {
            // set score to some negative number more than is possible
            score = -200;
            // get the white team pieces
            //ArrayList<BoardLocation> white_pieces = board.

            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j].getTeam() == Team.WHITE_TEAM) {
                        // Get all the moves for this piece
                        BoardLocation pieceLocation = new BoardLocation(j, i);
                        ArrayList<BoardLocation> moves = board.getPossibleMoves(pieces, pieces[i][j], pieceLocation,
                                true);
                        // System.out.println("Amount of moves for this piece is " + moves.size());
                        // Find the maximum move and store the piece and the location in a move
                        //Move maxMove = getMaxMove(copy, pieces[i][j], new BoardLocation(j, i), moves);
                        for (BoardLocation l : moves) {
                            Piece[][] copy = copyPieces(pieces);
                            board.simulateApplyMove(copy, pieces[i][j], pieceLocation, l);
                            score = Math.max(score, minimax(board, copy, depth - 1, Team.BLACK_TEAM));
                        }
                    }
                }
            }
            // System.out.println("This is the score so far " + score);
            return score;
        } else {
            // set score to some positive number that is more than is possible in the game
            score = 200;
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j].getTeam() == Team.BLACK_TEAM) {
                        // Get all the moves for this piece
                        BoardLocation pieceLocation = new BoardLocation(j, i);
                        ArrayList<BoardLocation> moves = board.getPossibleMoves(pieces, pieces[i][j], pieceLocation,
                                true);
                        // System.out.println("Amount of moves for this piece is " + moves.size());
                        // Find the maximum move and store the piece and the location in a move
                        //Move maxMove = getMaxMove(copy, pieces[i][j], new BoardLocation(j, i), moves);
                        for (BoardLocation l : moves) {
                            Piece[][] copy = copyPieces(pieces);
                            board.simulateApplyMove(copy, pieces[i][j], pieceLocation, l);
                            score = Math.min(score, minimax(board, copy, depth - 1, Team.WHITE_TEAM));
                        }
                    }
                }
            }
            // System.out.println("This is the score so far " + score);
            return score;
        }

    }
}
