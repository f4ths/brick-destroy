package com.BrickDestroy.Model;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class CountdownTimer implements ActionListener {
    private int seconds = 200;
   Timer time;

    public CountdownTimer(){
        time = new Timer(1000, this);
    }

    public void startTimer() {
        time.start();
    }

    public void actionPerformed(ActionEvent e) {
        if(seconds == 0)
            time.stop();
        else
            seconds--;
    }

    public void stopTimer(){
        time.stop();}

    public void resetTimer(){
        seconds= 200;}

    public int getTimer(){
        return seconds;}
}

