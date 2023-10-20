package edu.kingsu.SoftwareEngineering.Chess;

import edu.kingsu.SoftwareEngineering.Chess.GUI.GUIManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	 */
	public static void main(String[] args) {

		// Board b = new Board();

		// GUIManager ui = new GUIManager();

		List<Integer> seconds = Collections.synchronizedList(new ArrayList<>());
		seconds.add(0);

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					seconds.set(0, seconds.get(0) + 1);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

		while (true) {
			System.out.println(seconds.get(0));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * The "default" construtor—take it or leave it
	 */
	public Main() {
	}
}
