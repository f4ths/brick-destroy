package com.BrickDestroy.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Junit test for Ball class.
 * Public methods tested.
 */
class BallTest {

    private final Point2D CENTER = new Point(20, 20);
    private final Color INNER = new Color(2, 2, 2, 2);
    private final Color BORDER = new Color(1, 1, 1, 1);
    private final int WIDTH = 5;
    private final int HEIGHT = 5;

    private Ball testBall;

    /**
     * Generates a ball object with the same values before every test method.
     */
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

    /**
     * Tests that when move() is called, the speed of the ball is added to the location of the ball.
     * Asserts for both x and y in the same test for efficiency.
     */
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

    /**
     * Tests that the correct speed is set to x and y values.
     */
    @Test
    void testSetSpeed() {
        int x = 23;
        int y = 20;
        testBall.setSpeed(x, y);

        assertEquals(x, testBall.getSpeedX());
        assertEquals(y, testBall.getSpeedY());
    }

    /**
     * Tests that correct x value is set.
     */
    @Test
    void testSetXSpeed() {
        int x = 9;
        testBall.setXSpeed(x);

        assertEquals(x, testBall.getSpeedX());
    }

    /**
     * Tests that correct y value is set.
     */
    @Test
    void testSetYSpeed() {
        int y = 8;
        testBall.setYSpeed(y);

        assertEquals(y, testBall.getSpeedY());
    }

    /**
     * Tests that x value is multiplied by -1.
     */
    @Test
    void testReverseX() {
        int x = 22;
        testBall.setXSpeed(x);

        testBall.reverseX();

        assertEquals(-x, testBall.getSpeedX());
    }

    /**
     * Tests that y value is multiplied by -1.
     */
    @Test
    void testReverseY() {
        int y = 2;
        testBall.setYSpeed(y);

        testBall.reverseY();

        assertEquals(-y, testBall.getSpeedY());
    }

    /**
     * Tests that the correct border color has been set.
     */
    @Test
    void testSetBorderColor() {

        final Color expected = new Color(1, 1, 1, 1);
        testBall.setBorderColor(expected);
        final Color result = testBall.getBorderColor();

        assertEquals(expected, result);
    }

    /**
     * Tests that the correct inner color has been set
     */
    @Test
    void testSetInnerColor() {

        final Color expected = new Color(2, 2, 2, 2);
        testBall.setInnerColor(expected);
        final Color result = testBall.getInnerColor();

        assertEquals(expected, result);
    }

    /**
     * Tests that correct coordinate has been returned.
     */
    @Test
    void testGetCenter() {

        final Point2D result = testBall.getCenter();

        assertEquals(CENTER, result);
    }

    /**
     * Tests that the correct coordinate has been set.
     */
    @Test
    void testSetCenter() {
        final Point2D expected = new Point(10,20);
        testBall.setCenter(new Point(10, 20));
        final Point2D result = testBall.getCenter();

        assertEquals(expected, result);
    }

    /**
     * Tests that the ball has been set to a new location.
     */
    @Test
    void testMoveTo() {
        final Point p = new Point(30, 30);

        testBall.moveTo(p);

        assertEquals(30, CENTER.getX());
        assertEquals(30, CENTER.getY());
    }


}
