package edu.kingsu.SoftwareEngineering.Chess.GUI;

import edu.kingsu.SoftwareEngineering.Chess.Board.Pieces.Piece;
import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI.PIECES_ENUM;


/*
 
    ChessTileUI functions:
    enum PIECES_ENUM { Pawn, Rook, Knight, Bishop, Queen, King, None }
    
    void setPieceImage(PIECES_ENUM piece, boolean isWhite) 
    void setPossibleMoveCircleVisibility(boolean visibility)
    void setPreviousMoveSquareVisibility(boolean visibility)
 */


public class ChessUIManager {
    
    // -----------------------------------------------------
    // -----------------------------------------------------

    ChessTileUI boardTiles[][];

    // -----------------------------------------------------
    // -----------------------------------------------------

    public void drawBoard(Piece[][] pieces) {

        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                
                Piece pieceObject = pieces[row][column];
                if (pieceObject == null) {
                    boardTiles[row][column].setPieceImage(PIECES_ENUM.None, true);
                } // Keep doing if else statements,  for each piece
                
            }
        }

    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    public ChessUIManager() {
        boardTiles = CreateMainFrame.createChessBoard();
    }


}
