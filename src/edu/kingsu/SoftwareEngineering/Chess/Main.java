package edu.kingsu.SoftwareEngineering.Chess;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;

import javax.swing.*;

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
		if( SystemInfo.isLinux ) {
			// enable custom window decorations
			JFrame.setDefaultLookAndFeelDecorated( true );
			JDialog.setDefaultLookAndFeelDecorated( true );
		}
		FlatDarkLaf.setup();
		new GameLoop();
	}
}
