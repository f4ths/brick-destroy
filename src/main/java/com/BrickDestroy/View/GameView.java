
package com.BrickDestroy.View;

import com.BrickDestroy.Controller.DebugConsole;
import com.BrickDestroy.Controller.GameController;
import com.BrickDestroy.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;

/**
 * GameView has been separated from GameBoard to accomplish single responsibility and MVC.
 * This class handles the view methods from GameBoard.
 *
 * @author Fathan
 * @version 2.0
 * @see GameController
 */

public class GameView extends JComponent {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final Color MENU_COLOR = new Color(0, 255, 0);
    private static final int TEXT_SIZE = 30;

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = new Color(0xE60A0A0A, true);

    private Wall wall;

    private String message;
    private String scoreCount;
    private String timerString;

    private final CountdownTimer countdown = new CountdownTimer();

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;

    /**
     * The constructor for GameView creates a new wall object, sets the environment, and sets the wall to the next level.
     */
    public GameView(JFrame owner) {
        super();

        strLen = 0;
        setShowPauseMenu(false);
        setMenuFont(new Font("Monospaced", Font.PLAIN, TEXT_SIZE));

        setWall(new Wall(new Rectangle(0, 0, getDefWidth(), getDefHeight()), 30, 3, 6 * 0.5, new Point(300, 430)));

        setMessage(" ");
        setScoreCount(" ");
        setTimerString(" ");

        setDebugConsole(new DebugConsole(owner, wall, this));
        wall.nextLevel();

    }

    /**
     * Gets score from wall object and displays it.
     * Sets the color, font, and size of the text.
     */
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.WHITE);
        g2d.drawString(getTimerString(),10, 400);

        g2d.setColor(Color.WHITE);
        g2d.drawString(getMessage(), 10, 420);

        g2d.setColor(Color.WHITE);
        g2d.drawString(getScoreCount(), 510, 420);

        drawBall(getWall().ball, g2d);

        showScore(g2d);

        for (Brick b : getWall().getBricks())
            if (!b.isBroken())
                drawBrick(b, g2d);

        drawPlayer(getWall().getPlayer(), g2d);

        if (isShowPauseMenu())
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Gets score from wall object and displays it.
     * Sets the color, font, and size of the text.
     */
    private void showScore(Graphics g){
        int score = getWall().getScore();
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.PLAIN,14));
        g.drawString(String.valueOf(score),550,420);
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
        g2d.fillRect(0, 0, getDefWidth(), getDefHeight());

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

        if (getStrLen() == 0) {
            FontRenderContext frc = g2d.getFontRenderContext();
            setStrLen(getMenuFont().getStringBounds(PAUSE, frc).getBounds().width);
        }

        int x = (this.getWidth() - getStrLen()) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE, x, y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if (getContinueButtonRect() == null) {
            FontRenderContext frc = g2d.getFontRenderContext();
            setContinueButtonRect(getMenuFont().getStringBounds(CONTINUE, frc).getBounds());
            getContinueButtonRect().setLocation(x, y - getContinueButtonRect().height);
        }

        g2d.drawString(CONTINUE, x, y);

        y *= 2;

        if (getRestartButtonRect() == null) {
            setRestartButtonRect((Rectangle) getContinueButtonRect().clone());
            getRestartButtonRect().setLocation(x, y - getRestartButtonRect().height);
        }

        g2d.drawString(RESTART, x, y);

        y *= 3.0 / 2;

        if (getExitButtonRect() == null) {
            setExitButtonRect((Rectangle) getContinueButtonRect().clone());
            getExitButtonRect().setLocation(x, y - getExitButtonRect().height);
        }

        g2d.drawString(EXIT, x, y);


        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * Getter for menuFont
     */
    public Font getMenuFont() {
        return menuFont;
    }

    /**
     * Getter for exit button rectangle
     */
    public Rectangle getExitButtonRect() {
        return exitButtonRect;
    }

    /**
     * Setter for exit button rectangle
     */
    public void setExitButtonRect(Rectangle exitButtonRect) {
        this.exitButtonRect = exitButtonRect;
    }

    /**
     * Getter for continue button rectangle
     */
    public Rectangle getContinueButtonRect() {
        return continueButtonRect;
    }

    /**
     * Setter for continue button rectangle
     */
    public void setContinueButtonRect(Rectangle continueButtonRect) {
        this.continueButtonRect = continueButtonRect;
    }

    /**
     * Getter for restart button rectangle
     */
    public Rectangle getRestartButtonRect() {
        return restartButtonRect;
    }

    /**
     * Setter for restart button rectangle
     */
    public void setRestartButtonRect(Rectangle restartButtonRect) {
        this.restartButtonRect = restartButtonRect;
    }

    /**
     * Getter for strLen
     */
    public int getStrLen() {
        return strLen;
    }

    /**
     * Setter for strLen
     */
    public void setStrLen(int strLen) {
        this.strLen = strLen;
    }

    /**
     * Getter for message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Getter for scoreCount
     */
    public String getScoreCount() {
        return scoreCount;
    }

    /**
     * Setter for scoreCount
     */
    public void setScoreCount(String scoreCount) {
        this.scoreCount = scoreCount;
    }

    /**
     * Getter for timerString
     */
    public String getTimerString() {
        return timerString;
    }

    /**
     * Setter for timerString
     */
    public void setTimerString(String timerString) {
        this.timerString = timerString;
    }

    /**
     * Getter for Wall
     */
    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    /**
     * Setter for menuFont
     */
    public void setMenuFont(Font menuFont) {
        this.menuFont = menuFont;
    }

    /**
     * Getter for debugConsole
     * @return debugConsole object
     */
    public DebugConsole getDebugConsole() {
        return debugConsole;
    }

    /**
     * Setter for debugConsole object
     * @param debugConsole Object to be changed
     */
    public void setDebugConsole(DebugConsole debugConsole) {
        this.debugConsole = debugConsole;
    }

    /**
     * Getter for showPauseMenu
     * @return showPauseMenu
     */
    public boolean isShowPauseMenu() {
        return showPauseMenu;
    }

    /**
     * Setter for showPauseMenu
     * @param showPauseMenu Object To be changed
     */
    public void setShowPauseMenu(boolean showPauseMenu) {
        this.showPauseMenu = showPauseMenu;
    }

    /**
     * Getter for countdown timer
     * @return countdown timer
     */
    public CountdownTimer getCountdown() {
        return countdown;
    }

    /**
     * Getter for DEF_WIDTH
     * @return DEF_WIDTH constant
     */
    public static int getDefWidth() {
        return DEF_WIDTH;
    }

    /**
     * Getter for DEF_HEIGHT
     * @return DEF_HEIGHT constant
     */
    public static int getDefHeight() {
        return DEF_HEIGHT;
    }
}
