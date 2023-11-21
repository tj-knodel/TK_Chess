package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.UILibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A timer class responsible for timing by seconds in a separate thread.
 * @author Shaun Robinson
 */
public class Timer implements java.lang.Runnable {

    Thread thread;
    private boolean running = false;
    List<Integer> seconds = Collections.synchronizedList(new ArrayList<>());
    private GameLoop gameLoop;
    private int team;

    public Timer(GameLoop gameLoop, int team) {
        this.seconds.add(0);
        this.seconds.add(0);
        thread = new Thread(this);
        resetTimer();
        this.gameLoop = gameLoop;
        this.team = team;
    }

    public int getSeconds() {
        return seconds.get(0);
    }

    public int getMinutes() { return seconds.get(1); }

    public void resetTimer() {
        seconds.set(0, 0);
        seconds.set(1, 0);
    }

    public void startTimer() {
        thread.start();
    }

    @Override
    public String toString() {
        return getMinutes() + ":" + getSeconds();
    }

    /*A singular time function that shifts the timer to each team whenever a successful move is made */
    @Override
    public void run() {
        running = true;

        while (running) {
            try {

                Thread.sleep(1000L);
                seconds.set(0, seconds.get(0) + 1);
                if(seconds.get(0) == 60) {
                    seconds.set(1, seconds.get(1) + 1);
                    seconds.set(0, 0);
                }
                if(team == Team.WHITE_TEAM) {
                    UILibrary.WhiteTimer.setText("WHITE TIME: " + toString());
                } else {
                    UILibrary.BlackTimer.setText("BLACK TIME: " + toString());
                }
//                System.out.println(seconds.get(0) + " second has passed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void stopTimer() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
