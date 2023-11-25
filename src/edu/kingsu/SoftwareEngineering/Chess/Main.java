package edu.kingsu.SoftwareEngineering.Chess;

import com.formdev.flatlaf.FlatDarkLaf;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;

/**
 * Main Chess File, kick starts all the other files
 */
public class Main {

	/**
	 * Not Used
	 */
	public Main(){}

	/**
	 * Starts the chess game.
	 * @param args Not used in this application.
	 */
	public static void main(String[] args) {
		FlatDarkLaf.setup();
		new GameLoop();
	}
}
