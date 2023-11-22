package edu.kingsu.SoftwareEngineering.Chess.Board;

/**
 * Some static constants for the teams.
 * Easier to keep track of ID numbers for each
 * team color.
 * @author Daniell Buchner
 * @version 0.2.0
 */
public class Team {
    /**
     * The White team constant value.
     */
    public static final int WHITE_TEAM = 1;

    /**
     * The Black team constant value.
     */
    public static final int BLACK_TEAM = 0;

    /**
     * Gets the other team. If team is white, it will return Team.BLACK_TEAM
     * @param team The team to get the other team from.
     * @return The other team.
     */
    public int getOtherTeam(int team) {
        return (team == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
    }
}
