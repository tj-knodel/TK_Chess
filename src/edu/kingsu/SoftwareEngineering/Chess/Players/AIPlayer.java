package edu.kingsu.SoftwareEngineering.Chess.Players;
import edu.kingsu.SoftwareEngineering.Chess.Board.*;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;

import java.util.ArrayList;
import java.util.Random;
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
        //minimax(board, difficulty, colour);
        //Move move = randomMove(board);
        // get the pieces from the board
        Piece[][] pieces = board.getBoard();
        // get all the pieces for the team
        ArrayList<BoardLocation> team_pieces = new ArrayList<BoardLocation>();
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j].getTeam() == colour) {
                    team_pieces.add(new BoardLocation(j, i));
                }
            }
        }
        
        ArrayList<Move> allMoves = new ArrayList<Move>();
        // check each move for this team
        for (BoardLocation l : team_pieces) {
            Piece piece = pieces[l.row][l.column];
            ArrayList<BoardLocation> moves = board.getPossibleMoves(piece, l, false);
            for (BoardLocation lprime : moves) {
                // add new moves with a placeholder score which we will update later
                allMoves.add(new Move(piece, l, lprime, 0));
            }
        }
        if (allMoves.isEmpty()) {
            // There are no possible moves
            return null;
        }

        int bestScore = (colour == Team.WHITE_TEAM) ? -200 : 200;
        Move bestMove = null;
        for (Move m : allMoves) {
            Board copy = board.copy();
            copy.applyMove(m.piece, m.start, m.end);
            int curScore = minimax(copy, difficulty, colour);
            m.score = curScore;
            if (colour == Team.WHITE_TEAM) {
                if (m.score >= bestScore) {
                    bestMove = m;
                    bestScore = m.score;
                }
            } else if (colour == Team.BLACK_TEAM) {
                if (m.score <= bestScore) {
                    bestMove = m;
                    bestScore = m.score;
                }
            }
        }
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
                if (board[i][j].getTeam() == 1) {
                    score += pieceScore;
                } else if(board[i][j].getTeam() == 0) {
                    score -= pieceScore;
                }
            }
        }
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

            ArrayList<BoardLocation> possible_moves = board.getPossibleMoves(piece, start_move, true);
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
     * The minimax algorithm for the AI player
     * @return the score for the current iteration of the minimax.
     */
    private int minimax(Board board, int depth, int player) {
        Piece[][] pieces = board.getBoard();
        if (depth <= 0) {
            return calcScore(pieces);
        }

        // the board is properly copied here
        //Board copy = board.copyBoard();

        // the score of the board is declared here
        int score = 0;
        if (player == Team.WHITE_TEAM) {
            // set score to some negative number more than is possible
            score = -200;
            // get the white team pieces
            //ArrayList<BoardLocation> white_pieces = board.
            
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j].getTeam() == player) {
                        // Get all the moves for this piece
                        BoardLocation pieceLocation = new BoardLocation(j,i);
                        ArrayList<BoardLocation> moves = board.getPossibleMoves(pieces, pieces[i][j], pieceLocation, false);
                        // Find the maximum move and store the piece and the location in a move
                        //Move maxMove = getMaxMove(copy, pieces[i][j], new BoardLocation(j, i), moves);
                        for (BoardLocation l : moves) {
                            Board copy = board.copy();
                            copy.applyMove(pieces[i][j], pieceLocation, l);
                            score = Math.max(score, minimax(copy, depth - 1, Team.BLACK_TEAM));
                        }
                    }
                }
            }
            return score;
        }
        else {
            // set score to some positive number that is more than is possible in the game
            score = 200;
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j].getTeam() == player) {
                        // Get all the moves for this piece
                        BoardLocation pieceLocation = new BoardLocation(j,i);
                        ArrayList<BoardLocation> moves = board.getPossibleMoves(pieces, pieces[i][j], pieceLocation, false);
                        // Find the maximum move and store the piece and the location in a move
                        //Move maxMove = getMaxMove(copy, pieces[i][j], new BoardLocation(j, i), moves);
                        for (BoardLocation l : moves) {
                            Board copy = board.copy();
                            copy.applyMove(pieces[i][j], pieceLocation, l);
                            score = Math.min(score, minimax(copy, depth - 1, Team.WHITE_TEAM));
                        }
                    }
                }
            }
            return score;
        }
        
    }

    /**
     * Finds the maximum move out of a list of moves
     * @param moves
     * @param board
     * @return
     */
    private Move getMaxMove(Board board, Piece piece, BoardLocation pieceLocation, ArrayList<BoardLocation> moves) {
        int curMaxScore = -200;
        Move curMaxMove = null;
        for (BoardLocation l : moves) {
            board.applyMove(piece, pieceLocation, l);
            int moveScore = calcScore(board.getBoard());
            if (moveScore > curMaxScore) {
                curMaxMove = new Move(piece, pieceLocation, pieceLocation, moveScore);
            }
        }
        return curMaxMove;
    }

    /**
     * Finds the minimum move out of a list of moves
     * @param moves
     * @param board
     * @return
     */
    private Move getMinMove(Board board, Piece piece, BoardLocation pieceLocation, ArrayList<BoardLocation> moves) {
        int curMinScore = 200;
        Move curMinMove = null;
        for (BoardLocation l : moves) {
            board.applyMove(piece, pieceLocation, l);
            int moveScore = calcScore(board.getBoard());
            if (moveScore > curMinScore) {
                curMinMove = new Move(piece, pieceLocation, pieceLocation, moveScore);
            }
        }
        return curMinMove;
    }
}
