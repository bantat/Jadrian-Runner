import org.junit.Test;
import GameObjects.Player;

import static org.junit.Assert.*;

/**
 * Created by alexgriese on 6/7/15.
 */
public class ModelTest {
    private Model model;


    @Test
    public void testGameRunning() throws Exception {
        model = new Model();
        assertEquals(false, model.gameRunning());
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
    public void testRandIntTakesNoNegativeValues() throws Exception {
        model = new Model();
        int randomNegativeMin = model.randInt(-11,10);
        int randomNegativeMax = model.randInt(-2, -1);
        assertTrue(randomNegativeMin >= 0);
        assertEquals(-1,(randomNegativeMax));
    }

    @Test
    public void testGenerateNewObstacle() throws Exception {
        model = new Model();
        model.generateNewObstacle();
        assertTrue(model.getObstacles().size() != 0);
    }

    @Test(expected = Exception.class)
    public void testUpdateOffscreenObstaclesNull() throws Exception {
        model = new Model();
        model.updateOffscreenObstacles(null, 1);
    }

    @Test
    public void testGetObstacles() throws Exception {
        model = new Model();
        assertEquals(null, model.getObstacles());
    }

    @Test
    public void testGetPlayer() throws Exception {
        model = new Model();
        assertTrue(model.getPlayer() == null);
    }

    @Test
    public void testGetDistance() throws Exception {
        model = new Model();
        assertEquals(0, model.getDistance());
    }

    @Test
    public void testResetDistance() throws Exception {
        model = new Model();
        model.resetDistance();
        assertEquals(0, model.getDistance());
    }

}