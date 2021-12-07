package com.BrickDestroy.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BrickTest {


    private Point testBrickPos = new Point(40,40);
    private Dimension size = new Dimension(20,10);
    private final Color border = Color.GRAY;
    private final Color inner = new Color(178, 34, 34).darker();

    private Brick testBrick;

    @BeforeEach
    void setUp() {
        testBrick = new Brick(testBrickPos, size, border, inner, 10) {
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

    @Test
    void testSetImpact() {
        assertFalse(testBrick.setImpact(testBrickPos, 1));
    }

    @Test
    void testGetBorderColor() {
        final Color expectedResult = Color.GRAY;

        final Color result = testBrick.getBorderColor();

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetInnerColor() {
        final Color expectedResult = new Color(178, 34, 34).darker();

        final Color result = testBrick.getInnerColor();

        assertEquals(expectedResult, result);
    }

    @Test
    void testFindImpactWhenBallHitsLeftSideOfBrick() {
        Ball ball = new RubberBall(new Point(35,40));

        int result = testBrick.findImpact(ball);

        assertEquals(Brick.LEFT_IMPACT, result);
    }

    @Test
    void testFindImpactWhenBallHitsRightSideOfBrick(){
        Ball ball = new RubberBall(new Point(55, 40));

        int result = testBrick.findImpact(ball);

        assertEquals(Brick.RIGHT_IMPACT, result);
    }

    @Test
    void testFindImpactWhenBallHitsTopOfBrick(){
        Ball ball = new RubberBall(new Point(45,35));

        int result = testBrick.findImpact(ball);

        assertEquals(Brick.UP_IMPACT, result);
    }

    @Test
    void testFindImpactWhenBallHitsBottomOfBrick(){
        Ball ball = new RubberBall(new Point(45, 50));

        int result = testBrick.findImpact(ball);

        assertEquals(Brick.DOWN_IMPACT, result);

    }

    @Test
    void testRepair() {
        testBrick.impact();
        testBrick.repair();

        assertEquals(10, testBrick.getStrength());
    }

    @Test
    void testImpactWhenNotAboutToBreak() {

        int expected = testBrick.getStrength() - 1;
        testBrick.impact();


        assertEquals(expected, testBrick.getStrength());
    }

    @Test
    void testImpactWhenAboutToBreak() {

        int expected = 0;
        testBrick.setStrength(1);
        testBrick.impact();

        assertEquals(expected, testBrick.getStrength());
    }

}
