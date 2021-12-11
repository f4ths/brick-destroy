package com.BrickDestroy.Model;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * This is a Countdown Timer class that shows the player a countdown that acts as the maximum time the player could take to clear the game.
 * When the timer reaches 0, it is game over.
 * The timer is drawn in GameBoard.
 */
public class CountdownTimer implements ActionListener {
    private final int STARTING_SECONDS = 5000;
    private int seconds = STARTING_SECONDS;

    Timer time;

    /**
     * The timer updates every 1 second.
     */
    public CountdownTimer(){
        time = new Timer(1000, this);
    }

    /**
     * Starts the timer.
     */
    public void startTimer() {
        time.start();
    }

    /**
     * Decreases the timer value by 1 if it has not yet reached 0.
     */
    public void actionPerformed(ActionEvent e) {
        if(seconds == 0)
            time.stop();
        else
            seconds--;
    }

    /**
     * Stops the timer. Called in actionPerformed().
     */
    public void stopTimer(){
        time.stop();}

    /**
     * Resets the timer value to its original value.
     */
    public void resetTimer(){
        seconds = STARTING_SECONDS;}

    public int getTimer(){
        return seconds;}
}

