package edu.kingsu.SoftwareEngineering.Chess;

import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIManager;
import edu.kingsu.SoftwareEngineering.Chess.GameLoop.Timer;
import edu.kingsu.SoftwareEngineering.Chess.Board.Board;

/**
 * Tell me about this class
 *
 * @author Who are you?
 * @version 1.2.3.4.5
 */
public class Main {

	/**
	 * Replace me with something useful
	 *
	 * @param args What is args for?
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

	//	Board b = new Board();
	

	Timer timer = new Timer();
	timer.startTimer();

		//GUIManager ui = new GUIManager();
		

		while (timer.getSeconds() < 10){
			Thread.sleep(6000);
		}
    
	 timer.stopTimer();
		
	}

	/**
	 * The "default" construtor—take it or leave it
	 */
	public Main() {
	}
}
