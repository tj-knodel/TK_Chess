package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import edu.kingsu.SoftwareEngineering.Chess.Board.Team;

public class TimerManager implements Runnable {

    private Thread thread;
    private int team;
    private Timer whiteTimer;
    private Timer blackTimer;

    public TimerManager() {
        this.whiteTimer = new Timer(Team.WHITE_TEAM);
        this.blackTimer = new Timer(Team.BLACK_TEAM);
        this.thread = new Thread(this);
        this.team = Team.WHITE_TEAM;
    }

    public void switchTeams(int team) {
        thread.start();
    }

    @Override
    public void run() {
        if (team == Team.WHITE_TEAM) {
            System.out.println("HERE1");
            if (blackTimer.isRunning())
                blackTimer.stopTimer();
            if (!whiteTimer.isRunning())
                whiteTimer.startTimer();
        } else if (team == Team.BLACK_TEAM) {
            System.out.println("HERE2");
            if (whiteTimer.isRunning())
                whiteTimer.stopTimer();
            if (!blackTimer.isRunning())
                blackTimer.startTimer();
        }
        this.team = Team.getOtherTeam(this.team);
    }
}
