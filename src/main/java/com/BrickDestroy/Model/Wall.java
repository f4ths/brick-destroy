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


public class Wall {

    private Random rnd;

    private Rectangle area;

    private Brick[] bricks;
    public Ball ball;
    private Player player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    private int score;
    private int indexBrickBroken = 0;

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
            speedX = rnd.nextInt(5) - 2;
        } while (speedX == 0);
        do {
            speedY = -rnd.nextInt(3);
        } while (speedY == 0);

        ball.setSpeed(speedX, speedY);

        setPlayer(new Player((Point) ballPos.clone(), 150, 10, drawArea));

        area = drawArea;


    }


    private void makeBall(Point2D ballPos) {
        ball = new RubberBall(ballPos);
    }


    public void move() {
        getPlayer().move();
        ball.move();
    }

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

    private void addScore(){

        String checkBrickType = bricks[indexBrickBroken].getClass().getName();
        switch (checkBrickType) {
            case "com.BrickDestroy.Model.ClayBrick" -> score++;
            case "com.BrickDestroy.Model.CementBrick" -> score += 2;
            case "com.BrickDestroy.Model.SteelBrick" -> score += 3;
        }

        setScore(score);
    }

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

    private boolean impactBorder() {
        Point2D p = ball.getCenter();
        return ((p.getX() < area.getX()) || (p.getX() > (area.getX() + area.getWidth())));
    }

    public int getBrickCount() {
        return brickCount;
    }

    public int getBallCount() {
        return ballCount;
    }

    public boolean isBallLost() {
        return ballLost;
    }

    public void ballReset() {
        getPlayer().moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX, speedY;
        do {
            speedX = rnd.nextInt(5) - 2;
        } while (speedX == 0);
        do {
            speedY = -rnd.nextInt(3);
        } while (speedY == 0);

        ball.setSpeed(speedX, speedY);
        ballLost = false;
    }

    public void wallReset() {
        for (Brick b : getBricks())
            b.repair();
        brickCount = getBricks().length;
        ballCount = 3;
    }

    public boolean ballEnd() {
        return ballCount == 0;
    }

    public boolean isDone() {
        return brickCount == 0;
    }

    public void nextLevel() {
        setBricks(levels[level++]);
        this.brickCount = getBricks().length;
    }

    public boolean hasLevel() {
        return level < levels.length;
    }

    public void setBallXSpeed(int s) {
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s) {
        ball.setYSpeed(s);
    }

    public void resetBallCount() {
        ballCount = 3;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Brick[] getBricks() {
        return bricks;
    }

    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
