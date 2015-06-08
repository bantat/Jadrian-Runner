package GameObjects;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Obstacle
 */
public class ObstacleTest {

    Obstacle obstacleBush = new Obstacle(50, 50, 50, 20, 20);
    Obstacle obstacleStick = new Obstacle(10, 100, 50, 20, 20);
    Obstacle obstacleLog = new Obstacle(100, 10, 50, 20, 20);
    String str;
    String[] strings;
    String[] expectedStrings= {"Stick", "Log", "Bush"};

    public void testGetObstacleType() throws Exception{
        str = obstacleBush.getObstacleType();
        assertEquals(str, "Bush");
        str = obstacleStick.getObstacleType();
        assertEquals(str, "Stick");
        str = obstacleLog.getObstacleType();
        assertEquals(str, "Log");

    }

    public void testGetObstacleTypes() throws Exception{
        strings= obstacleBush.getObstacleTypes();
        assertArrayEquals(strings, expectedStrings);
    }
    public void testMinimumDimensions() throws Exception{
        Obstacle smallObstacle= new Obstacle(-100, -10, 50, 20, 20);
        assertEquals(smallObstacle.getHeight(), 10);
        assertEquals(smallObstacle.getWidth(), 10);
    }

}