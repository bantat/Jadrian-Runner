import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import sprites.GameObject;
import sprites.Obstacle;
import sprites.SpriteAnimation;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

/**
 * A class for rendering the game graphics on a screen.
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public class View {

    // Instance variables for generating the window on the screen
    private Model model;
    private Controller controller;
    private ArrayList<Canvas> gameCanvasses;
    private Canvas mainCanvas;
    private Canvas backgroundCanvas;
    private Stage gameWindow;

    // Instance variables for continual running
    private AnimationTimer timer;
    private long lastFrameDraw = 0;
    private int frameCount = 0;

    // Instance variables for displaying the player
    private SpriteAnimation playerAnimation;
    private ArrayList<Image[]> playerSprites;

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
     * @param controller the controller that handles keyboard input
     * @param gameWindow the Stage on which everything is drawn
     */
    public View(Model model, Controller controller, Stage gameWindow) {
        this.model = model;
        this.controller = controller;
        this.gameWindow = gameWindow;

        AnimationTimer timer;
    }

    public void loadStartScreen() {
        gameWindow.setTitle("Jadrian Runner");
        gameWindow.setScene(loadMainScene());
        gameWindow.show();
    }

    public void loadGameScreen() {
        gameWindow.setScene(loadGameScene());
        gameWindow.show();
    }

    /**
     * Draws the game onto the game canvas.
     */
    public void drawGame() {
        GraphicsContext context = mainCanvas.getGraphicsContext2D();
        context.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());



        GameObject player = model.getPlayer();
        //player.draw(mainCanvas);
        drawPlayer(mainCanvas,player);

        List<Obstacle> obstacles = model.getObstacles();

        for (int i = 0; i < obstacles.size(); i++) {
            //obstacles.get(i).draw(mainCanvas);
            drawObstacle(mainCanvas, obstacles.get(i));
        }
        if (model.isRunning() == false) {
            timer.stop();
            //model = new Model();
            controller = new Controller(model);
            loadStartScreen();
            model.resetScore();
            //System.exit(1);
        }
        context.setFont(new Font("TimesRoman", (double) 24));
        context.fillText("SCORE   " + model.getScore() + "m", 40, 40);
    }

    /**
     * Loads the main menu game scene from the relevant FXML file.
     * @return the main menu game scene
     */
    public Scene loadMainScene() {
        Scene mainScene;
        Parent root = null;

        try {
            FXMLLoader temp = new FXMLLoader(
                    View.class.getResource("Menu.fxml")
            );
            temp.setController(this);
            root = temp.load();
        }

        catch (Exception e) {
            e.printStackTrace();
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
                    View.class.getResource("Menu.fxml")
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
//    public void onQuitGame(ActionEvent actionEvent) {
//        System.exit(1);
//    }

    public void onNewGame(ActionEvent actionEvent) {
        loadGameScreen();
    }


    /**
     * Loads the standard game screen for playing  from the relevant FXML file.
     * @return the playing game scene
     */
    public Scene loadGameScene() {

        generateBackgroundCanvas();
        generateGameCanvas();

        gameCanvasses = new ArrayList<Canvas>();
        gameCanvasses.add(backgroundCanvas);
        gameCanvasses.add(mainCanvas);

        model.init();
        genPlayerAnimation();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.nanoTime();

                if (currentTime - lastFrameDraw > 25000000L) {
                    lastFrameDraw = currentTime;
                    drawGame();

                    model.updateGameState();

                    frameCount++;
                }
            }
        };

        timer.start();

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

    /**
     * Generates the canvas used for game play, including the player and any
     * obstacles on the screen.
     */
    public void generateGameCanvas() {
        mainCanvas = new Canvas(800,600);
        GraphicsContext context = mainCanvas.getGraphicsContext2D();
    }

    /**
     * Generates the canvas for the background of the game, along with any
     * images drawn onto it.
     */
    public void generateBackgroundCanvas() {
        backgroundCanvas = new Canvas(800,600);
        GraphicsContext context = backgroundCanvas.getGraphicsContext2D();

        Image background = new Image(
                "Resources/background.png",0,800,true,false
        );
        context.drawImage(background,0,-200);
    }

    /**
     * Generates the player's spries and animation.
     */
    public void genPlayerAnimation() {
        // Loads the sprites
        try {
            playerSprites = new ArrayList<Image[]>();
            loadPlayerSprites();
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerAnimation = new SpriteAnimation();
        playerAnimation.setFrames(playerSprites.get(PLAYER_RUNNING));
    }

    /**
     * Loads the sprite images arrays to the animation ArrayList.
     */
    public void loadPlayerSprites() {
        for (int i = 0; i < NUM_PLAYER_STATES; i++) {
            Image[] imageArray;

            if (i == PLAYER_JUMPING) {
                imageArray = new Image[numFrames[PLAYER_JUMPING]];
                for (int j = 0; j < numFrames[PLAYER_JUMPING]; j++) {
                    String imagePath = String.format("/Resources/" +
                            "sprites/player/jumping/PlayerJumping-%d.gif", j);
                    imageArray[j] = loadScaledImage(imagePath, 2);
                }

            } else if (i == PLAYER_FALLING) {
                imageArray = new Image[numFrames[PLAYER_FALLING]];
                for (int j = 0; j < numFrames[PLAYER_FALLING]; j++) {
                    String imagePath = String.format("/Resources/" +
                            "sprites/player/falling/PlayerFalling-%d.gif", j);
                    imageArray[j] = loadScaledImage(imagePath, 2);
                }

            } else {
                imageArray = new Image[numFrames[PLAYER_RUNNING]];
                for (int j = 0; j < numFrames[PLAYER_RUNNING]; j++) {
                    String imagePath = String.format("/Resources/" +
                            "sprites/player/running/PlayerRunning-%d.gif", j);
                    imageArray[j] = loadScaledImage(imagePath, 2);
                }
            }

            playerSprites.add(imageArray);
        }
    }

    /**
     * Loads the image, requesting it be of size width x height.
     * @param imagePath the filepath to the image
     * @param width the requested width
     * @param height the requested height
     * @return the scaled image
     */
    public Image loadScaledImage(String imagePath, int width, int height) {
        Image scaledSpriteImage = new Image(
                imagePath,
                width, height,
                false, false
        );
        return scaledSpriteImage;
    }

    /**
     * Loads the image scaled by scalingFactor to retain the original aspect
     * ratio.
     * @param imagePath the filepath to the image
     * @param scalingFactor the scaling factor for the image
     * @return the scaled image
     */
    public Image loadScaledImage(String imagePath, double scalingFactor) {
        Image spriteImage = new Image(imagePath);
        Image scaledSpriteImage = new Image(
                imagePath,
                spriteImage.getWidth() * scalingFactor,
                spriteImage.getHeight() * scalingFactor,
                true,    // preserveRatio
                false    // smooth
        );
        return scaledSpriteImage;
    }

    /**
     * Updates the player's animation, depending on if the player is running,
     * jumping, or falling.
     * Will later be abstracted to account for any object's animation.
     * @param player the player to be updated.
     */
    public void updatePlayerAnimation(GameObject player) {
        // Sets the animation to correspond to the correct action.
        if (player.getDirectionY() > 0) {
            playerAnimation.setFrames(playerSprites.get(PLAYER_FALLING));
            playerAnimation.setFrameDelay(100);
//            width = 40;

        } else if (player.getDirectionY() < 0) {
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
     * Draws the player onto the game canvas.
     * @param gameCanvas the canvas the player is drawn on.
     * @param player the player that is drawn
     */
    public void drawPlayer(Canvas gameCanvas, GameObject player) {
        GraphicsContext context = gameCanvas.getGraphicsContext2D();
        context.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        updatePlayerAnimation(player);
        Image playerImage = playerAnimation.getImage();
        context.drawImage(
                playerImage,
                (int) player.getX(),
                (int) player.getY(),
                playerImage.getWidth(),
                playerImage.getHeight()
        );
    }

    /**
     * Draws the obstacle onto the game canvas.
     * @param gameCanvas the canvas the obstacle is drawn on.
     * @param obstacle the obstacle that is drawn
     */
    public void drawObstacle(Canvas gameCanvas, GameObject obstacle) {
        GraphicsContext context = gameCanvas.getGraphicsContext2D();
        //context.setFill(javafx.scene.paint.Color.GREEN);
        //context.fillRect(obstacle.getX(), obstacle.getY(), obstacle.getWidth(),
                //obstacle.getHeight());

        Image obstacleImage = loadScaledImage("Resources/stick.png",
                                              obstacle.getWidth(),
                                              obstacle.getHeight());
        context.drawImage(obstacleImage, obstacle.getX(), obstacle.getY());
    }
}
