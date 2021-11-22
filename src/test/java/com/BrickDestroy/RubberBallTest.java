package com.BrickDestroy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;


class RubberBallTest {

    private final Point2D center = new Point.Double(20,20);

    private RubberBall testRubberBall;

    @BeforeEach
    void setUp() {
        testRubberBall = new RubberBall(center);
    }

    @Test
    void testMakeBall() {

        final Shape result = testRubberBall.makeBall(center, 2, 2);

        assertEquals(new Ellipse2D.Double(19, 19, 2, 2), result);
    }
}
