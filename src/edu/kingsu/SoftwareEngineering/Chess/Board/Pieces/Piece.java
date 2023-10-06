package edu.kingsu.SoftwareEngineering.Chess.Board.Pieces;

import java.util.HashMap;

/**
 * The generic piece representation. Does not hold data
 * besides the piece name map to map an integer to a String
 * 
 * @author Daniell Buchner
 * @version 0.1.0
 */
public abstract class Piece {
    protected static HashMap<Integer, String> pieceNameMap;
    protected static int PIECE = -1;
    protected static int PAWN = 0;

    static {
        pieceNameMap = new HashMap<>();
        pieceNameMap.put(Piece.PAWN, "Pawn");
    }

}
