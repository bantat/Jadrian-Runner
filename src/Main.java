import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The primary class and access point for Jadrian Runner.
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public class Main extends Application {

    /**
     * Method which is called for running the game on startup. Takes a Stage
     * object which is the graphics window containing view elements of the game.
     *
     * @param gameWindow The Stage in which the game will be held
     * @throws Exception
     */
    @Override
    public void start(Stage gameWindow) throws Exception {

        // Instantiates MVC components
        final Model model = new Model();
        final GameController gameController = new GameController(model);
        final View view = new View(model, gameController, gameWindow);

        // Call to newly constructed view to load starting screen
        view.loadStartScreen();
    }

    public static void main(String[] args) { launch(args); }
}