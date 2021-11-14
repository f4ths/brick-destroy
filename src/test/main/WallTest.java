package main;

import main.Player;
import main.Wall;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    @Test
    void findImpactBetweenPlayerAndBall() {
        Wall testWall = new Wall(
                new Rectangle(1, 1, 1, 1),
                30,
                3,
                6/2,
                new Point(300, 430)) ;

        testWall.ball.setYSpeed(1);
        testWall.findImpacts();
        assertEquals(-1,testWall.ball.getSpeedY());
    }
}