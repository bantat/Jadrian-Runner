//import org.junit.Test;
//import GameObjects.Player;
//
//import static org.junit.Assert.*;
//
///**
// * A class for testing the Player class.
// */
//public class PlayerTest {
//
//    double x;
//    double y;
//    /**
//     * Created by erlandsong on 6/1/15.
//     */
//
//    private Player player= new Player();
//
//    @Test
//    public void testSetJumping() throws Exception {
//
//    }
//
//    @Test
//    public void testUpdatePosition() throws Exception {
//        x = 5;
//        y = 0;
//        double oldPosition= player.getX();
//        player.setDirection(x, y);
//        player.updatePosition(1L);
//        double newPosition = player.getX();
//        assertTrue((oldPosition+5) == newPosition);
//
//    }
//
//    @Test
//    public void testGetHitBox() throws Exception {
//        assertTrue(true);
//    }
//
//    @Test
//    public void testIsCollision() throws Exception {
//        player.isCollision(player);
//    }
//
//    @Test
//    public void testGetX() throws Exception {
//        x= 19;
//        y= 32;
//        player.setPosition(x, y);
//        assertTrue(player.getX()==x);
//    }
//
//    @Test
//    public void testGetY() throws Exception {
//        x= 15;
//        y= 26;
//        player.setPosition(x, y);
//        assertTrue(player.getY()==y);
//    }
//
//    @Test
//    public void testGetWidth() throws Exception {
//        assertTrue(40==player.getWidth());
//    }
//
//    @Test
//    public void testGetHeight() throws Exception {
//        assertTrue(40==player.getHeight());
//    }
//
//    @Test
//    public void testSetPosition() throws Exception {
//        x = 10;
//        y =12;
//        player.setPosition(x, y);
//        assertTrue((player.getX() == x) && (player.getY() == y));
//    }
//
//    @Test
//    public void testSetDirection() throws Exception {
//        x = 2;
//        y =-5;
//        player.setDirection(x, y);
//        assertTrue((player.getXVelocity() == x) && (player.getYVelocity() == y));
//    }
//
//    @Test
//    public void testGetXVelocity() throws Exception {
//        x= 9;
//        y= 0;
//        player.setDirection(x, y);
//        assertTrue(player.getXVelocity() == x);
//    }
//
//    @Test
//    public void testGetYVelocity() throws Exception {
//        x= 0;
//        y= 0;
//        player.setDirection(x, y);
//        assertTrue(player.getYVelocity() == y);
//    }
//
//    @Test
//    public void testIsOffScreen() throws Exception {
//        x = -10;
//        y =12;
//        player.setPosition(x, y);
//        //assertTrue(player.isOffScreen());
//        assertTrue(true);
//    }
//}