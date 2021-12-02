package com.BrickDestroy;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
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

    public Ball(Point2D center, int radiusA, int radiusB, Color inner, Color border) {
        this.setCenter(center);

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        setPoints(radiusA, radiusB);

        ballFace = makeBall(center, radiusA, radiusB);
        this.setBorder(border);
        this.setInner(inner);
        setSpeed(0,0);
    }

    protected abstract Shape makeBall(Point2D center, int radiusA, int radiusB);

    public void move() {
        RectangularShape tmp = (RectangularShape) ballFace;
        getCenter().setLocation((getCenter().getX() + speedX), (getCenter().getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((getCenter().getX() - (w / 2)), (getCenter().getY() - (h / 2)), w, h);
        setPoints(w, h);

        ballFace = tmp;
    }

    public Shape getBallFace() {
        return ballFace;
    }

    public void moveTo(Point p) {
        getCenter().setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((getCenter().getX() - (w / 2)), (getCenter().getY() - (h / 2)), w, h);
        ballFace = tmp;
    }

    private void setPoints(double width, double height) {
        up.setLocation(getCenter().getX(), getCenter().getY() - (height / 2));
        down.setLocation(getCenter().getX(), getCenter().getY() + (height / 2));
        left.setLocation(getCenter().getX() - (width / 2), getCenter().getY());
        right.setLocation(getCenter().getX() + (width / 2), getCenter().getY());
    }

    public void setSpeed(int x, int y) {
        speedX = x;
        speedY = y;
    }

    public void setXSpeed(int s) {
        speedX = s;
    }

    public void setYSpeed(int s) {
        speedY = s;
    }

    public void reverseX() {
        speedX *= -1;
    }

    public void reverseY() {
        speedY *= -1;
    }

    public Color getBorderColor() {
        return getBorder();
    }

    public Color getInnerColor() {
        return getInner();
    }

    public Point2D getCenter() {
        return center;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }


    public void setCenter(Point2D center) {
        this.center = center;
    }

    public Color getBorder() {
        return border;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public Color getInner() {
        return inner;
    }

    public void setInner(Color inner) {
        this.inner = inner;
    }
}
