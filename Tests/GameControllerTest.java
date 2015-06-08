import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the GameController class
 */
public class GameControllerTest {
    private GameController controller;
    private Model model = new Model();

    @Test (expected = Exception.class)
    public void testConstructor() throws Exception {
        controller = new GameController(null);
    }

    @Test
    public void testKeyPressed() throws Exception {
        controller = new GameController(model);
        controller.keyPressed(null);
    }

    @Test
    public void testKeyReleased() throws Exception {
        controller = new GameController(model);
        controller.keyPressed(null);
    }
}