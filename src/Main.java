import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Canvas canvas;
    GraphicsContext context;

    private Model model;
    private View view;
    private Controller controller;

    @Override
    public void start(Stage gameWindow) throws Exception {
        Group root = new Group();
        canvas = new Canvas(800,400);
        root.getChildren().add(canvas);
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        model = new Model();
        view = new View(canvas, model);
        gameWindow.setTitle("Hello World");
        gameWindow.setScene(new Scene(root, 300, 275));
        gameWindow.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}