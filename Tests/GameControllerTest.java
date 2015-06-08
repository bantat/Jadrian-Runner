import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the GameController class
 */
public class GameControllerTest {
    private GameController controller;
    private Model model = new Model();


    // The following 3 tests, make sure the Controller class does not break
    // when null is passed as a parameter.

    @Test
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