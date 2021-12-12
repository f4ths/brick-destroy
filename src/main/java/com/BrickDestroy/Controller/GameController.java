
package com.BrickDestroy.Controller;

import com.BrickDestroy.Model.*;
import com.BrickDestroy.View.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GameController has been separated from GameBoard to accomplish single responsibility and MVC.
 * This class handles user input and the GameBoard logic.
 *
 * @author Fathan
 * @version 2.0
 * @see GameView
 */
public class GameController extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private Timer gameTimer;
    private GameView gameView;

    /**
     * This is the constructor for gameController.
     * gameView gets passed in order to allow it to be connected to the game logic.
     * Initialises the game to allow user input to be made.
     * Starts the game timer.
     *
     * @param gameView Passed to connect the view of the game to the logic.
     */
    public GameController(GameView gameView){

        this.gameView = gameView;
        this.initialize();
        this.setGameTimer();

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
                gameView.getWall().resetScore();

            }

            if (gameView.getWall().isBallLost()) {
                gameView.getCountdown().stopTimer();
                if (gameView.getWall().ballEnd()) {
                    gameView.getWall().wallReset();
                    gameView.setMessage("Game Over");
                    gameView.getWall().resetScore();
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
                    gameView.getWall().resetScore();

                } else {
                    gameView.setMessage("ALL WALLS DESTROYED");
                    gameTimer.stop();
                }
            }

            gameView.repaint();
        });
    }

    /**
     * Initialises gameBoard
     * Set dimension, request for window focus, enable user input.
     */
    private void initialize() {
        gameView.setPreferredSize(new Dimension(GameView.getDefWidth(), GameView.getDefHeight()));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        gameView.addMouseListener(this);
        gameView.addMouseMotionListener(this);
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
    /**
     * When key goes up, stops player movement.
     * @param keyEvent Keyboard input.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameView.getWall().getPlayer().stop();
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
        if (gameView.getExitButtonRect() != null && gameView.isShowPauseMenu()) {
            if (gameView.getExitButtonRect().contains(p) || gameView.getContinueButtonRect().contains(p) || gameView.getRestartButtonRect().contains(p))
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
        gameView.setMessage("Focus Lost");
        gameView.repaint();
    }


}
