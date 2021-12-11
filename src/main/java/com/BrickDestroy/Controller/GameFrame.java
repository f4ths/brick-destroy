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

import com.BrickDestroy.View.HomeMenu;
import com.BrickDestroy.View.Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * GameFrame is the container for the whole game window.
 * It adjusts the size of each screen and allows the program to switch between the home menu, info, and the game.
 *
 * @author Fathan
 * @version 2.0
 * @see GameBoard
 * @see HomeMenu
 * @see Info
 */
public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";

    private GameBoard gameBoard;
    //private GameController gameController;
    private HomeMenu homeMenu;
    private Info gameInfo;

    private boolean gaming;

    /**
     * Constructor for GameFrame
     * Sets the border layout and the dimension of the menu screens.
     * Creates new gameBoard, home menu, and info screen.
     * Shows the home menu as the first screen when the game is run.
     */
    public GameFrame() {
        super();

        gaming = false;

        this.setLayout(new BorderLayout());
        setGameBoard(new GameBoard(this));
        setHomeMenu(new HomeMenu(this, new Dimension(450, 300)));
        setGameInfo(new Info(this, new Dimension(450,300)));

        this.add(getHomeMenu(), BorderLayout.CENTER);

        this.setUndecorated(true);

    }

    /**
     * Called when user clicks the back button in the info screen to return to the home screen.
     */
    public void BackHome(){
        this.dispose();
        this.remove(getGameInfo());
        this.add(getHomeMenu(), BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
    }

    /**
     * Called to change the menu from the home menu to the info screen.
     */
    public void GoToInfo(){
        this.dispose();
        this.remove(getHomeMenu());
        this.add(getGameInfo(), BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
    }

    /**
     * Initialises the window.
     */
    public void initialize() {
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     * Called when the user clicks play to switch the view from the home menu to the game itself.
     */
    public void enableGameBoard() {
        this.dispose();
        this.remove(getHomeMenu());
        this.add(getGameBoard(), BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    /**
     * Sizes the elements automatically.
     */
    private void autoLocate() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }


    /**
     * When user clicks on the window focus is gained.
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true;
    }

    /**
     * When user clicks outside of the window focus is lost.
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if (gaming)
            getGameBoard().onLostFocus();

    }

    /**
     * Getter for gameBoard object
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Setter for gameBoard object
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Getter for homeMenu object
     */
    public HomeMenu getHomeMenu() {
        return homeMenu;
    }

    /**
     * Setter for homeMenu object
     */
    public void setHomeMenu(HomeMenu homeMenu) {
        this.homeMenu = homeMenu;
    }
    /**
     * Getter for gameInfo object
     */
    public Info getGameInfo() {
        return gameInfo;
    }
    /**
     * Setter for gameInfo object
     */
    public void setGameInfo(Info gameInfo) {
        this.gameInfo = gameInfo;
    }
/*
    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

*/
}
