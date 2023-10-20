package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Timer implements java.lang.Runnable {

    Thread thread;
    private boolean running = false;
    List<Integer> seconds = Collections.synchronizedList(new ArrayList<>());

    public Timer(){
        this.seconds.add(0);
        thread = new Thread(this);
    }

    public int getSeconds(){
        return seconds.get(0);
    }


    public void startTimer(){
        thread.start();
    }

    /*A singular time function that shifts the timer to each team whenever a successful move is made */
    @Override
    public void run() {
        running = true;

        while (running){
            try {

                seconds.set(0, seconds.get(0) + 1);
                System.out.println( seconds.get(0) + " second has passed");
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        
    }

    public void stopTimer(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
