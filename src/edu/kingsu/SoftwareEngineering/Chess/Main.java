package edu.kingsu.SoftwareEngineering.Chess;

import com.formdev.flatlaf.FlatDarkLaf;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.GameLoop;

public class Main {

	public static void main(String[] args) {
		FlatDarkLaf.setup();
		new GameLoop();
	}
}
