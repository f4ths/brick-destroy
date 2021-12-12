package com.BrickDestroy.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for Player class.
 * Public methods tested.
 */
class PlayerTest {
    private final Point PlayerPoint = new Point(300, 430);
    private final Rectangle Container = new Rectangle(0, 0, 600, 450);
    private Player testPlayer;

    /**
     * Generates a consistent player object before every test method.
     */
    @BeforeEach
    void setUp(){
        testPlayer = new Player(PlayerPoint, 150, 10, Container);
    }

    /**
     * Tests that the player has been moved by -5.
     */
    @Test
    void testMovePlayerLeft() {
        testPlayer.movePlayerLeft();
        assertEquals(-5, testPlayer.getMoveAmount());
    }

    /**
     * Tests that the player has been moved by 5.
     */
    @Test
    void testMovePlayerRight() {
        testPlayer.movePLayerRight();
        assertEquals(5, testPlayer.getMoveAmount());
    }

    /**
     * Tests that the player's speed has been set to 0.
     */
    @Test
    void testStopPlayer() {
        testPlayer.movePLayerRight();
        testPlayer.stop();
        assertEquals(0, testPlayer.getMoveAmount());
    }

    /**
     * Tests that the method returns true when the player impacts the ball.
     */
    @Test
    void testImpact() {
        final Ball b = new RubberBall(PlayerPoint);

        final boolean result = testPlayer.impact(b);

        assertTrue(result);
    }

    /**
     * Checks that the player has been moved to the correct location.
     */
    @Test
    void testMoveTo() {
        final Point p = new Point(20, 20);

        testPlayer.moveTo(p);
        assertEquals(new Point(20, 20), testPlayer.getPlayerPoint());
    }

}