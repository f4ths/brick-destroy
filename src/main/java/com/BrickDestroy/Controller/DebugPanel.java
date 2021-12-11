/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.BrickDestroy.Controller;

import com.BrickDestroy.Model.Wall;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * DebugPanel is part of the debugging window that the user may use to skip levels, reset balls, and adjust the speed of the ball.
 * DebugPanel holds methods that deal with the buttons and sliders that the user may interact with.
 * These buttons are sliders are contained within the DebugConsole.
 *
 * @author Fathan
 * @version 2.0
 * @see DebugConsole
 */
public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;

    private static final int MIN = -4;
    private static final int MAX = 4;

    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    /**
     * This is the constructor for DebugPanel. It sets up the environment to draw all the necessary buttons and sliders.
     * It takes in a wall object in order to link the buttons to perform the methods stored in Wall:
     *      skipLevel sets the wall to the next level
     *      resetBalls resets the ball count
     *      ballSpeed sets the speed of the ball
     *
     * @param wall Takes in wall object to get the logic for the components.
     */
    public DebugPanel(Wall wall) {

        initialize();

        JButton skipLevel = makeButton("Skip Level", e -> wall.nextLevel());
        JButton resetBalls = makeButton("Reset Balls", e -> wall.resetBallCount());

        ballXSpeed = makeSlider(e -> wall.setBallXSpeed(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(e -> wall.setBallYSpeed(ballYSpeed.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);

    }

    /**
     * Sets the color of the background and the layout of the components.
     */
    private void initialize() {
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2, 2));
    }

    /**
     * Generates a JButton that the user can interact with.
     */
    private JButton makeButton(String title, ActionListener e) {
        JButton out = new JButton(title);
        out.addActionListener(e);
        return out;
    }

    /**
     * Generates a slider that the user can interact with.
     */
    private JSlider makeSlider(ChangeListener e) {
        JSlider out = new JSlider(MIN, MAX);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    /**
     * Sets the x and y value.
     */
    public void setValues(int x, int y) {
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }

}
