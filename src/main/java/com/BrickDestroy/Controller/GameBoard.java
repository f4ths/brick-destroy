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

import com.BrickDestroy.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;

/**
 * GameBoard is the class that deals with user input logic and draws the wall object to visualise gameplay.
 * This class also visualises the ballCount, the brickCount, the countdown timer, and the user's score.
 * It is part of the window which is the GameFrame.
 * I have attempted to separate this class into GameController and GameBoardView however it did not work as intended so the plan has been scrapped for now.
 *
 * @author Fathan
 * @version 2.0
 * @see GameFrame
 * @see Wall
 */
public class GameBoard extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0, 255, 0);


    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = new Color(0xE60A0A0A, true);

    private Timer gameTimer;
    private final CountdownTimer countdownTimer = new CountdownTimer();

    private Wall wall;

    private String message;
    private String scoreCount;
    private String timerString;

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;

    /**
     * This is the constructor for GameBoard.
     * It initialises the gameBoard to allow user input to be made.
     * It creates a new wall object and initialises the first level for the user to play when gameBoard is first called.
     * Creates new debugConsole object that will be ready when the user enables it.
     * Starts the game timer.
     */
    public GameBoard(JFrame owner) {
        super();

        strLen = 0;
        showPauseMenu = false;


        setMenuFont(new Font("Monospaced", Font.PLAIN, TEXT_SIZE));


        this.initialize();
        message = "";
        scoreCount = "";
        timerString = "";
        setWall(new Wall(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6 * 0.5, new Point(300, 430)));

        setDebugConsole(new DebugConsole(owner, getWall(), this));
        //initialize the first level
        getWall().nextLevel();

        setGameTimer();

    }

    /**
     * This method deals with the game's timer logic.
     * This has been separated from the constructor to de-clutter it.
     * The timer starts as the components from wall moves, and impacts between objects are looked for.
     * It also prints the initial ballCount, brickCount, time remaining, and user score.
     *
     * When the timer reaches 0, the brick, player, and ball objects are reset and the program displays a Game Over text.
     * When no balls are remaining, Game Over text is shown.
     * When all bricks in one level have been broken, go to the next level of bricks and reset player and ball position.
     * When all bricks in the final level have been broken, display "All walls destroyed" and stop the game timer.
     */
    private void setGameTimer() {
        gameTimer = new Timer(10, e -> {
            getWall().move();
            getWall().findImpacts();
            timerString = "Remaining Time:" + countdownTimer.getTimer();
            scoreCount = "Score: ";
            message = String.format("Bricks: %d Balls %d", getWall().getBrickCount(), getWall().getBallCount());

            countdownTimer.startTimer();
            if(countdownTimer.getTimer() == 0){
                wall.wallReset();
                wall.ballReset();
                countdownTimer.resetTimer();
                gameTimer.stop();
                message = "Game Over";

            }

            if (getWall().isBallLost()) {
                countdownTimer.stopTimer();
                if (getWall().ballEnd()) {
                    getWall().wallReset();
                    message = "Game over";
                }
                getWall().ballReset();
                gameTimer.stop();

            } else if (getWall().isDone()) {
                if (getWall().hasLevel()) {
                    message = "Go to Next Level";
                    gameTimer.stop();
                    getWall().ballReset();
                    getWall().wallReset();
                    getWall().nextLevel();

                } else {
                    message = "ALL WALLS DESTROYED";
                    gameTimer.stop();
                }
            }

            repaint();
        });
    }

    /**
     * Initialises gameBoard
     * Set dimension, request for window focus, enable user input.
     */
    private void initialize() {
        this.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * Gets score from wall object and displays it.
     * Sets the color, font, and size of the text.
     */
    private void showScore(Graphics g){
        int score = wall.getScore();
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.PLAIN,14));
        g.drawString(String.valueOf(score),550,420);
    }

    /**
     * Paint method that allows the class to draw the wall and the text.
     */
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.WHITE);
        g2d.drawString(timerString,10, 400);

        g2d.setColor(Color.WHITE);
        g2d.drawString(message, 10, 420);

        g2d.setColor(Color.WHITE);
        g2d.drawString(scoreCount, 510, 420);
        drawBall(getWall().ball, g2d);

        showScore(g2d);

        for (Brick b : getWall().getBricks())
            if (!b.isBroken())
                drawBrick(b, g2d);

        drawPlayer(getWall().getPlayer(), g2d);

        if (showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Clears screen of any graphic components.
     */
    private void clear(Graphics2D g2d) {
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(tmp);
    }

    /**
     * Method to draw and display the brick object.
     * @param brick Brick object to be drawn.
     */
    private void drawBrick(Brick brick, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());

        g2d.setColor(tmp);
    }

    /**
     * Method to draw and display the ball object.
     * @param ball Ball object to be drawn.
     */
    private void drawBall(Ball ball, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * Method to draw and display the player object.
     * @param p Player object to be drawn.
     */
    private void drawPlayer(Player p, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * Method to draw and display home menu by hiding the gameBoard.
     */
    private void drawMenu(Graphics2D g2d) {
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /**
     * Obscures the gameBoard by removing from view and disabling any user inputs to the gameBoard.
     * Called when a menu is being shown.
     */
    private void obscureGameBoard(Graphics2D g2d) {

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, DEF_WIDTH, DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * Method to draw and display the pause menu.
     * The pause menu allows user to continue the game, to restart the game, or to exit the game.
     */
    private void drawPauseMenu(Graphics2D g2d) {
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(getMenuFont());
        g2d.setColor(MENU_COLOR);

        if (strLen == 0) {
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = getMenuFont().getStringBounds(PAUSE, frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE, x, y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if (continueButtonRect == null) {
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = getMenuFont().getStringBounds(CONTINUE, frc).getBounds();
            continueButtonRect.setLocation(x, y - continueButtonRect.height);
        }

        g2d.drawString(CONTINUE, x, y);

        y *= 2;

        if (restartButtonRect == null) {
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x, y - restartButtonRect.height);
        }

        g2d.drawString(RESTART, x, y);

        y *= 3.0 / 2;

        if (exitButtonRect == null) {
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x, y - exitButtonRect.height);
        }

        g2d.drawString(EXIT, x, y);


        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * Unused in game but is required to be defined to implement keyboardListener.
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * Takes in user keyboard inputs when the key goes down.
     * A moves the player to the left.
     * D moves the player to the right.
     * Escape displays the pause menu.
     * Space freezes/pauses the game.
     * Shift + Alt + F1 displays the debug window.
     *
     * @param keyEvent Keyboard input.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A:
                getWall().getPlayer().movePlayerLeft();
                break;
            case KeyEvent.VK_D:
                getWall().getPlayer().movePLayerRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if (!showPauseMenu)
                    if (gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if (keyEvent.isAltDown() && keyEvent.isShiftDown())
                    getDebugConsole().setVisible(true);
            default:
                getWall().getPlayer().stop();
        }
    }

    /**
     * When key goes up, stops player movement.
     * @param keyEvent Keyboard input.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        getWall().getPlayer().stop();
    }

    /**
     * Takes in user mouse input when the mouse is both pressed and released.
     * Mouse input only looked at during pause menu.
     * When continue button is clicked, return to game.
     * When restart button is clicked, resets the level.
     * When exit button is clicked, exit game.
     *
     * @param mouseEvent Mouse input.
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (!showPauseMenu)
            return;
        if (continueButtonRect.contains(p)) {
            showPauseMenu = false;
            repaint();
        } else if (restartButtonRect.contains(p)) {
            message = "Restarting Game...";
            getWall().ballReset();
            getWall().wallReset();
            showPauseMenu = false;
            repaint();
        } else if (exitButtonRect.contains(p)) {
            System.exit(0);
        }

    }

    /**
     * Most MouseEvent methods are unused but must be defined in order to use MouseListener.
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * Switches the mouse cursor to a hand pointer when it is hovering over any clickable buttons in pause menu.
     * @param mouseEvent Mouse cursor.
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        } else {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Freezes the game when window is not focused on.
     */
    public void onLostFocus() {
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

    /**
     * Getter for wall object.
     *
     * @return Current wall object.
     */
    public Wall getWall() {
        return wall;
    }

    /**
     * Setter for wall object.
     * @param wall Wall object to be sent as new wall.
     */
    public void setWall(Wall wall) {
        this.wall = wall;
    }

    /**
     * Getter for menuFont.
     *
     * @return Current menuFont.
     */
    public Font getMenuFont() {
        return menuFont;
    }

    /**
     * Setter for menuFont.
     *
     * @param menuFont menuFont to be set as the new menuFont.
     */
    public void setMenuFont(Font menuFont) {
        this.menuFont = menuFont;
    }

    /**
     * Getter for debugConsole object.
     * @return Current debugConsole.
     */
    public DebugConsole getDebugConsole() {
        return debugConsole;
    }

    /**
     * Setter for debugConsole object.
     * @param debugConsole debugConsole to be set as the new debugConsole.
     */
    public void setDebugConsole(DebugConsole debugConsole) {
        this.debugConsole = debugConsole;
    }
}
