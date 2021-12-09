package com.BrickDestroy.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BallTest {

    private final Point2D CENTER = new Point(20, 20);
    private final Color INNER = new Color(2, 2, 2, 2);
    private final Color BORDER = new Color(1, 1, 1, 1);
    private final int WIDTH = 5;
    private final int HEIGHT = 5;

    private Ball testBall;


    @BeforeEach
    void setUp() {
        testBall = new Ball(CENTER, WIDTH, HEIGHT, INNER, BORDER) {
            @Override
            protected Shape makeBall(Point2D center, int radiusA, int radiusB) {
                double x = center.getX() - (radiusA * 0.5);
                double y = center.getY() - (radiusB * 0.5);

                return new Ellipse2D.Double(x, y, radiusA, radiusB);
            }
        };
    }

    @Test
    void whenMoveAddSpeedToCenter() {
        testBall.setCenter(new Point (0,0));
        testBall.setSpeed(1,23);

        double x = testBall.getCenter().getX() + testBall.getSpeedX();
        double y = testBall.getCenter().getY() + testBall.getSpeedY();

        testBall.move();

        assertEquals(x, testBall.getCenter().getX());
        assertEquals(y, testBall.getCenter().getY());
    }

    @Test
    void testSetSpeed() {
        int x = 23;
        int y = 20;
        testBall.setSpeed(x, y);

        assertEquals(x, testBall.getSpeedX());
        assertEquals(y, testBall.getSpeedY());
    }

    @Test
    void testSetXSpeed() {
        int x = 9;
        testBall.setXSpeed(x);

        assertEquals(x, testBall.getSpeedX());
    }

    @Test
    void testSetYSpeed() {
        int y = 8;
        testBall.setYSpeed(y);

        assertEquals(y, testBall.getSpeedY());
    }

    @Test
    void testReverse() {
        int x = 22;
        testBall.setXSpeed(x);

        testBall.reverseX();

        assertEquals(-x, testBall.getSpeedX());
    }

    @Test
    void testReverseY() {
        int y = 2;
        testBall.setYSpeed(y);

        testBall.reverseY();

        assertEquals(-y, testBall.getSpeedY());
    }

    @Test
    void testSetBorderColor() {

        final Color expected = new Color(1, 1, 1, 1);
        testBall.setBorderColor(expected);
        final Color result = testBall.getBorderColor();

        assertEquals(expected, result);
    }

    @Test
    void testSetInnerColor() {

        final Color expected = new Color(2, 2, 2, 2);
        testBall.setInnerColor(expected);
        final Color result = testBall.getInnerColor();

        assertEquals(expected, result);
    }

    @Test
    void testGetCenter() {

        final Point2D result = testBall.getCenter();

        assertEquals(CENTER, result);
    }

    @Test
    void testSetCenter() {
        final Point2D expected = new Point(10,20);
        testBall.setCenter(new Point(10, 20));
        final Point2D result = testBall.getCenter();

        assertEquals(expected, result);
    }

    @Test
    void testMoveTo() {
        final Point p = new Point(30, 30);

        testBall.moveTo(p);

        assertEquals(30, CENTER.getX());
        assertEquals(30, CENTER.getY());
    }


}
