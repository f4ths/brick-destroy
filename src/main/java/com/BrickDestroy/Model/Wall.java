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
package com.BrickDestroy.Model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This is the wall class. It contains the logic for the interaction between the player, the bricks, and the ball.
 *
 * @author Fathan
 * @version 2.0
 */
public class Wall {

    private final Random rnd;

    private final Rectangle area;

    private Brick[] bricks;
    public Ball ball;
    private Player player;

    private final Brick[][] levels;
    private int level;

    private final Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    private int score;
    private int indexBrickBroken = 0;

    /**
     * The constructor of the wall.
     * It sets the starting position of all objects, generates and initialises the level, and sets the starting speed of the ball based on pseudorandom values.
     *
     *
     * @param drawArea The area that the wall of bricks occupies. The bricks fill up this area uniformly.
     * @param brickCount The total number of bricks.
     * @param lineCount The number of rows of bricks. Used to calculate the width of the bricks.
     * @param brickDimensionRatio The ratio of the size of the bricks. Used to calculate the height of the bricks.
     * @param ballPos The position of the ball.
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) {

        this.startPoint = new Point(ballPos);

        levels = Levels.makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        makeBall(ballPos);
        int speedX, speedY;
        do {
            speedX = rnd.nextInt(7) - 2;
        } while (speedX == 0);
        do {
            speedY = -rnd.nextInt(5);
        } while (speedY == 0);

        ball.setSpeed(speedX, speedY);

        setPlayer(new Player((Point) ballPos.clone(), 150, 10, drawArea));

        area = drawArea;


    }

    /**
     * Generates a new RubberBall object in a certain position.
     * @param ballPos Takes in the point position of the ball.
     */
    private void makeBall(Point2D ballPos) {
        ball = new RubberBall(ballPos);
    }

    /**
     * Moved the player and the ball objects by calling the move() methods from the Player and Ball class.
     */
    public void move() {
        getPlayer().move();
        ball.move();
    }

    /**
     * Searches for any impacts between the objects.
     * If the ball comes into contact with the player object, then the ball's movement in the Y direction is reversed to bounce off the player.
     * If the ball comes into contact with the the brick arrays, then the brickCount is reduced by 1, and the score is added.
     * The ball is reversed in the impactWall() method since for every brick, the program checks for horizontal and vertical impacts.
     * If the ball comes into contact with the side borders, its movement in the X direction is reversed to bounce off the border.
     * If the ball comes into contact with the top border, its movement in the Y direction is reversed to bounce off the border.
     * If the ball comes into contact with the bottom border, ballCount is reduced by one and the ball is lost.
     */
    public void findImpacts() {
        if (getPlayer().impact(ball)) {
            ball.reverseY();
        } else if (impactWall()) {
            /*for efficiency reverse is done into method impactWall
             * because for every brick program checks for horizontal and vertical impacts
             */
            brickCount--;
            addScore();
        } else if (impactBorder()) {
            ball.reverseX();
        } else if (ball.getCenter().getY() < area.getY()) {
            ball.reverseY();
        } else if (ball.getCenter().getY() > area.getY() + area.getHeight()) {
            ballCount--;
            ballLost = true;
        }
    }

    /**
     * This method increases score based on the type of brick.
     */
    private void addScore(){

        String checkBrickType = bricks[indexBrickBroken].getClass().getName();
        switch (checkBrickType) {
            case "com.BrickDestroy.Model.ClayBrick" -> score++;
            case "com.BrickDestroy.Model.CementBrick" -> score += 2;
            case "com.BrickDestroy.Model.DiamondBrick" -> score += 3;
        }

        setScore(score);
    }

    /**
     * This method checks for horizontal and vertical impacts that the ball makes with the bricks.
     * A horizontal impact reverses the x direction of the ball movement.
     * A vertical impact reverses the y direction of the ball movement.
     * A crack is made on the brick depending on the direction.
     *
     * @return Directional value of the ball's impact, and reverses the ball accordingly. If no impact was found, returns false.
     */
    private boolean impactWall() {
        for (int x = 0; x < bricks.length; x++) {

            Brick b = bricks[x];

            switch (b.findImpact(ball)) {
                case Brick.UP_IMPACT -> {
                    ball.reverseY();
                    return b.setImpact(ball.down, Crack.UP);
                }
                case Brick.DOWN_IMPACT -> {
                    ball.reverseY();
                    return b.setImpact(ball.up, Crack.DOWN);
                }
                case Brick.LEFT_IMPACT -> {
                    ball.reverseX();
                    return b.setImpact(ball.right, Crack.RIGHT);
                }
                case Brick.RIGHT_IMPACT -> {
                    ball.reverseX();
                    return b.setImpact(ball.left, Crack.LEFT);
                }
            }
            indexBrickBroken = x;
            indexBrickBroken --;

            if(indexBrickBroken == -1)
                indexBrickBroken = 0;
        }
        return false;
    }

    /**
     * This method checks for if the ball is in contact with the border.
     *
     * @return A boolean value corresponding with if the ball's location overlaps with the border.
     */
    private boolean impactBorder() {
        Point2D p = ball.getCenter();
        return ((p.getX() < area.getX()) || (p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * Getter for the number of bricks.
     *
     * @return Integer value representing the number of bricks.
     */
    public int getBrickCount() {
        return brickCount;
    }

    /**
     * Getter for the number of balls.
     *
     * @return Integer value representing the number of balls.
     */
    public int getBallCount() {
        return ballCount;
    }

    /**
     * Getter for the lost status of the ball.
     *
     * @return Boolean value for if the ball is lost or not.
     */
    public boolean isBallLost() {
        return ballLost;
    }

    /**
     * This method resets the status of the ball.
     * It resets the ball location, speed, and ballLost value to its original value.
     */
    public void ballReset() {
        getPlayer().moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX, speedY;
        do {
            speedX = rnd.nextInt(7) - 2;
        } while (speedX == 0);
        do {
            speedY = -rnd.nextInt(5);
        } while (speedY == 0);

        ball.setSpeed(speedX, speedY);
        ballLost = false;
    }

    /**
     * This method resets the status of the wall of bricks and the ball count.
     * It resets the strength of the bricks via repair(), the number of bricks, and the number of balls.
     */
    public void wallReset() {
        for (Brick b : getBricks())
            b.repair();
        brickCount = getBricks().length;
        ballCount = 3;
    }

    /**
     * This method determines if there are any balls remaining.
     *
     * @return True is there are 0 balls remaining, and false if there are still balls remaining.
     */
    public boolean ballEnd() {
        return ballCount == 0;
    }

    /**
     * This method determines if there are any bricks remaining.
     *
     * @return True if there are 0 bricks remaining, and false if there are still bricks remaining.
     */
    public boolean isDone() {
        return brickCount == 0;
    }

    /**
     * This method sets the game to the next level.
     */
    public void nextLevel() {
        setBricks(levels[level++]);
        this.brickCount = getBricks().length;
    }

    /**
     * This method determines if there is a next level to go to.
     */
    public boolean hasLevel() {
        return level < levels.length;
    }

    /**
     * Setter for the ball's speed in the horizontal direction.
     * @param s Value of movement speed.
     */
    public void setBallXSpeed(int s) {
        ball.setXSpeed(s);
    }

    /**
     * Setter for the ball's speed in the vertical direction.
     * @param s Value of movement speed.
     */
    public void setBallYSpeed(int s) {
        ball.setYSpeed(s);
    }

    /**
     * Resets the ball count to its original value.
     */
    public void resetBallCount() {
        ballCount = 3;
    }

    /**
     * Getter for the player object.
     * @return Player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Setter for the player object.
     * @param player Takes in a player object.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Getter for an array of bricks
     * @return Bricks arrays
     */
    public Brick[] getBricks() {
        return bricks;
    }

    /**
     * Setter for an array of bricks.
     */
    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }

    /**
     * Getter for the score.
     * @return Score integer.
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for the score.
     * @param score Score integer.
     */
    public void setScore(int score) {
        this.score = score;
    }

    public void resetScore(){
        setScore(0);
    }
}
