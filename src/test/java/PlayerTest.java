import com.BrickDestroy.Player;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player generateTestPlayer() {
        return new Player(new Point(300, 430), 150, 10, new Rectangle(0, 0, 600, 450));
    }

    @Test
    void testMovePlayerLeft() {
        Player testPlayer = generateTestPlayer();
        testPlayer.moveLeft();
        assertEquals(-5, testPlayer.getMoveAmount());
    }

    @Test
    void testMovePlayerRight() {
        Player testPlayer = generateTestPlayer();
        testPlayer.moveRight();
        assertEquals(5, testPlayer.getMoveAmount());
    }

    @Test
    void testStopPlayer() {
        Player testPlayer = generateTestPlayer();
        testPlayer.moveRight();
        testPlayer.stop();
        assertEquals(0, testPlayer.getMoveAmount());
    }

}