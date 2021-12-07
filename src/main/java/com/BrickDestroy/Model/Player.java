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

import com.BrickDestroy.Model.Ball;

import java.awt.*;


public class Player {


    public static final Color BORDER_COLOR = Color.YELLOW.darker().darker();
    public static final Color INNER_COLOR = Color.YELLOW;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point ballPoint;

    private int moveAmount;
    private int min;
    private int max;


    public Player(Point ballPoint, int width, int height, Rectangle container) {
        this.setBallPoint(ballPoint);
        setMoveAmount(0);
        setPlayerFace(makeRectangle(width, height));
        setMin(container.x + (width / 2));
        setMax(getMin() + container.width - width);

    }

    private Rectangle makeRectangle(int width, int height) {
        Point rectanglePoint = new Point((int) (getBallPoint().getX() - (width / 2)), (int) getBallPoint().getY());
        return new Rectangle(rectanglePoint, new Dimension(width, height));
    }

    public boolean impact(Ball b) {
        return getPlayerFace().contains(b.getCenter()) && getPlayerFace().contains(b.down);
    }

    public void move() {
        double ballLocation = getBallPoint().getX() + moveAmount;
        boolean ballOutOfBounds = ballLocation < getMin() || ballLocation > getMax();

        if (ballOutOfBounds)
            return;

        getBallPoint().setLocation(ballLocation, getBallPoint().getY());
        setPlayerLocation();
    }

    private void setPlayerLocation() {
        getPlayerFace().setLocation(getBallPoint().x - (int) getPlayerFace().getWidth() / 2, getBallPoint().y);
    }

    public void movePlayerLeft() {
        setMoveAmount(-DEF_MOVE_AMOUNT);
    }

    public void movePLayerRight() {
        setMoveAmount(DEF_MOVE_AMOUNT);
    }

    public void stop() {
        setMoveAmount(0);
    }


    public void moveTo(Point p) {
        getBallPoint().setLocation(p);
        setPlayerLocation();
    }


    public void setPlayerFace(Rectangle playerFace) {
        this.playerFace = playerFace;
    }

    public void setBallPoint(Point ballPoint) {
        this.ballPoint = ballPoint;
    }

    public void setMoveAmount(int moveAmount) {
        this.moveAmount = moveAmount;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }


    public Rectangle getPlayerFace() {
        return playerFace;
    }

    public Point getBallPoint() {
        return ballPoint;
    }

    public int getMoveAmount() {
        return moveAmount;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }



}
