package com.BrickDestroy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private final Point BallPoint = new Point(300, 430);
    private final Rectangle Container = new Rectangle(0, 0, 600, 450);
    private Player testPlayer;

    @BeforeEach
    void setUp(){
        testPlayer = new Player(BallPoint, 150, 10, Container);
    }

    @Test
    void testMovePlayerLeft() {
        testPlayer.moveLeft();
        assertEquals(-5, testPlayer.getMoveAmount());
    }

    @Test
    void testMovePlayerRight() {
        testPlayer.moveRight();
        assertEquals(5, testPlayer.getMoveAmount());
    }

    @Test
    void testStopPlayer() {
        testPlayer.moveRight();
        testPlayer.stop();
        assertEquals(0, testPlayer.getMoveAmount());
    }

    @Test
    void testImpact() {
        final Ball b = new RubberBall(BallPoint);

        final boolean result = testPlayer.impact(b);

        assertTrue(result);
    }

    @Test
    void testMoveTo() {
        final Point p = new Point(20, 20);

        testPlayer.moveTo(p);
        assertEquals(new Point(20, 20), p);
    }

}