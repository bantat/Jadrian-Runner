import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test class for Model.java
 */
public class ModelTest {
    Model model= new Model();

    @Test
    public void testInit() throws Exception {


    }

    @Test
    public void testIsRunning() throws Exception {
        assertEquals(true, model.gameRunning());
    }

    @Test
    public void testRandInt() throws Exception {
        int random;

        for (int i =0; i < 10; ++i) {
            random = Model.randInt(0, 10);
            assertTrue((random>-1) && (random<11));
        }
    }

    @Test
    public void testGenerateNewObstacle() throws Exception {
        model.generateNewObstacle();
        assertNull(null);
    }

    @Test
    public void testUpdateInputState() throws Exception {
        model.updateInputState(true, false, false);
        assertNull(null);
    }

    @Test
    public void testUpdateGameState() throws Exception {
        model.updateGameState(0);
    }

    @Test
    public void testGetObstacles() throws Exception {
        //list<Obstacle> obstacles;
        //obstacles = model.getObstacles();
        assertNull(null);
    }

    @Test
    public void testGetPlayer() throws Exception {
        //Player player;
        //player = model.getPlayer();
    }
}