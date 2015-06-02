import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sprites.GameObject;
import sprites.Obstacle;
import java.util.ArrayList;
import java.util.List;

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
    private AnimationTimer timer;

    private long lastFrameDraw = 0;
    private int frameCount = 0;

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

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.nanoTime();

                if (currentTime - lastFrameDraw > 25000000L) {
                    lastFrameDraw = currentTime;

                    drawGame();

                    frameCount++;

                    model.updateGameState();
                }
            }
        };

        timer.start();
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
        GraphicsContext context = mainCanvas.getGraphicsContext2D();
        context.clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());

        GameObject player = model.getPlayer();
        player.draw(mainCanvas);

        List<Obstacle> obstacles = model.getObstacles();
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).draw(mainCanvas);
        }
        if (model.isRunning() == false) {
            System.exit(1);
        }
    }

    private void helpDraw() {

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

        model.init();

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
    }

    public void generateBackgroundCanvas() {
        backgroundCanvas = new Canvas(800,600);
        GraphicsContext context = backgroundCanvas.getGraphicsContext2D();

        Image background = new Image(
                "Resources/background.png",0,800,true,false
        );
        context.drawImage(background,0,-200);
    }
}
