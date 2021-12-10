/*
package com.BrickDestroy.Controller;

import com.BrickDestroy.Model.*;
import com.BrickDestroy.View.GameBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameController extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final int TEXT_SIZE = 30;

    private Timer gameTimer;
    private final CountdownTimer timer = new CountdownTimer();

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;

    private Wall wall;

    private final GameBoardView gameView;

    public GameController(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;
        setMenuFont(new Font("Monospaced", Font.PLAIN, TEXT_SIZE));

        this.initialize();
        setWall(new Wall(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6 * 0.5, new Point(300, 430)));

        gameView = new GameBoardView(wall);
        this.add(gameView);

        gameView.setMessage(" ");
        gameView.setScoreCount(" ");
        gameView.setTimerString(" ");

        setDebugConsole(new DebugConsole(owner, getWall(), this));
        //initialize the first level
        getWall().nextLevel();

        setGameTimer();
    }


    private void setGameTimer() {
        gameTimer = new Timer(10, e -> {
            getWall().move();
            getWall().findImpacts();
            gameView.setTimerString("Remaining Time:" + timer.getTimer());
            gameView.setScoreCount("Score: ");
            gameView.setMessage(String.format("Bricks: %d Balls %d", getWall().getBrickCount(), getWall().getBallCount()));

            timer.startTimer();
            if(timer.getTimer() == 0){
                wall.wallReset();
                wall.ballReset();
                timer.resetTimer();
                gameTimer.stop();
                gameView.setMessage("Game Over");

            }

            if (getWall().isBallLost()) {
                timer.stopTimer();
                if (getWall().ballEnd()) {
                    getWall().wallReset();
                    gameView.setMessage("Game Over");
                }
                getWall().ballReset();
                gameTimer.stop();

            } else if (getWall().isDone()) {
                if (getWall().hasLevel()) {
                    gameView.setMessage("Go to Next Level");
                    gameTimer.stop();
                    getWall().ballReset();
                    getWall().wallReset();
                    getWall().nextLevel();

                } else {
                    gameView.setMessage("ALL WALLS DESTROYED");
                    gameTimer.stop();
                }
            }

            repaint();
        });
    }

    private void initialize() {
        this.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

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

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        getWall().getPlayer().stop();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (!showPauseMenu)
            return;
        if (continueButtonRect.contains(p)) {
            showPauseMenu = false;
            repaint();
        } else if (restartButtonRect.contains(p)) {
            gameView.setMessage("Restarting Game...");
            getWall().ballReset();
            getWall().wallReset();
            showPauseMenu = false;
            repaint();
        } else if (exitButtonRect.contains(p)) {
            System.exit(0);
        }

    }

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

    public void onLostFocus() {
        gameTimer.stop();
        gameView.setMessage("Focus Lost");
        repaint();
    }

    public DebugConsole getDebugConsole() {
        return debugConsole;
    }

    public void setDebugConsole(DebugConsole debugConsole) {
        this.debugConsole = debugConsole;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public void setMenuFont(Font menuFont) {
        this.menuFont = menuFont;
    }

}
*/
