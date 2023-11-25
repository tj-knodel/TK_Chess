package edu.kingsu.SoftwareEngineering.Chess;

import com.formdev.flatlaf.FlatDarkLaf;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;

public class Main {

	/**
	 * Starts the chess game.
	 * @param args Not used in this application.
	 */
	public static void main(String[] args) {
		FlatDarkLaf.setup();
		new GameLoop();
	}
}
