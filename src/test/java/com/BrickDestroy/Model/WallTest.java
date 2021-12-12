package com.BrickDestroy.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for wall.
 * Most public methods tested.
 */
class WallTest {

    private Wall testWall;
    private final Rectangle DrawArea = new Rectangle(0, 0, 600, 450);
    private final Point BallPos = new Point (300, 430);

    /**
     * Generates a wall object with the same value before every test method.
     */
    @BeforeEach
    void setUp() {
        testWall = new Wall(DrawArea, 30, 3, 6*0.5, BallPos);
    }

    /**
     * Tests that the ball's y speed has been set to the correct value.
     */
    @Test
    void testSetBallYSpeed() {
        testWall.setBallYSpeed(20);
        assertEquals(20, testWall.ball.getSpeedY());
    }

    /**
     * Tests that the ball's x speed has been set to the correct value
     */
    @Test
    void testSetBallXSpeed() {
        testWall.setBallXSpeed(20);
        assertEquals(20, testWall.ball.getSpeedX());
    }

    /**
     * Tests that the method returns true since wall exists.
     */
    @Test
    void testHasLevel() {
        assertTrue(testWall.hasLevel());
    }

    /**
     * Test that returns true.
     */
    @Test
    void testIsDone() {
        assertTrue(testWall.isDone());
    }

    /**
     * Checks that returns false since ball exists.
     */
    @Test
    void testBallEnd() {
        assertFalse(testWall.ballEnd());
    }

    /**
     * When wall is first made, player and ball impacts.
     * This test checks that the ball's movement is reversed.
     */
    @Test
    void whenBallImpactsPlayerReverseBallYSpeed() {
        int newSpeed = 2;
        testWall.ball.setYSpeed(newSpeed);
        testWall.findImpacts();
        assertEquals(-newSpeed, testWall.ball.getSpeedY());
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



