import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.stage.Modality;
import javafx.stage.Stage;

import GameObjects.GameObject;
import GameObjects.Obstacle;
import GameObjects.SpriteAnimation;

/**
 * A class for rendering the game graphics on a screen.
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public class View {

    // Instance variables for generating the window on the screen.
    private Model model;
    private GameController gameController;
    private Canvas mainCanvas;
    private Canvas backgroundSkyCanvas;
    private Canvas backgroundGrassCanvas;
    private Stage gameWindow;
    private Stage popupWindow;

    private int windowWidth = 800;
    private int windowHeight = 600;

    private double backgroundSkyShift = -2;
    private double backgroundGrassShift = -8;
    private double backgroundSkyCanvasX = 0;
    private double backgroundGrassCanvasX = 0;

    private Image backgroundSkyImage;
    private Image backgroundGrassImage;

    // Instance variables for continual running.
    private AnimationTimer viewTimer;
    private long lastFrameDraw = 0;

    private String defaultObstacleType;
    private Map<String, Image> obstacleImages;

    // Instance variables for displaying the Player object.
    private SpriteAnimation playerAnimation;
    private ArrayList<Image[]> playerSprites;

    // Instance variables that determine which series of images to use for the
    // Player object animation, based on index in numFrames. numFrames contains
    // number of images used for each different animation.
    private static final int PLAYER_RUNNING = 0;
    private static final int PLAYER_JUMPING = 1;
    private static final int PLAYER_FALLING = 2;
    private static final int NUM_PLAYER_STATES = 3;
    private final int[] numFrames = {
            8, // RUNNING
            3, // JUMPING
            3  // FALLING
    };

    /**
     * Stores references to the gameWindow Stage and model objects for the
     * game, and creates a two dimensional context for drawing game elements
     * on the canvas.
     *
     * @param model the model that stores all of the information about the
     *              game's current state.
     * @param gameController the controller that handles keyboard input during
     *                       game play
     * @param gameWindow the Stage on which everything is drawn
     */
    public View(Model model, GameController gameController, Stage gameWindow) {
        this.model = model;
        this.gameController = gameController;
        this.gameWindow = gameWindow;

        backgroundSkyImage = loadScaledImage(
                "/Images/Background/Background_Sky.png",
                0,     // width
                800,   // height
                true); // preserveRatio
        backgroundGrassImage = loadScaledImage(
                "/Images/Background/Background_Grass.png",
                0,     // width
                800,   // height
                true); // preserveRatio
    }

    /**
     * Generates the Start window the user interacts with when the game is
     * opened initially.
     */
    public void loadStartScreen() {
        gameWindow.setTitle("Jadrian Runner");
        gameWindow.setScene(loadMainScene());
        gameWindow.show();
    }

    /**
     * Generates the game window the user interacts with in order to play the
     * game.
     */
    public void loadGameScreen() {
        gameWindow.setScene(loadGameScene());
        gameWindow.show();
    }

    /**
     * Draws the game onto the game canvas.
     */
    public void drawGame() {
        //Checks to see if the game has ended as a result of a collision.
        // If it has send the user to the start screen.
        if (!model.gameRunning()) {
            viewTimer.stop();
            //loadStartScreen();
            int distance = model.getDistance();
            model.resetDistance();
            loadGameOverScreen(distance);
        }

        // Initializes a GraphicsContext variable to allow the program to draw
        // the player object, Obstacle objects and background on the game
        // window. Clears the previous game screen in order to draw new game
        // state with the updated Player object, Obstacle objects and distance.
        GraphicsContext context = mainCanvas.getGraphicsContext2D();
        context.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        backgroundSkyCanvasX += backgroundSkyShift;
        updateBackgroundImage(backgroundSkyCanvas,
                              backgroundSkyImage,
                              backgroundSkyCanvasX);

        backgroundGrassCanvasX += backgroundGrassShift;
        updateBackgroundImage(backgroundGrassCanvas,
                              backgroundGrassImage,
                              backgroundGrassCanvasX);

        // Resets the position of each background image if they've travelled
        // far enough across the screen.
        if (backgroundSkyCanvasX <= -backgroundSkyImage.getWidth()) {
            backgroundSkyCanvasX = 0;
        }
        if (backgroundGrassCanvasX <= -backgroundGrassImage.getWidth()) {
            backgroundGrassCanvasX = 0;
        }

        GraphicsContext gameContext = mainCanvas.getGraphicsContext2D();
//        context.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        GameObject player = model.getPlayer();
        drawPlayer(gameContext, player);

        List<Obstacle> obstaclesOffscreenLeft = new ArrayList<Obstacle>();
        int numObjectsOffscreenRight = 0;

        // Determines which obstacles are on the screen. If the obstacle is
        // offscreen to the right, it is simply not drawn. If it is offscreen
        // to the left, it is not drawn and is recorded in a list.
        for (Obstacle obstacle : model.getObstacles()) {
            if (isOffScreen(obstacle, mainCanvas)) {
                if (obstacle.getX() < 0) {
                    obstaclesOffscreenLeft.add(obstacle);
                } else {
                    ++numObjectsOffscreenRight;
                }
            } else {
                drawObstacle(gameContext, obstacle);
            }
        }

        // Informs Model which obstacles are offscreen to the left and how
        // many are offscreen to the right.
        model.updateOffscreenObstacles(obstaclesOffscreenLeft,
                                       numObjectsOffscreenRight);

        context.setFont(new Font("Comic Sans MS", (double) 24));
        context.fillText("SCORE   " + model.getDistance() + "m", 40, 40);
    }

    /**
     * Method for checking if a GameObject is within the boundaries of a Canvas.
     * @param gameCanvas the canvas the GameObject has been drawn on
     * @return true if the object is off of the screen
     */
    private boolean isOffScreen(GameObject object, Canvas gameCanvas) {
        return (object.getX() + object.getWidth() < 0)
            || (object.getX() > gameCanvas.getWidth())
            || (object.getY() + object.getHeight() < 0)
            || (object.getY() > gameCanvas.getHeight());
    }

    /**
     * Updates the position of a background image. Draws two copies of the
     * image to allow for smooth scrolling across the screen.
     * @param canvas the canvas the image is drawn on
     * @param image the background image to be drawn
     * @param x the x position of the image
     */
    public void updateBackgroundImage(Canvas canvas, Image image,
                                       double x) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        context.drawImage(image, x, -200);
        context.drawImage(image, x + image.getWidth(), -200);
    }

    /**
     * Creates a popup window that displays the player's distance and has
     * buttons allowing the player to start a new game or quit.
     * @param distance the distancee the player travelled in the game
     */
    public void loadGameOverScreen(int distance) {
        Button quitButton = new Button("Quit Game");
        Button newButton = new Button("New Game");

        FlowPane flowPlane = new FlowPane(Orientation.VERTICAL);
        flowPlane.setPadding(new Insets(0,0,75, 0));
        flowPlane.setAlignment(Pos.BOTTOM_CENTER);

        Text distanceText = new Text(String.format("Distance: %d", distance));
        distanceText.setFont(Font.font ("Comic Sans MS", 20));

        flowPlane.getChildren().add(distanceText);
        flowPlane.getChildren().add(newButton);
        flowPlane.getChildren().add(quitButton);

        flowPlane.setVgap(25);
        flowPlane.setHgap(25);
        flowPlane.setStyle("-fx-background: #95f7ff;");

        Scene scene = new Scene(flowPlane, 400, 300);

        // Overrides the handle method for each button with a lambda function
        quitButton.setOnAction( (ActionEvent e) -> onQuitGame() );
        newButton.setOnAction( (ActionEvent e) -> {
            gameController.reset();

            loadGameScreen();
            popupWindow.close();
        });

        popupWindow = new Stage();
        popupWindow.setScene(scene);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.show();
    }

    /**
     * Loads the main menu game scene from the relevant FXML file.
     * @return the main menu game scene.
     */
    public Scene loadMainScene() {
        Scene mainScene;
        Parent root = null;

        try {
            FXMLLoader temp = new FXMLLoader(
                    View.class.getResource("/FXML/Menu.fxml")
            );
            temp.setController(this);
            root = temp.load();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (root == null) {
            System.out.println("Could not load FXML file.");
            System.exit(1);
        }

        mainScene = new Scene(root, 800, 600);

        return mainScene;
    }

    /**
     * Handles the case where the game is quit by the user.
     */
    public void onQuitGame() {
        try {
            FXMLLoader temp = new FXMLLoader(
                    View.class.getResource("/FXML/Menu.fxml")
            );
            temp.setController(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(1);
    }

//    public void onNewGame(ActionEvent actionEvent) {
//        loadGameScreen();
//    }
//
//    public void onQuitGame() { System.exit(1); }

    /**
     * Loads the standard game screen for playing  from the relevant FXML file.
     * @return the playing game scene
     */
    public Scene loadGameScene() {
        generateBackgroundCanvases();
        generateGameCanvas();

        List<Canvas> gameCanvasses = new ArrayList<Canvas>();
        gameCanvasses.add(backgroundSkyCanvas);
        gameCanvasses.add(backgroundGrassCanvas);
        gameCanvasses.add(mainCanvas);

        model.init();

        genPlayerAnimation();
        genObstacleImages();

        Scene gameScene;

        // Adds all the canvasses, which have all been 'drawn' on to the scene,
        // in order to be displayed.
        Group root = new Group();
        root.getChildren().addAll(gameCanvasses);
        gameScene = new Scene(root, 800, 600);

        // Overrides the handle method with a method reference to the
        // game controller.
        gameScene.setOnKeyPressed( gameController::keyPressed );
        gameScene.setOnKeyReleased( gameController::keyReleased );

        // Initializes the game timer that responds to the passage of time
        // requests updates regularly.
        viewTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Redraws the game
                if (now - lastFrameDraw > 20000000L) {
                    lastFrameDraw = now;
                    drawGame();
                }
            }
        };
        viewTimer.start();

        return gameScene;
    }

    /**
     * Generates the canvas for the background of the game, along with any
     * images drawn onto it.
     */
    public void generateBackgroundCanvases() {
        backgroundGrassCanvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext backgroundSkyContext = backgroundGrassCanvas.getGraphicsContext2D();

        backgroundSkyCanvas = new Canvas(800,600);
        GraphicsContext backgroundGrassContext = backgroundSkyCanvas.getGraphicsContext2D();

        backgroundSkyContext.drawImage(backgroundSkyImage,0,-200);
        backgroundGrassContext.drawImage(backgroundGrassImage,0,-200);
    }

    /**
     * Generates the canvas used for game play, including the Player object
     * and any Obstacle objects on the screen.
     */
    public void generateGameCanvas() {
        mainCanvas = new Canvas(windowWidth, windowHeight);
        //GraphicsContext context = mainCanvas.getGraphicsContext2D();
    }

    /**
     * Generates the Player object's sprites and animation.
     */
    public void genPlayerAnimation() {
        // Loads the sprites
        try {
            playerSprites = new ArrayList<Image[]>();
            loadPlayerSprites();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sets the initial animation to the player running.
        playerAnimation = new SpriteAnimation();
        playerAnimation.setFrames(playerSprites.get(PLAYER_RUNNING));
    }

    /**
     * Loads the sprite Image arrays to the animation ArrayList.
     */
    public void loadPlayerSprites() {
        // Iterates through the player states and loads the respective Image
        // array for each one.
        for (int i = 0; i < NUM_PLAYER_STATES; i++) {
            Image[] imageArray;

            if (i == PLAYER_JUMPING) {
                imageArray = new Image[numFrames[PLAYER_JUMPING]];
                for (int j = 0; j < numFrames[PLAYER_JUMPING]; j++) {
                    String imagePath = String.format(
                            "/Images/GameObjects/Player/Jumping/Player_Jumping_%d.png", j
                    );
                    imageArray[j] = loadScaledImage(imagePath, 2);
                }

            } else if (i == PLAYER_FALLING) {
                imageArray = new Image[numFrames[PLAYER_FALLING]];
                for (int j = 0; j < numFrames[PLAYER_FALLING]; j++) {
                    String imagePath = String.format(
                            "/Images/GameObjects/Player/Falling/Player_Falling_%d.png", j
                    );
                    imageArray[j] = loadScaledImage(imagePath, 2);
                }

            } else {
                imageArray = new Image[numFrames[PLAYER_RUNNING]];
                for (int j = 0; j < numFrames[PLAYER_RUNNING]; j++) {
                    String imagePath = String.format(
                            "/Images/GameObjects/Player/Running/Player_Running_%d.png", j
                    );
                    imageArray[j] = loadScaledImage(imagePath, 2);
                }
            }

            // Adds the frames for the Player object's animation.
            playerSprites.add(imageArray);
        }
    }

    private void genObstacleImages() {
        obstacleImages = new HashMap<String, Image>();
        String[] obstacleTypes = Obstacle.getObstacleTypes();

        for (String type : obstacleTypes) {
            String path = String.format("/Images/GameObjects/Obstacles/%s.png", type);
            obstacleImages.put(type, loadScaledImage(path, 1));
        }

        defaultObstacleType = obstacleTypes[0];
    }

    /**
     * Loads the image, requesting it be of size width x height.
     * @param imagePath the filepath to the image
     * @param width the requested width
     * @param height the requested height
     * @return the scaled image
     */
    public Image loadScaledImage(String imagePath,
                                 int width,
                                 int height,
                                 boolean preserveRatio) {
        return new Image(
                getClass().getResourceAsStream(imagePath),
                width,
                height,
                preserveRatio,
                false    // smooth
        );
    }

    /**
     * Loads the image scaled by scalingFactor to retain the original aspect
     * ratio.
     * @param imagePath the filepath to the image
     * @param scalingFactor the scaling factor for the image
     * @return the scaled image
     */
    public Image loadScaledImage(String imagePath, double scalingFactor) {
        Image spriteImage = new Image(getClass().getResourceAsStream(imagePath));
        return new Image(
                getClass().getResourceAsStream(imagePath),
                spriteImage.getWidth() * scalingFactor,
                spriteImage.getHeight() * scalingFactor,
                true,    // preserveRatio
                false    // smooth
        );
    }

    /**
     * Updates the Player object's animation, depending on if the player is
     * running, jumping, or falling.
     * Will later be abstracted to account for any object's animation.
     * @param player the Player object to be updated.
     */
    public void updatePlayerAnimation(GameObject player) {
        if (player.getYVelocity() > 0) {
            playerAnimation.setFrames(playerSprites.get(PLAYER_FALLING));
            playerAnimation.setFrameDelay(100);
//            width = 40;

        } else if (player.getYVelocity() < 0) {
            playerAnimation.setFrames(playerSprites.get(PLAYER_JUMPING));
            playerAnimation.setFrameDelay(100);
//            width = 40;

        } else {
            playerAnimation.setFrames(playerSprites.get(PLAYER_RUNNING));
            playerAnimation.setFrameDelay(40);
//            width = 40;
        }

        playerAnimation.update();
    }

    /**
     * Draws the Player object onto the game canvas.
     * @param context the GraphicsContext the Player object is drawn on.
     * @param player the Player object that is drawn
     */
    public void drawPlayer(GraphicsContext context, GameObject player) {
        updatePlayerAnimation(player);

        Image playerImage = playerAnimation.getImage();
        context.drawImage(
                playerImage,
                player.getX(),
                player.getY(),
                playerImage.getWidth(),
                playerImage.getHeight()
        );
    }

    /**
     * Draws the Obstacle object onto the game canvas.
     * @param context the GraphicsContext the Obstacle object is drawn on.
     * @param obstacle the Obstacle object that is drawn
     */
    public void drawObstacle(GraphicsContext context, Obstacle obstacle) {
        // Loads the obstacle image from the obstacleImages map. If the
        // obstacleType is invalid, loads the default obstacleImage.
        Image obstacleImage;
        String obstacleType = obstacle.getObstacleType();

        if (obstacleImages.containsKey(obstacleType)) {
            obstacleImage = obstacleImages.get(obstacle.getObstacleType());
        } else {
            obstacleImage = obstacleImages.get(defaultObstacleType);
        }

        context.drawImage(obstacleImage,
                          obstacle.getX(),
                          obstacle.getY(),
                          obstacle.getWidth(),
                          obstacle.getHeight());
    }
}
