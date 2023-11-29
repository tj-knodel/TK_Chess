package edu.kingsu.SoftwareEngineering.Chess.Players;

import edu.kingsu.SoftwareEngineering.Chess.Board.*;
import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.*;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Thaler Knodel
 * @version 0.1.0
 */
public class AIPlayer extends Player {

    // The piece-square tables!
    private static double[][] pawnTable = { { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 50, 50, 50, 50, 50, 50, 50, 50 },
            { 10, 10, 20, 30, 30, 20, 10, 10 },
            { 5, 5, 10, 25, 25, 10, 5, 5 },
            { 0, 0, 0, 20, 20, 0, 0, 0 },
            { 5, -5, -10, 0, 0, -10, -5, 5 },
            { 5, 10, 10, -20, -20, 10, 10, 5 },
            { 0, 0, 0, 0, 0, 0, 0, 0 } };

    private static double[][] knightTable = { { -50, -40, -30, -30, -30, -30, -40, -50 },
            { -40, -20, 0, 0, 0, 0, -20, -40 },
            { -30, 0, 10, 15, 15, 10, 0, -30 },
            { -30, 5, 15, 20, 20, 15, 5, -30 },
            { -30, 0, 15, 20, 20, 15, 0, -30 },
            { -30, 5, 10, 15, 15, 10, 5, -30 },
            { -40, -20, 0, 5, 5, 0, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 } };

    private static double[][] bishopTable = { { -20, -10, -10, -10, -10, -10, -10, -20 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 10, 10, 5, 0, -10 },
            { -10, 5, 5, 10, 10, 5, 5, -10 },
            { -10, 0, 10, 10, 10, 10, 0, -10 },
            { -10, 10, 10, 10, 10, 10, 10, -10 },
            { -10, 5, 0, 0, 0, 0, 5, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 } };

    private static double[][] rookTable = { { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 5, 10, 10, 10, 10, 10, 10, 5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { 0, 0, 0, 5, 5, 0, 0, 0 } };

    private static double[][] queenTable = { { -20, -10, -10, -5, -5, -10, -10, -20 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 5, 5, 5, 0, -10 },
            { -5, 0, 5, 5, 5, 5, 0, -5 },
            { 0, 0, 5, 5, 5, 5, 0, -5 },
            { -10, 5, 5, 5, 5, 5, 0, -10 },
            { -10, 0, 5, 0, 0, 0, 0, -10 },
            { -20, -10, -10, -5, -5, -10, -10, -20 } };

    private static double[][] kingMidTable = { { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -20, -30, -30, -40, -40, -30, -30, -20 },
            { -10, -20, -20, -20, -20, -20, -20, -10 },
            { 20, 20, 0, 0, 0, 0, 20, 20 },
            { 20, 30, 10, 0, 0, 10, 30, 20 } };

    private static double[][] kingEndTable = { { -50, -40, -30, -20, -20, -30, -40, -50 },
            { -30, -20, -10, 0, 0, -10, -20, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -30, 0, 0, 0, 0, -30, -30 },
            { -50, -30, -30, -30, -30, -30, -30, -50 } };

    // just a note
    /*
     * We can make this multithreaded without changing this class by making a special AIThread object
     * that contains an AIPlayer 
     */
    private int difficulty;

    private long start_time;

    private GameLoop gameLoop;

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

    public AIPlayer(int difficulty, int colour, String name, GameLoop gameLoop) {
        this(difficulty, colour, name);
        this.gameLoop = gameLoop;
    }

    /**
     * Gets a move from the AI
     * @return a move
     */
    public Move getMove(Board board) {
        // return randomMove(board);
        // get the piece locations
        start_time = System.currentTimeMillis();
        ArrayList<BoardLocation> pieceLocations = board.getBoardLocationsForTeam(board.getBoard(), colour);
        ArrayList<Move> moves = new ArrayList<Move>();
        Piece[][] pieces = board.getBoard();
        for (BoardLocation l : pieceLocations) {
            ArrayList<BoardLocation> possibleDestinations = board.getPossibleMoves(pieces, pieces[l.row][l.column], l
            );
            for (BoardLocation l_prime : possibleDestinations) {
                Piece[][] copy = board.cloneBoard2DArray(pieces);
                board.simulateApplyMove(copy, pieces[l.row][l.column], l, l_prime);
                // System.out.println("thinking");
                moves.add(new Move(pieces[l.row][l.column], l, l_prime, minimaxAB(board, copy, Integer.MIN_VALUE, Integer.MAX_VALUE,
                        difficulty - 1, (colour == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM)));
            }
        }

        Move bestMove = null;
        double bestScore = 0;
        if (colour == Team.WHITE_TEAM) {
            bestScore = -20000;
            for (Move m : moves) {
                if (m.score > bestScore) {
                    bestMove = m;
                    bestScore = m.score;
                }
            }
        } else if (colour == Team.BLACK_TEAM) {
            bestScore = 20000;
            for (Move m : moves) {
                if (m.score < bestScore) {
                    bestMove = m;
                    bestScore = m.score;
                }
            }
        }
        // System.out.println("the best move is has a score of " + bestScore);
        return bestMove;
    }

    /**
     * Helper function that gets the current score of the board
     */
    private double calcScore(Board board, Piece[][] pieces) {
        double score = 0;
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                int pieceScore = pieces[i][j].getValue();
                if (pieces[i][j].getTeam() == Team.WHITE_TEAM) {
                    score += pieceScore;
                    switch (pieces[i][j].getPieceName().charAt(0)) {
                        case 'P':
                            score += pawnTable[i][j];
                            break;
                        case 'N':
                            score += knightTable[i][j];
                            break;
                        case 'B':
                            score += bishopTable[i][j];
                            break;
                        case 'R':
                            score += rookTable[i][j];
                            break;
                        case 'Q':
                            score += queenTable[i][j];
                            break;
                        case 'K':
                            score += kingMidTable[i][j] - 50;
                            break;
                        default:
                            break;
                    }
                } else if (pieces[i][j].getTeam() == Team.BLACK_TEAM) {
                    score -= pieceScore;
                    switch (pieces[i][j].getPieceName().charAt(0)) {
                        case 'P':
                            score += -pawnTable[7 - i][j];
                            break;
                        case 'N':
                            score += -knightTable[7 - i][j];
                            break;
                        case 'B':
                            score += -bishopTable[7 - i][j];
                            break;
                        case 'R':
                            score += -rookTable[7 - i][j];
                            break;
                        case 'Q':
                            score += -queenTable[7 - i][j];
                            break;
                        case 'K':
                            score += -kingMidTable[7 - i][j] + 50;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        // System.out.println("Total score is " + score);
        double num_moves = board.getPossibleMovesForTeamWithCheckKingInCheck(pieces, colour).size();
        // if (num_moves == 0.0) {
        //     System.out.println("AAAAAAAAAAAAAAAAAA");
        // }
        double mobilitywt = 1.5;

        if (colour == Team.WHITE_TEAM) {
            num_moves *= mobilitywt;
        } else if (colour == Team.BLACK_TEAM) {
            num_moves *= -mobilitywt;
        }

        // give a higher weight to position with more friendly pieces
        double num_pieces = board.getBoardLocationsForTeam(pieces, colour).size();

        double num_weight = 1.5;

        if (colour == Team.WHITE_TEAM) {
            num_pieces *= num_weight;
        } else if (colour == Team.BLACK_TEAM) {
            num_pieces *= -num_weight;
        }

        return score + num_moves + num_pieces;
    }
    /**
    * The minimax algorithm with AB pruning algorithm for the AI player
    * @return the score for the current iteration of the minimax.
    */
    private double minimaxAB(Board board, Piece[][] pieces, double alpha, double beta, int depth, int player) {
        double score = 0;
        long maxThinkTime = (gameLoop == null) ? 5000 : gameLoop.getAiThinkTimeSeconds() * 1000;
        if (depth <= 0 || System.currentTimeMillis() - start_time >= maxThinkTime
                || board.getBoardLocationsForTeamForPiece(pieces, colour, Piece.KING).isEmpty()) {
            // System.out.println(calcScore(pieces));
            return calcScore(board, pieces);
        }
        // JOptionPane.showConfirmDialog(null, "Got here with team: " + board.getTeamTurn());

        double cur_alpha = alpha;
        double cur_beta = beta;

        // System.out.println("Going down a level!");
        if (player == Team.WHITE_TEAM) {
            // set score to some negative number more than is possible
            score = Double.MIN_VALUE;
            // get the white team pieces
            //ArrayList<BoardLocation> white_pieces = board.
            for (Move move : board.getPossibleMovesForTeamWithoutCheckKingInCheckAsMoveClass(pieces, Team.WHITE_TEAM)) {
                Piece[][] copy = board.cloneBoard2DArray(pieces);
                if (board.getBoardLocationsForTeamForPiece(copy, Team.BLACK_TEAM, Piece.KING).size() == 0) {
                    // captured the king
                    // return calcScore(board, copy);
                }
                board.simulateApplyMove(copy, move.piece, move.start, move.end);
                score = Math.max(score, minimaxAB(board, copy, cur_alpha, cur_beta, depth - 1, Team.BLACK_TEAM));
                // if (!(copy[move.end.row][move.end.column] instanceof EmptyPiece)) {
                //     // score += copy[move.end.row][move.end.column].getValue();
                // }
                cur_alpha = Math.max(cur_alpha, score);
                if (score >= cur_beta) {
                    break;
                }
            }

            // for (int i = 0; i < pieces.length; i++) {
            //     for (int j = 0; j < pieces[i].length; j++) {
            //         if (pieces[i][j].getTeam() == Team.WHITE_TEAM) {
            //             // Get all the moves for this piece
            //             BoardLocation pieceLocation = new BoardLocation(j, i);
            //             ArrayList<BoardLocation> moves = board.getPossibleMoves(pieces, pieces[i][j], pieceLocation
            //             );
            //             // System.out.println("Amount of moves for this piece is " + moves.size());
            //             // Find the maximum move and store the piece and the location in a move
            //             //Move maxMove = getMaxMove(copy, pieces[i][j], new BoardLocation(j, i), moves);
            //             for (BoardLocation l : moves) {
            //                 Piece[][] copy = copyPieces(pieces);
            //                 board.simulateApplyMove(copy, pieces[i][j], pieceLocation, l);
            //                 if (board.getBoardLocationsForTeamForPiece(copy, Team.BLACK_TEAM, Piece.KING).size() == 0) {
            //                     // captured the king
            //                     System.out.println("Captured the black king!");
            //                 }
            //                 score = Math.max(score,
            //                         minimaxAB(board, copy, cur_alpha, cur_beta, depth - 1, Team.BLACK_TEAM));
            //                 cur_alpha = Math.max(cur_alpha, score);
            //                 if (score >= cur_beta) {
            //                     break;
            //                 }
            //             }
            //         }
            //     }
            // System.out.println("This is the score so far " + score);
            return score;
        } else {
            // set score to some positive number that is more than is possible in the game
            score = Double.MAX_VALUE;
            for (Move move : board.getPossibleMovesForTeamWithoutCheckKingInCheckAsMoveClass(pieces, Team.BLACK_TEAM)) {
                Piece[][] copy = board.cloneBoard2DArray(pieces);
                if (board.getBoardLocationsForTeamForPiece(copy, Team.WHITE_TEAM, Piece.KING).size() == 0) {
                    // captured the king
                    // return calcScore(board, copy);
                }
                board.simulateApplyMove(copy, move.piece, move.start, move.end);
                score = Math.min(score, minimaxAB(board, copy, cur_alpha, cur_beta, depth - 1, Team.WHITE_TEAM));
                // if (!(copy[move.end.row][move.end.column] instanceof EmptyPiece)) {
                //     // score -= copy[move.end.row][move.end.column].getValue();
                // }
                cur_beta = Math.min(cur_beta, score);
                if (score <= cur_alpha) {
                    break;
                }
            }
            // for (int i = 0; i < pieces.length; i++) {
            //     for (int j = 0; j < pieces[i].length; j++) {
            //         if (pieces[i][j].getTeam() == Team.BLACK_TEAM) {
            //             // Get all the moves for this piece
            //             BoardLocation pieceLocation = new BoardLocation(j, i);s
            //             ArrayList<BoardLocation> moves = board.getPossibleMoves(pieces, pieces[i][j], pieceLocation
            //             );
            //             // System.out.println("Amount of moves for this piece is " + moves.size());
            //             // Find the maximum move and store the piece and the location in a move
            //             //Move maxMove = getMaxMove(copy, pieces[i][j], new BoardLocation(j, i), moves);
            //             for (BoardLocation l : moves) {
            //                 Piece[][] copy = copyPieces(pieces);
            //                 board.simulateApplyMove(copy, pieces[i][j], pieceLocation, l);
                            // if (board.getBoardLocationsForTeamForPiece(copy, Team.WHITE_TEAM, Piece.KING).size() == 0) {
                            //     // captured the king
                            //     System.out.println("Captured the whiteking!");
                            // }
            //                 score = Math.min(score,
            //                         minimaxAB(board, copy, cur_alpha, cur_beta, depth - 1, Team.WHITE_TEAM));
            //                 cur_beta = Math.min(cur_beta, score);
            //                 if (score <= cur_alpha) {
            //                     break;
            //                 }
            //             }
            //         }
            //     }
            // System.out.println("This is the score so far " + score);
            return score;
        }
    }
}
