/*
package com.BrickDestroy.View;

import com.BrickDestroy.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;

public class GameBoardView extends JComponent {

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

    private boolean showPauseMenu;

    public void setMenuFont(Font menuFont) {
        this.menuFont = menuFont;
    }

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private GameController gameController;

    public GameBoardView(Wall wall) {
        this.wall = wall;
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.WHITE);
        g2d.drawString(getTimerString(),10, 400);

        g2d.setColor(Color.WHITE);
        g2d.drawString(getMessage(), 10, 420);

        g2d.setColor(Color.WHITE);
        g2d.drawString(getScoreCount(), 510, 420);
        drawBall(gameController.getWall().ball, g2d);

        showScore(g2d);

        for (Brick b : gameController.getWall().getBricks())
            if (!b.isBroken())
                drawBrick(b, g2d);

        drawPlayer(gameController.getWall().getPlayer(), g2d);

        if (showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void showScore(Graphics g){
        int score = gameController.getWall().getScore();
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.PLAIN,14));
        g.drawString(String.valueOf(score),550,420);
    }

    private void clear(Graphics2D g2d) {
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0, 0, gameController.getWidth(), gameController.getHeight());
        g2d.setColor(tmp);
    }

    private void drawBrick(Brick brick, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPlayer(Player p, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawMenu(Graphics2D g2d) {
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

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

    private void drawPauseMenu(Graphics2D g2d) {
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(getMenuFont());
        g2d.setColor(MENU_COLOR);

        if (getStrLen() == 0) {
            FontRenderContext frc = g2d.getFontRenderContext();
            setStrLen(getMenuFont().getStringBounds(PAUSE, frc).getBounds().width);
        }

        int x = (this.gameController.getWidth() - getStrLen()) / 2;
        int y = this.gameController.getHeight() / 10;

        g2d.drawString(PAUSE, x, y);

        x = this.gameController.getWidth() / 8;
        y = this.gameController.getHeight() / 4;


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

    public Font getMenuFont() {
        return menuFont;
    }


    public Rectangle getExitButtonRect() {
        return exitButtonRect;
    }

    public void setExitButtonRect(Rectangle exitButtonRect) {
        this.exitButtonRect = exitButtonRect;
    }

    public Rectangle getContinueButtonRect() {
        return continueButtonRect;
    }

    public void setContinueButtonRect(Rectangle continueButtonRect) {
        this.continueButtonRect = continueButtonRect;
    }

    public Rectangle getRestartButtonRect() {
        return restartButtonRect;
    }

    public void setRestartButtonRect(Rectangle restartButtonRect) {
        this.restartButtonRect = restartButtonRect;
    }

    public int getStrLen() {
        return strLen;
    }

    public void setStrLen(int strLen) {
        this.strLen = strLen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(String scoreCount) {
        this.scoreCount = scoreCount;
    }

    public String getTimerString() {
        return timerString;
    }

    public void setTimerString(String timerString) {
        this.timerString = timerString;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }
}
 */