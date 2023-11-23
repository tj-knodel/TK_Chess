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
    private int team;

    public Timer(int team) {
        this.seconds.add(0);
        this.seconds.add(0);
        this.seconds.add(0);
        thread = new Thread(this);
        resetTimer();
        this.team = team;
    }

    public int getSeconds() {
        return seconds.get(0);
    }

    public int getMinutes() {
        return seconds.get(1);
    }

    public void pause() {
        this.seconds.set(2, 1);
    }

    public void unpause() {
        this.seconds.set(2, 0);
    }

    public void resetTimer() {
        seconds.set(0, 0);
        seconds.set(1, 0);
        seconds.set(2, 0);
    }

    public boolean isRunning() {
        return running;
    }

    public void startTimer() {
        thread.start();
    }

    @Override
    public String toString() {
        String output = "";
        if(getMinutes() < 10)
            output += "0";
        output += getMinutes();
        output += ":";
        if(getSeconds() < 10)
            output += "0";
        output += getSeconds();
        return output;
    }

    /*A singular time function that shifts the timer to each team whenever a successful move is made */
    @Override
    public void run() {
        running = true;

        while (running) {
            try {

                Thread.sleep(1000L);
                if(seconds.get(2) == 1)
                    continue;
                seconds.set(0, seconds.get(0) + 1);
                if (seconds.get(0) == 60) {
                    seconds.set(1, seconds.get(1) + 1);
                    seconds.set(0, 0);
                }
                if (team == Team.WHITE_TEAM) {
                    UILibrary.WhiteTimer.setText("WHITE TIME: " + toString());
                } else {
                    UILibrary.BlackTimer.setText("BLACK TIME: " + toString());
                }
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
            System.out.println(e.getMessage());
        }
    }

}
