package GameObjects;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Obstacle
 */
public class ObstacleTest {

    @Test
    public void testGetObstacleType() throws Exception{
        Obstacle bush =  new Obstacle( 50,  // width
                                       50,  // height
                                       50,  // speed
                                       20,  // x
                                       20); // y
        Obstacle stick = new Obstacle( 10,  // width
                                      100,  // height
                                       50,  // speed
                                       20,  // x
                                       20); // y
        Obstacle log =   new Obstacle(100,  // width
                                       10,  // height
                                       50,  // speed
                                       20,  // x
                                       20); // y

        assertEquals(bush.getObstacleType(), "Bush");
        assertEquals(stick.getObstacleType(), "Stick");
        assertEquals(log.getObstacleType(), "Log");

    }

    @Test
    public void testGetObstacleTypes() throws Exception{
        String[] expectedTypes = {"Stick", "Log", "Bush"};
        String[] obstacleTypes = Obstacle.getObstacleTypes();

        assertArrayEquals(obstacleTypes, expectedTypes);
    }

    @Test
    public void testMinimumDimensions() throws Exception{
        Obstacle smallObstacle =   new Obstacle(-100,  // width
                                                 -10,  // height
                                                  50,  // speed
                                                  20,  // x
                                                  20); // y

        assertEquals(smallObstacle.getHeight(), 10);
        assertEquals(smallObstacle.getWidth(), 10);
    }

}