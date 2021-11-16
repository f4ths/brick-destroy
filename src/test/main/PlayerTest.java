package main;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testMovePlayerLeft() {
        Player testPlayer = new Player(new Point(300, 430), 150, 10, new Rectangle(0, 0, 600, 450)) ;
        testPlayer.moveLeft();
        assertEquals(-5, testPlayer.getMoveAmount());
    }

    @Test
    void testMovePlayerRight() {
        Player testPlayer = new Player(new Point(300, 430), 150, 10, new Rectangle(0, 0, 600, 450)) ;
        testPlayer.moveRight();
        assertEquals(5, testPlayer.getMoveAmount());
    }

}