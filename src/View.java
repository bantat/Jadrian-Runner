import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by torebanta on 5/27/15.
 */

public class View {
    private Model model;
    private Controller controller;
    private ArrayList<Canvas> gameCanvasses;
    private Canvas mainCanvas;
    private Canvas backgroundCanvas;
    private Stage gameWindow;

    /**
     * Stores references to the gameWindow Stage and model objects for the game,
     * and creates a two dimensional context for drawing game elements on the
     * canvas.
     *
     * @param model
     * @param controller
     * @param gameWindow
     */
    public View(Model model, Controller controller, Stage gameWindow) {
        this.model = model;
        this.controller = controller;
        this.gameWindow = gameWindow;
    }

    public void loadStartScreen() {
        gameWindow.setTitle("Jadrian Runner");
        gameWindow.setScene(loadGameScene());
        gameWindow.show();
    }

    public void loadMainScreen() {
        gameWindow.setTitle("Jadrian Runner");
        gameWindow.setScene(loadMainScene());
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
        gameCanvasses.add(backgroundCanvas);
        gameCanvasses.add(mainCanvas);

        drawGame();

        Scene gameScene;

        Group root = new Group();
        root.getChildren().addAll(gameCanvasses);
        gameScene = new Scene(root, 800, 600);

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controller.keyPressed(event);
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controller.keyReleased(event);
            }
        });

        return gameScene;
    }

    public void generateGameCanvas() {
        mainCanvas = new Canvas(800,600);
        GraphicsContext context = mainCanvas.getGraphicsContext2D();
        context.fillOval(10,300,30,30);
    }

    public void generateBackgroundCanvas() {
        backgroundCanvas = new Canvas(800,600);
        GraphicsContext context = backgroundCanvas.getGraphicsContext2D();

        Image background =
                new Image("resources/background.png",0,800,true,false);
        context.drawImage(background,0,-200);
    }
}