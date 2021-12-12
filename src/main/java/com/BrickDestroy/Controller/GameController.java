
package com.BrickDestroy.Controller;

import com.BrickDestroy.Model.*;
import com.BrickDestroy.View.GameFrame;
import com.BrickDestroy.View.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameController extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private GameFrame owner;
    private Timer gameTimer;
    private Wall wall;
    private GameView gameView;

    public GameController(GameFrame owner, GameView gameView){

        this.gameView = gameView;
        this.owner = owner;
        setWall(new Wall(new Rectangle(0, 0, GameView.getDefWidth(), GameView.getDefHeight()), 30, 3, 6 * 0.5, new Point(300, 430)));
        this.initialize();
        this.setGameTimer();

    }

    private void setGameTimer() {
        gameTimer = new Timer(10, e -> {
            gameView.getWall().move();
            gameView.getWall().findImpacts();
            gameView.setTimerString("Remaining Time:" + gameView.getCountdown().getTimer());
            gameView.setScoreCount("Score: ");
            gameView.setMessage(String.format("Bricks: %d Balls %d", gameView.getWall().getBrickCount(), gameView.getWall().getBallCount()));

            gameView.getCountdown().startTimer();
            if(gameView.getCountdown().getTimer() == 0){
                gameView.getWall().wallReset();
                gameView.getWall().ballReset();
                gameView.getCountdown().resetTimer();
                gameTimer.stop();
                gameView.setMessage("Game Over");

            }

            if (gameView.getWall().isBallLost()) {
                gameView.getCountdown().stopTimer();
                if (gameView.getWall().ballEnd()) {
                    gameView.getWall().wallReset();
                    gameView.setMessage("Game Over");
                }
                gameView.getWall().ballReset();
                gameTimer.stop();

            } else if (gameView.getWall().isDone()) {
                if (gameView.getWall().hasLevel()) {
                    gameView.setMessage("Go to Next Level");
                    gameTimer.stop();
                    gameView.getWall().ballReset();
                    gameView.getWall().wallReset();
                    gameView.getWall().nextLevel();

                } else {
                    gameView.setMessage("ALL WALLS DESTROYED");
                    gameTimer.stop();
                }
            }

            gameView.repaint();
        });
    }

    private void initialize() {
        gameView.setPreferredSize(new Dimension(GameView.getDefWidth(), GameView.getDefHeight()));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        gameView.addMouseListener(this);
        gameView.addMouseMotionListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A:
                gameView.getWall().getPlayer().movePlayerLeft();
                break;
            case KeyEvent.VK_D:
                gameView.getWall().getPlayer().movePLayerRight();
                break;
            case KeyEvent.VK_ESCAPE:
                gameView.setShowPauseMenu(!gameView.isShowPauseMenu());
                gameView.repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if (!gameView.isShowPauseMenu())
                    if (gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if (keyEvent.isAltDown() && keyEvent.isShiftDown())
                    gameView.getDebugConsole().setVisible(true);
            default:
                gameView.getWall().getPlayer().stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameView.getWall().getPlayer().stop();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (!gameView.isShowPauseMenu())
            return;
        if (gameView.getContinueButtonRect().contains(p)) {
            gameView.setShowPauseMenu(false);
            gameView.repaint();
        } else if (gameView.getRestartButtonRect().contains(p)) {
            gameView.setMessage("Restarting Game...");
            gameView.getWall().ballReset();
            gameView.getWall().wallReset();
            gameView.setShowPauseMenu(false);
            gameView.repaint();
        } else if (gameView.getExitButtonRect().contains(p)) {
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
        if (gameView.getExitButtonRect() != null && gameView.isShowPauseMenu()) {
            if (gameView.getExitButtonRect().contains(p) || gameView.getContinueButtonRect().contains(p) || gameView.getRestartButtonRect().contains(p))
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
        gameView.repaint();
    }


    public void setWall(Wall wall) {
        this.wall = wall;
    }


}
