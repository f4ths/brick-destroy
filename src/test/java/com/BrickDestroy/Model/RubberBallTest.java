package com.BrickDestroy.Model;

import com.BrickDestroy.Model.RubberBall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Junit test for RubberBall. Only one method could be tested
 */
class RubberBallTest {

    private final Point2D center = new Point.Double(20,20);

    private RubberBall testRubberBall;

    @BeforeEach
    void setUp() {
        testRubberBall = new RubberBall(center);
    }

    /**
     * Asserts that this method returns an Ellipse2D object in location (19, 19) and has a width and height of 2.
     */
    @Test
    void testMakeBall() {

        final Shape result = testRubberBall.makeBall(center, 2, 2);

        assertEquals(new Ellipse2D.Double(19, 19, 2, 2), result);
    }
}
