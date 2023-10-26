package edu.kingsu.SoftwareEngineering.Chess.Players;
import edu.kingsu.SoftwareEngineering.Chess.Board.Move;

/**
 * @author Thaler Knodel
 * @version 0.1.0
 */
public abstract class Player {
    private int colour;
    private String name;

    /**
     * Gets a move from the player and returns it
     * @return the selected move
     */
    public abstract Move getMove();

    /**
     * Gets the colour of the players pieces (i.e white or black)
     * @return the colour of the players pieces
     */
    public int getColour() {
        return colour;
    }

    /**
     * Sets the colour of the players pieces to a different colour
     * @param colour the desired colour or team of the player
     */
    public void setColour(int colour) {
        this.colour = colour;
    }

    /**
     * Gets the name of the player
     * @return the name given to the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player to the desired name
     * @param name the new name for the player
     */
    public void setName(String name) {
        this.name = name;
    }

}
