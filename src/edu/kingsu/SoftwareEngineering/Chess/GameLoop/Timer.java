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

    /**
     * The thread to run the timer on.
     */
    private Thread thread;

    /**
     * Is the timer running.
     */
    private boolean running = false;

    /**
     * A synchronized list to keep track of seconds and if the timer is paused or not.
     */
    List<Integer> seconds = Collections.synchronizedList(new ArrayList<>());

    /**
     * The team associated with this timer.
     */
    private int team;

    /**
     * The timer public constructor.
     * @param team The team the timer should be associated with.
     */
    public Timer(int team) {
        this.seconds.add(0);
        this.seconds.add(0);
        this.seconds.add(0);
        thread = new Thread(this);
        resetTimer();
        this.team = team;
    }

    /**
     * Gets the timer's seconds (0-60)
     * @return The seconds of the timer.
     */
    public int getSeconds() {
        return seconds.get(0);
    }

    /**
     * Gets the minutes the timer has ran.
     * @return The minutes the timer was running for.
     */
    public int getMinutes() {
        return seconds.get(1);
    }

    /**
     * Pause the timer.
     */
    public void pause() {
        this.seconds.set(2, 1);
    }

    /**
     * Un-pause the timer.
     */
    public void unpause() {
        this.seconds.set(2, 0);
    }

    /**
     * Reset all values in the timer and unpause.
     */
    public void resetTimer() {
        seconds.set(0, 0);
        seconds.set(1, 0);
        seconds.set(2, 0);
    }

    /**
     * Is the timer running.
     * @return True if running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Starts the timer thread.
     */
    public void startTimer() {
        thread.start();
    }

    @Override
    public String toString() {
        String output = "";
        if (getMinutes() < 10)
            output += "0";
        output += getMinutes();
        output += ":";
        if (getSeconds() < 10)
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
                if (seconds.get(2) == 1)
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

    /**
     * Stop the timer, pause the thread calling this
     * until the timer has stopped.
     */
    public void stopTimer() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
