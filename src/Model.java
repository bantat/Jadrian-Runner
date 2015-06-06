import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GameObjects.Obstacle;
import GameObjects.Player;

/**
 * A class for containing the logic behind the game. Generates obstacles in
 * game, and processes user input from Controller.
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public class Model {
    /*
    Keep track of the Obstacle and Player objects,
    whether or not the game is running, the specified user input
    (move left, move right, or jump), and the score.
     */
    private ArrayList<Obstacle> obstacles;
    private Player player;
    private boolean isRunning;
    private boolean isJumping = false;
    private boolean left;
    private boolean right;
    private int score;

    /**
     * Initializes our game by creating a GameObject for the player and a
     * GameObject for the obstacles encountered in our game. Sets isRunning to
     * true, since the game isRunning at this point
     */
    public void init() {
        player = new Player();
        obstacles = new ArrayList<Obstacle>();
        isRunning = true;
    }

    /**
     * Let's the program know if the game is
     * running or not.
     * @return a boolean, isRunning
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Helper method that generates a random int.
     * @param min, the lowest possible int that could be generated.
     * @param max, the largest possible int that could be generated.
     * @return randNum, the random int desired.
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        /*
        The rand.nextInt method generates a random number between 0 and one
        less than the desired int. Thus, randomNum takes the difference between
        the max and min and add 1. We then add min to the randomly generated
        number in order to keep it within the constraints (min,max).
         */
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * Returns an ArrayList of Obstacle objects to be used at the beginning
     * of the game.
     */
    public void generateNewObstacle() {
//        ARE WE GOING TO USE THIS???

//        int minWidth = 20;
//        int maxWidth = 80;
//
//        int minHeight = 20;
//        int maxHeight = 80;
//
//        int minSpeed = 5;
//        int maxSpeed = 7;
//
//        int minX = 800;
//        int maxX = 1500;
//
//        int minY = 280;
//        int maxY = 500;
//        boolean obstacleNotMade = true
//        while (obstacleNotMade) {
//            tempObstacle = new Obstacle(randInt(minWidth, maxWidth),
//                                                 randInt(minHeight, maxHeight),
//                                                 randInt(minSpeed, maxSpeed),
//                                                 randInt(minX, maxX),
//                                                 randInt(minY, maxY));
//
//            for (int i = 0; i < obstacles.size(); i++) {
//                Obstacle otherObstacle = obstacles.get(i);
//            }
//        }
//        Obstacle tempObstacle;
        obstacles.add(new Obstacle(13,              // width
                                   75,             // height
                                   8,               // speed
                                   1050,            // x
                                   randInt(240,400) // y
                )
        );


    }

    /**
     * Updates the GameState. Determines whether or not the user specified
     * a jump, move left, or move right.
     */
    public void updateInputState(boolean jumpState, boolean leftState,
                                 boolean rightState) {
        isJumping = jumpState;
        left = leftState;
        right = rightState;
    }

    /**
     * Updates the game, i.e. all the Obstacle objects' and Player object's
     * positions.
     */
    public void updateGameState() {
        // Tells the Player object whether or not the user wants to move the
        // Player object is meant to jump, move left, or move right.
        player.setJumping(isJumping);
        player.setLeft(left);
        player.setRight(right);

        // Checks to see if any of the Obstacle objects have collided with the
        // Player object. If any of them have it tells the game to end.
        for (int j = 0; j < obstacles.size(); j++) {
            if (obstacles.get(j).isCollision(player)) {
                isRunning = false;
            }
        }
        player.updatePosition();

        /*
        Checks to see if an Obstacle object is off the screen to the left; if it
        has, remove it from the Obstacle objects list, in order to keep the list
        size reasonably small. Also, checks to see if there is an Obstacle
        object, which is generated off the screen to the right before
        moving on to the game screen, is off the screen. If there is not one,
        it generates a new one. Then, updates the position of each of the
        existing Obstacle objects.
         */
        int obstaclesOffScreen = 0;
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).getX() + obstacles.get(i).getWidth() < 0) {
                obstacles.remove(i);
            }
            if (obstacles.get(i).isOffScreen(new Canvas(800, 600))) {
                obstaclesOffScreen++;
            }
            obstacles.get(i).updatePosition();

        }
        if (obstaclesOffScreen < 1) {
            generateNewObstacle();

        }
        score++;
    }

    /**
     * Gets the list of Obstacle objects.
     */
    public List<Obstacle> getObstacles()   {
        return obstacles;
    }

    /**
     * Gets Player object.
     */
    public Player getPlayer()   {
        return player;
    }

    /**
     * Generates the next section of track for the user to play on.
     */
    public void genNewTrack() {

    }

    /**
     * Getter for score.
     * @return scoreString, in a string format in order to be displayed as
     * text on screen.
     */
    public String getScore() {
        String scoreString = Integer.toString(score/10);
        return scoreString;
    }

    /**
     * Setter for score.
     */
    public void resetScore() {
        score = 0;
    }
}
