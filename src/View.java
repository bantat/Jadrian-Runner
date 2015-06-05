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

    // Instance variables for generating the window on the screen.
    private Model model;
    private Controller controller;
    private ArrayList<Canvas> gameCanvasses;
    private Canvas mainCanvas;
    private Canvas backgroundCanvas;
    private Stage gameWindow;

    // Instance variables for continual running.
    private AnimationTimer timer;
    private long lastFrameDraw = 0;
    //private int frameCount = 0;

    // Instance variables for displaying the Player object.
    private SpriteAnimation playerAnimation;
    private ArrayList<Image[]> playerSprites;

    /*
    Instance variables that determine which series of images to use for the
    Player object animation, based on index in numFrames. numFrames contains
    number of images used for each different animation.
    */
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

        //AnimationTimer timer;
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
        /*
        Initializes a GraphicsContext variable to allow the program to draw
        the player object, Obstacle objects and background on the game window.
        Clears the previous game screen in order to draw new game state with the
        updated Player object, Obstacle objects and score.
         */
        GraphicsContext context = mainCanvas.getGraphicsContext2D();
        context.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());


        GameObject player = model.getPlayer();
        drawPlayer(mainCanvas,player);

        List<Obstacle> obstacles = model.getObstacles();

        for (int i = 0; i < obstacles.size(); i++) {
            drawObstacle(mainCanvas, obstacles.get(i));
        }

        context.setFont(new Font("TimesRoman", (double) 24));
        context.fillText("SCORE   " + model.getScore() + "m", 40, 40);


        //Checks to see if the game has ended as a result of a collision.
        // If it has send the user to the start screen.
        if (model.isRunning() == false) {
            timer.stop();
            controller = new Controller(model);
            loadStartScreen();
            model.resetScore();
        }
    }

    /**
     * Loads the main menu game scene from the relevant FXML file.
     * @return the main menu game scene.
     */
    public Scene loadMainScene() {
        // Javafx based variables to generate new scene for start screen.
        Scene mainScene;
        Parent root = null;

        // Loads Menu.fxml to display as start screen.
        try {
            FXMLLoader temp = new FXMLLoader(
                    View.class.getResource("/resources/Menu.fxml")
            );
            temp.setController(this);
            root = temp.load();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        // Sets the scene, after root has been set.
        mainScene = new Scene(root, 800, 600);

        return mainScene;
    }

    /**
     * Handles the case where the game is quit by the user.
     */
    public void onQuitGame() {
//        Why doe we have to do this?
//        try {
//            FXMLLoader temp = new FXMLLoader(
//                    View.class.getResource("/resources/Menu.fxml")
//            );
//            temp.setController(this);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }

        System.exit(1);
    }

//    Can we get rid of this?
//
//    public void onNewGame(ActionEvent actionEvent) {
//        loadGameScreen();
//    }
//
//    public void onQuitGame(ActionEvent actionEvent) {
//        System.exit(1);
//    }
//
//    public void onNewGame(ActionEvent actionEvent) {
//        loadGameScreen()
//    }


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

        // Initializes the game timer, which will allow the user to play the
        // game.
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.nanoTime();

                /*
                Determines the frame rate, then draws the updated positions
                based on user input, and re-updates the game state.
                */
                if (currentTime - lastFrameDraw > 25000000L) {
                    lastFrameDraw = currentTime;
                    drawGame();

                    model.updateGameState();

                    //frameCount++;
                }
            }
        };

        timer.start();

        // I don't think we need this call.
        // drawGame();

        Scene gameScene;

        // Adds all the canvasses, which have all been 'drawn' on to the scene,
        // in order to be displayed.

        Group root = new Group();
        root.getChildren().addAll(gameCanvasses);
        gameScene = new Scene(root, 800, 600);

        // Checks to see if a key has been pressed.
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controller.keyPressed(event);
            }
        });

        // Checks to see if a key has been released.
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controller.keyReleased(event);
            }
        });

        return gameScene;
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
     * Generates the canvas used for game play, including the Player object
     * and any Obstacle objects on the screen.
     */
    public void generateGameCanvas() {
        mainCanvas = new Canvas(800,600);
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
     * Loads the sprite images arrays to the animation ArrayList.
     */
    public void loadPlayerSprites() {
        for (int i = 0; i < NUM_PLAYER_STATES; i++) {
            Image[] imageArray;


            // Loads the frames for the Player object's jumping animation into
            // the image array.
            if (i == PLAYER_JUMPING) {
                imageArray = new Image[numFrames[PLAYER_JUMPING]];
                for (int j = 0; j < numFrames[PLAYER_JUMPING]; j++) {
                    String imagePath = String.format("/Resources/" +
                            "sprites/player/jumping/Player_Jumping_%d.png", j);
                    imageArray[j] = loadScaledImage(imagePath, 2);
                }

            // Loads the frames for the Player object's falling animation into
            // the image array.
            } else if (i == PLAYER_FALLING) {
                imageArray = new Image[numFrames[PLAYER_FALLING]];
                for (int j = 0; j < numFrames[PLAYER_FALLING]; j++) {
                    String imagePath = String.format("/Resources/" +
                            "sprites/player/falling/PlayerFalling-%d.gif", j);
                    imageArray[j] = loadScaledImage(imagePath, 2);
                }

            // Loads the frames for the Player object's running animation into
            // the image array.
            } else {
                imageArray = new Image[numFrames[PLAYER_RUNNING]];
                for (int j = 0; j < numFrames[PLAYER_RUNNING]; j++) {
                    String imagePath = String.format("/Resources/" +
                            "sprites/player/running/Player_Running_%d.png", j);
                    imageArray[j] = loadScaledImage(imagePath, 2);
                }
            }


            // Adds the frames for the Player object's animation to be displayed
            // in the game.
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
     * Updates the Player object's animation, depending on if the player is
     * running, jumping, or falling.
     * Will later be abstracted to account for any object's animation.
     * @param player the Player object to be updated.
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
     * Draws the Player object onto the game canvas.
     * @param gameCanvas the canvas the Player object is drawn on.
     * @param player the Player object that is drawn
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
     * Draws the Obstacle object onto the game canvas.
     * @param gameCanvas the canvas the Obstacle object is drawn on.
     * @param obstacle the Obstacle object that is drawn
     */
    public void drawObstacle(Canvas gameCanvas, GameObject obstacle) {
        GraphicsContext context = gameCanvas.getGraphicsContext2D();
        Image obstacleImage = loadScaledImage("Resources/stick.png",
                                              obstacle.getWidth(),
                                              obstacle.getHeight());
        context.drawImage(obstacleImage, obstacle.getX(), obstacle.getY());
    }
}
