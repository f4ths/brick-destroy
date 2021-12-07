package com.BrickDestroy.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BallTest {

    private final Point2D Center = new Point(20, 20);
    private final Color Inner = new Color(2, 2, 2, 2);
    private final Color Border = new Color(1, 1, 1, 1);

    private Ball testBall;


    @BeforeEach
    void setUp() {
        testBall = new Ball(Center, 10, 10, Inner, Border) {
            @Override
            protected Shape makeBall(Point2D center, int radiusA, int radiusB) {
                double x = center.getX() - (radiusA * 0.5);
                double y = center.getY() - (radiusB * 0.5);

                return new Ellipse2D.Double(x, y, radiusA, radiusB);
            }
        };
    }

    @Test
    void whenMoveAddSpeedXToCenterX() {
        testBall.moveTo(new Point (0,0));
        testBall.setSpeed(1,1);

        testBall.move();

        assertEquals(1, testBall.getCenter().getX());
    }

    @Test
    void whenMoveAddSpeedYToCenterY() {
        testBall.moveTo(new Point (0,0));
        testBall.setSpeed(1,1);

        testBall.move();

        assertEquals(1, testBall.getCenter().getY());
    }

    @Test
    void testSetSpeed() {

        testBall.setSpeed(5, 5);

        assertEquals(5, testBall.getSpeedX());
        assertEquals(5, testBall.getSpeedY());
    }

    @Test
    void testSetXSpeed() {

        testBall.setXSpeed(10);

        assertEquals(10, testBall.getSpeedX());
    }

    @Test
    void testSetYSpeed() {

        testBall.setYSpeed(10);

        assertEquals(10, testBall.getSpeedY());
    }

    @Test
    void testReverseX() {

        testBall.setSpeed(1, 0);
        testBall.reverseX();

        assertEquals(-1, testBall.getSpeedX());
    }

    @Test
    void testReverseY() {

        testBall.setSpeed(0, 1);
        testBall.reverseY();

        assertEquals(-1, testBall.getSpeedY());
    }

    @Test
    void testGetBorderColor() {

        final Color expectedResult = new Color(1, 1, 1, 1);
        testBall.setBorderColor(expectedResult);
        final Color result = testBall.getBorderColor();

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetInnerColor() {

        final Color expectedResult = new Color(2, 2, 2, 2);
        testBall.setInnerColor(expectedResult);
        final Color result = testBall.getInnerColor();

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetPosition() {

        final Point2D expectedResult = new Point(20, 20);
        final Point2D result = testBall.getCenter();

        assertEquals(expectedResult, result);
    }

    @Test
    void testMoveTo() {
        final Point p = new Point(30, 30);

        testBall.moveTo(p);

        assertEquals(30, Center.getX());
        assertEquals(30, Center.getY());
    }


}
