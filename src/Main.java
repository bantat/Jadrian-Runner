import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Model model;
    private View view;
    private Controller controller;
    private Stage gameWindow;

    @Override
    public void start(Stage gameWindow) throws Exception {

        //Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        this.gameWindow = gameWindow;

        model = new Model();
        controller = new Controller(model);
        view = new View(model, controller, gameWindow);

        view.loadStartScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}