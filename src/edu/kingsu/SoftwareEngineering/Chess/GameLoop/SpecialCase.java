package edu.kingsu.SoftwareEngineering.Chess.GameLoop;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;

public class SpecialCase {
    private boolean verifyCastle = false;
    private boolean verifyPassant = false;
    private boolean verifyPromote = false;


    /*Castle check method to see if:
     * the chosen rook or king has moved once before (Reject if so)
     * If the rook and king will move over a check slot (Reject if so)
     * 
     */
    public boolean castleCheck(int team){

        



        return verifyCastle;
    }


     /*En passant check method to see if:
     * If the passing pawn has moved before jumping 2 tiles (Reject if so)
     * If the capturing pawn has passed over the square the passing pawn moved over (Allow if true)
      */
    public boolean enPassantCheck(){



        return verifyPassant;
    }

      /*Promote check method to see if:
       * the pawn has reached the opposite side of the board (must have the team number as a flag)
       * if(piece instanceof Pawn) 
       */
    public boolean promoteCheck(){
        


        return verifyPromote;
    }

}
