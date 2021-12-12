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

import com.BrickDestroy.Model.Ball;
import com.BrickDestroy.Model.Wall;
import com.BrickDestroy.View.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * DebugConsole is part of the debugging window.
 * It acts as the frame that holds the different buttons and sliders initialised by DebugPanel.
 *
 * @author Fathan
 * @version 2.0
 * @see DebugPanel
 */
public class DebugConsole extends JDialog implements WindowListener {

    private static final String TITLE = "Debug Console";

    private JFrame owner;
    private DebugPanel debugPanel;
    private GameView gameView;
    //private GameController gameController;
    private Wall wall;

    /**
     * Constructor for DebugConsole.
     * Takes in the owner, wall, and gameBoard and is called in gameBoard.
     *
     */
    public DebugConsole(JFrame owner, Wall wall, GameView gameView) {

        this.setWall(wall);
        this.setOwner(owner);
        this.setGameView(gameView);
        initialize();

        setDebugPanel(new DebugPanel(wall));
        this.add(getDebugPanel(), BorderLayout.CENTER);

        this.pack();
    }

    /**
     * Called in constructor to set the environment.
     */
    private void initialize() {
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }

    /**
     * Sets the location of the console.
     */
    private void setLocation() {
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x, y);
    }

    /**
     * When window is opened.
     */
    @Override
    public void windowOpened(WindowEvent windowEvent) {
    }

    /**
     * When window is closing.
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gameView.repaint();
    }

    /**
     * When window has closed.
     */
    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    /**
     * When window is iconified.
     */
    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    /**
     * When window is deiconified.
     */
    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    /**
     * Activates the window and reapplies movement of the ball.
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball b = getWall().ball;
        getDebugPanel().setValues(b.getSpeedX(), b.getSpeedY());
    }

    /**
     * When window is deactivated.
     */
    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }


    public void setOwner(JFrame owner) {
        this.owner = owner;
    }

    public void setDebugPanel(DebugPanel debugPanel) {
        this.debugPanel = debugPanel;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public DebugPanel getDebugPanel() {
        return debugPanel;
    }

    public GameView getGameView() {
        return gameView;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

/*
    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameBoardView getGameBoardView() {
        return gameBoardView;
    }

    public void setGameBoardView(GameBoardView gameBoardView) {
        this.gameBoardView = gameBoardView;
    }
 */
}
