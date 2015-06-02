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

    private Model model;
    private View view;
    private Controller controller;
    private Stage gameWindow;

    /**
     * Method which is called for running the game on startup. Takes a Stage
     * object which is the graphics window containing view elements of the game.
     *
     * @param gameWindow
     * @throws Exception
     */
    @Override
    public void start(Stage gameWindow) throws Exception {

        this.gameWindow = gameWindow;

        // Instantiates MVC components
        model = new Model();
        controller = new Controller(model);
        view = new View(model, controller, gameWindow);

        // Loads main menu screen for game
        view.loadStartScreen();
    }

    /**
     * Method for running the class Main, calls launch using command line
     * arguments provided.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}