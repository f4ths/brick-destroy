package com.BrickDestroy.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    private Wall testWall;
    private final Rectangle DrawArea = new Rectangle(0, 0, 600, 450);
    private final Point BallPos = new Point (300, 430);

    @BeforeEach
    void setUp() {
        testWall = new Wall(DrawArea, 30, 3, 6/2, BallPos);
    }

    @Test
    void testSetBallYSpeed() {
        testWall.setBallYSpeed(20);
        assertEquals(20, testWall.ball.getSpeedY());
    }

    @Test
    void testSetBallXSpeed() {
        testWall.setBallXSpeed(20);
        assertEquals(20, testWall.ball.getSpeedX());
    }

    @Test
    void testHasLevel() {
        assertTrue(testWall.hasLevel());
    }

    @Test
    void testIsDone() {
        assertTrue(testWall.isDone());
    }

    @Test
    void testBallEnd() {
        assertFalse(testWall.ballEnd());
    }

    @Test
    void whenBallImpactsPlayerReverseBallYSpeed() {
        testWall.ball.setYSpeed(1);
        testWall.findImpacts();
        assertEquals(-1, testWall.ball.getSpeedY());
    }


/*
    @Test
    void whenBallImpactsBorderReverseBallXSpeed() {
        Wall testWall = makeTestWall();
        borderPoint = new Point (600, 460);
        testWall.ball.moveTo(borderPoint);
        testWall.ball.setXSpeed(1);
        Point2D p = testWall.ball.getPosition();
        if (((p.getX() < testWall.getArea().getX()) ||
                (p.getX() > (testWall.getArea().getX()
                        + testWall.getArea().getWidth())))){

            testWall.findImpacts();
            assertEquals(-1, testWall.ball.getSpeedX());
        }

    }

    @Test
    void whenBallImpactsWallBrickCountDecrease(){
        Wall testWall = makeTestWall();

        testWall.ball.moveTo(testWall.getLevels().);
        testWall.findImpacts();
        assertEquals(29, testWall.getBrickCount());
    }
*/

}



