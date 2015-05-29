import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by torebanta on 5/27/15.
 */

public class View {
    private Model model;
    private ArrayList<Canvas> gameCanvasses;
    private Canvas mainCanvas;
    private Canvas backgroundCanvas;
    private Stage gameWindow;

    /**
     * Stores references to canvas and model objects for the game, and creates a
     * two dimensional context for drawing game elements on the canvas.
     *
     * @param model
     */
    public View(Model model, Stage gameWindow) {
        this.model = model;
        this.gameWindow = gameWindow;
    }

    public void loadStartScreen() {
        gameWindow.setTitle("Jadrian Runner");
        gameWindow.setScene(loadGameScene());
        gameWindow.show();
    }

    public void drawGame() {
        //...
    }

    public Scene loadMainScene() {
        Scene mainScene = null;

        return mainScene;
    }

    public Scene loadGameScene() {

        generateBackgroundCanvas();
        generateGameCanvas();

        gameCanvasses = new ArrayList<Canvas>();

        gameCanvasses.add(mainCanvas);
        gameCanvasses.add(backgroundCanvas);

        drawGame();

        Scene gameScene;

        Group root = new Group();
        root.getChildren().add(backgroundCanvas);
        root.getChildren().add(mainCanvas);
        gameScene = new Scene(root, 800, 600);

        return gameScene;
    }

    public void generateGameCanvas() {
        mainCanvas = new Canvas(800,600);
        GraphicsContext context = mainCanvas.getGraphicsContext2D();
        context.fillOval(10,10,30,30);
    }

    public void generateBackgroundCanvas() {
        backgroundCanvas = new Canvas(800,600);
        GraphicsContext context = backgroundCanvas.getGraphicsContext2D();
        context.setFill(Color.DARKBLUE);
        context.fillRect(0,0,100000,100000);
    }
}