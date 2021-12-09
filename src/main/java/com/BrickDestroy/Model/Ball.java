package com.BrickDestroy.Model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Ball is an abstract class for a ball object that defines concrete methods.
 *
 * @author Fathan
 * @version 2.0
 */
abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;

    /**
     * This is the constructor for a ball object. It defines the position, size, and color of a ball.
     *
     * @param center Takes in a defined coordinate in 2d space (x, y).
     * @param width Takes in the radius of a ball.
     * @param height Takes in the radius of a ball.
     * @param inner Takes in the inner color of a ball.
     * @param border Takes in the outline color of a ball.
     */
    public Ball(Point2D center, int width, int height, Color inner, Color border) {
        this.setCenter(center);

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        setPoints(width, height);

        setBallFace(makeBall(center, width, height));
        this.setBorderColor(border);
        this.setInnerColor(inner);
        setSpeed(0,0);
    }

    /**
     * This abstract method is overridden in RubberBall
     *
     * @see RubberBall
     *
     */
    protected abstract Shape makeBall(Point2D center, int radiusA, int radiusB);

    /**
     * This method adds the speed of the ball to the location of the ball in order to move it.
     */
    public void move() {
        getCenter().setLocation((getCenter().getX() + speedX), (getCenter().getY() + speedY));
        updateLocation();
    }

    /**
     * This method moves a ball to a specified point in the wall.
     *
     * @param p The point where the ball is moved to.
     */
    public void moveTo(Point p) {
        getCenter().setLocation(p);
        updateLocation();
    }

    /**
     * This method updates a ball's location after its coordinates have been changed. It updates the space that the ball occupies in the new defined location.
     */
    private void updateLocation() {
        RectangularShape tmp = (RectangularShape) ballFace;

        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((getCenter().getX() - (w / 2)), (getCenter().getY() - (h / 2)), w, h);
        setPoints(w, h);
        setBallFace(tmp);
    }

    /**
     * This method defines a ball's directional points - up, down, left, right.
     *
     * @param width Used to calculate the ball's up and down location.
     * @param height Used to calculate the ball's left and right location.
     */
    private void setPoints(double width, double height) {
        up.setLocation(getCenter().getX(), getCenter().getY() - (height / 2));
        down.setLocation(getCenter().getX(), getCenter().getY() + (height / 2));
        left.setLocation(getCenter().getX() - (width / 2), getCenter().getY());
        right.setLocation(getCenter().getX() + (width / 2), getCenter().getY());
    }

    /**
     * Sets the speed value of a ball in both the X and Y coordinate.
     *
     * @param x Takes in horizontal speed
     * @param y Takes in vertical speed
     */
    public void setSpeed(int x, int y) {
        speedX = x;
        speedY = y;
    }

    /**
     * Reverses the Horizontal speed of the ball.
     */
    public void reverseX() {
        speedX *= -1;
    }

    /**
     * Reverses the vertical speed of the ball.
     */
    public void reverseY() {
        speedY *= -1;
    }

    /**
     * Sets the bounding space that the ball occupies.
     *
     * @param ballFace Takes in the space that the ball occupies.
     */
    public void setBallFace(Shape ballFace) {
        this.ballFace = ballFace;
    }

    /**
     * Sets the central coordinate of the ball.
     *
     * @param center A coordinate that defines the center point of the ball.
     */
    public void setCenter(Point2D center) {
        this.center = center;
    }

    /**
     * Sets the color of the border of the ball.
     *
     * @param border Takes in a color.
     */
    public void setBorderColor(Color border) {
        this.border = border;
    }

    /**
     * Sets the color of the body of the ball.
     *
     * @param inner Takes in a color.
     */
    public void setInnerColor(Color inner) {
        this.inner = inner;
    }

    /**
     * Sets the horizontal speed value of the ball.
     *
     * @param s Takes in an integer that will be set as the speed.
     */
    public void setXSpeed(int s) {
        speedX = s;
    }

    /**
     * Sets the vertical speed value of the ball.
     *
     * @param s Takes in an integer that will be set as the speed.
     */
    public void setYSpeed(int s) {
        speedY = s;
    }

    /**
     * @return The bounding space that the ball occupies.
     */
    public Shape getBallFace() {
        return ballFace;
    }

    /**
     * @return The central coordinate of the ball.
     */
    public Point2D getCenter() {
        return center;
    }

    /**
     * @return The border color of the ball.
     */
    public Color getBorderColor() {
        return border;
    }

    /**
     * @return The color of the body of the ball.
     */
    public Color getInnerColor() {
        return inner;
    }

    /**
     *
     * @return The horizontal speed of the ball.
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     *
     * @return The vertical speed of the ball.
     */
    public int getSpeedY() {
        return speedY;
    }
}
