package GameObjects;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class for testing the Player class.
 */
public class PlayerTest {

    private Player player= new Player();

    @Test
    public void testSetJumping() throws Exception {

    }

    @Test
    public void testUpdatePosition() throws Exception {
        double x = 5;
        double y = 0;
        double oldPosition= player.getX();

        player.setDirection(x, y);
        player.updatePosition(1L);
        double newPosition = player.getX();

        assertTrue(newPosition == (oldPosition + 5));

    }

    @Test
    public void testGetHitBox() throws Exception {
        assertTrue(true);
    }

    @Test
    public void testIsCollision() throws Exception {
        player.isCollision(player);
    }

    @Test
    public void testGetX() throws Exception {
        double x = 19;
        double y = 32;
        player.setPosition(x, y);

        assertTrue(player.getX() == x);
    }

    @Test
    public void testGetY() throws Exception {
        double x = 15;
        double y = 26;
        player.setPosition(x, y);

        assertTrue(player.getY() == y);
    }

    @Test
    public void testGetWidth() throws Exception {
        assertTrue(player.getWidth() == 40);
    }

    @Test
    public void testGetHeight() throws Exception {
        assertTrue(player.getHeight() == 40);
    }

    @Test
    public void testSetPosition() throws Exception {
        double x = 10;
        double y = 12;
        player.setPosition(x, y);

        assertTrue((player.getX() == x)
                && (player.getY() == y));
    }

    @Test
    public void testSetDirection() throws Exception {
        double x = 2;
        double y = -5;
        player.setDirection(x, y);

        assertTrue((player.getXVelocity() == x)
                && (player.getYVelocity() == y));
    }

    @Test
    public void testGetXVelocity() throws Exception {
        double x = 9;
        double y = 0;
        player.setDirection(x, y);

        assertTrue(player.getXVelocity() == x);
    }

    @Test
    public void testGetYVelocity() throws Exception {
        double x = 0;
        double y = 0;
        player.setDirection(x, y);

        assertTrue(player.getYVelocity() == y);
    }
}