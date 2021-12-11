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

/**
 * This is the player class that represents the paddle that the user is able to control.
 */
public class Player {


    public static final Color BORDER_COLOR = Color.YELLOW.darker().darker();
    public static final Color INNER_COLOR = Color.YELLOW;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point playerPoint;

    private int moveAmount;
    private int min;
    private int max;

    /**
     * This is the constructor of Player. It sets the width and height of the paddle, and the points that represent the area of the paddle.
     * It sets the move amount of the player to 0.
     *
     * @param playerPoint The point coordinate where the point is.
     * @param width The width of the paddle.
     * @param height The height of the paddle.
     * @param container The area that the paddle occupies.
     */
    public Player(Point playerPoint, int width, int height, Rectangle container) {
        this.setBallPoint(playerPoint);
        setMoveAmount(0);
        setPlayerFace(makeRectangle(width, height));
        setMin(container.x + (width / 2));
        setMax(getMin() + container.width - width);

    }

    /**
     * This method sets up the area that the player occupies.
     * The area is determined by the height and width of the player.
     *
     * @return A rectangle object that represents the area that the player occupies.
     */
    private Rectangle makeRectangle(int width, int height) {
        Point rectanglePoint = new Point((int) (getPlayerPoint().getX() - (width / 2)), (int) getPlayerPoint().getY());
        return new Rectangle(rectanglePoint, new Dimension(width, height));
    }

    /**
     * This method determines whether the ball impacts the player or not.
     *
     * @param b A ball object to be looked at.
     * @return A boolean value that is true only if the center and down points of the ball occupies a coordinate occupied by the player.
     */
    public boolean impact(Ball b) {
        return getPlayerFace().contains(b.getCenter()) && getPlayerFace().contains(b.down);
    }

    /**
     * This method moves the player by updating its coordinate location.
     * It adds the moveAmount to its coordinate.
     * If the ball is out of bounds, it resets the position of player to the original position.
     * setPlayerLocation() is called to update the player's position.
     */
    public void move() {
        double ballLocation = getPlayerPoint().getX() + moveAmount;
        boolean ballOutOfBounds = ballLocation < getMin() || ballLocation > getMax();

        if (ballOutOfBounds)
            return;

        getPlayerPoint().setLocation(ballLocation, getPlayerPoint().getY());
        setPlayerLocation();
    }
/**
 * This method sets the location of the player.
 */
    private void setPlayerLocation() {
        getPlayerFace().setLocation(getPlayerPoint().x - (int) getPlayerFace().getWidth() / 2, getPlayerPoint().y);
    }

    /**
     * This method sets the player's move amount by a constant defined amount in the negative x direction.
     */
    public void movePlayerLeft() {
        setMoveAmount(-DEF_MOVE_AMOUNT);
    }

    /**
     * This method sets the player's move amount by a constant defined amount in the positive x direction.
     */
    public void movePLayerRight() {
        setMoveAmount(DEF_MOVE_AMOUNT);
    }

    /**
     * This method sets the player's movement to 0.
     */
    public void stop() {
        setMoveAmount(0);
    }

    /**
     * This method moves the player to a specific location on the game board.
     * @param p The location where the player will be moved to.
     */
    public void moveTo(Point p) {
        getPlayerPoint().setLocation(p);
        setPlayerLocation();
    }

    /**
     * This method sets the area that the player occupies.
     * @param playerFace The shape that stores coordinate points that serves as the boundary of the area.
     */
    public void setPlayerFace(Rectangle playerFace) {
        this.playerFace = playerFace;
    }

    /**
     * This method sets the location of the ball.
     */
    public void setBallPoint(Point ballPoint) {
        this.playerPoint = ballPoint;
    }

    /**
     * This method is the setter for moveAmount, which is used to determine how much the player moves.
     */
    public void setMoveAmount(int moveAmount) {
        this.moveAmount = moveAmount;
    }

    /**
     * This method is the setter for min.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method is the setter for max.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method is the getter for playerFace.
     * @return The area that the player occupies.
     */
    public Rectangle getPlayerFace() {
        return playerFace;
    }

    /**
     * This method is the getter for playerPoint.
     * @return The coordinate position of the player.
     */
    public Point getPlayerPoint() {
        return playerPoint;
    }

    /**
     * This method is the getter for moveAmount.
     * @return The amount that the player moves.
     */
    public int getMoveAmount() {
        return moveAmount;
    }

    /**
     * This method is the getter for min.
     * @return Minimum.
     */
    public int getMin() {
        return min;
    }

    /**
     * This method is the getter for max.
     * @return Maximum.
     */
    public int getMax() {
        return max;
    }



}
