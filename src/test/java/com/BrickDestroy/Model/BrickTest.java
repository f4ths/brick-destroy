package com.BrickDestroy.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for Brick class.
 * Public methods tested.
 */
class BrickTest {

    private final Point POS = new Point(40,40);
    private final Dimension SIZE = new Dimension(20,10);
    private final Color BORDER = Color.GRAY;
    private final Color INNER = new Color(178, 34, 34).darker();
    private final int STRENGTH = 20;

    private Brick testBrick;

    /**
     * Generates a brick object with the same values before every test method.
     */
    @BeforeEach
    void setUp() {
        testBrick = new Brick(POS, SIZE, BORDER, INNER, STRENGTH) {
            @Override
            public Shape makeBrickFace(Point pos, Dimension size) {
                return new Rectangle(pos, size);
            }

            @Override
            public Shape getBrick() {
                return testBrick.getBrickFace();
            }
        };
        testBrick.repair();
    }

    /**
     * Checks that setImpact returns false when there is no impact.
     */
    @Test
    void testSetImpact() {
        assertFalse(testBrick.setImpact(POS, 1));
    }

    /**
     * Tests that method returns the correct color.
     */
    @Test
    void testGetBorderColor() {
        final Color expected = Color.GRAY;

        final Color result = testBrick.getBorderColor();

        assertEquals(expected, result);
    }

    /**
     * Tests that method returns the correct color.
     */
    @Test
    void testGetInnerColor() {
        final Color expected = new Color(178, 34, 34).darker();

        final Color result = testBrick.getInnerColor();

        assertEquals(expected, result);
    }

    /**
     * Tests that when the ball impacts the left side of the brick, the correct value is returned.
     */
    @Test
    void findImpactWhenBallHitsLeftSideOfBrick() {
        Ball ball = new RubberBall(new Point(35,40));

        int result = testBrick.findImpact(ball);

        assertEquals(Brick.LEFT_IMPACT, result);
    }

    /**
     * Tests that when the ball impacts the right side of the brick, the correct value is returned.
     */
    @Test
    void findImpactWhenBallHitsRightSideOfBrick(){
        Ball ball = new RubberBall(new Point(55, 40));

        int result = testBrick.findImpact(ball);

        assertEquals(Brick.RIGHT_IMPACT, result);
    }

    /**
     * Tests that when the ball impacts the top side of the brick, the correct value is returned.
     */
    @Test
    void findImpactWhenBallHitsTopOfBrick(){
        Ball ball = new RubberBall(new Point(45,35));

        int result = testBrick.findImpact(ball);

        assertEquals(Brick.UP_IMPACT, result);
    }

    /**
     * Tests that when the ball impacts the bottom side of the brick, the correct value is returned.
     */
    @Test
    void findImpactWhenBallHitsBottomOfBrick(){
        Ball ball = new RubberBall(new Point(45, 50));

        int result = testBrick.findImpact(ball);

        assertEquals(Brick.DOWN_IMPACT, result);

    }

    /**
     * Impacts brick then repairs.
     * Tests that the brick's strength is returned to original value after repair.
     */
    @Test
    void testRepair() {
        testBrick.impact();
        testBrick.repair();

        assertEquals(STRENGTH, testBrick.getStrength());
    }

    /**
     * Tests that brick does not break when impact is made when strength of brick is greater than 1.
     */
    @Test
    void testImpactWhenNotBreak() {
        int expected = testBrick.getStrength() - 1;
        testBrick.impact();

        assertEquals(expected, testBrick.getStrength());
    }

    /**
     * Tests that brick's strength is 0 when impact is made when strength of brick is 1.
     */
    @Test
    void testImpactAndBreaks() {
        testBrick.setStrength(1);
        testBrick.impact();

        assertEquals(0, testBrick.getStrength());
    }

}
