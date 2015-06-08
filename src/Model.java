import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GameObjects.Obstacle;
import GameObjects.Player;
import javafx.animation.AnimationTimer;

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
    // Keeps track of the Obstacle and Player objects, whether or not the game
    // is running, the specified user input (move left, move right, or jump),
    // and the distance.
    private ArrayList<Obstacle> obstacles;
    private Player player;
    private boolean gameRunning;
    private boolean isJumping;
    private boolean left;
    private boolean right;
    private int distance;

    private static final Random random = new Random();

    AnimationTimer modelTimer;
    long lastUpdateTime;

    private int numObstacleTypes;

    /**
     * Initializes our game by creating a GameObject for the player and a
     * GameObject for the obstacles encountered in our game. Sets gameRunning
     * to true, since the game gameRunning at this point
     */
    public void init() {
        player = new Player();
        obstacles = new ArrayList<Obstacle>();
        numObstacleTypes = 1;

        gameRunning = true;
        isJumping = false;

        modelTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedSeconds = (now - lastUpdateTime) / 1e9;

                if (elapsedSeconds > .005) {
                    updateGameState(elapsedSeconds * 30);
                    lastUpdateTime = now;
                }
            }
        };
        modelTimer.start();
    }

    /**
     * @return true if the game should be running
     */
    public boolean gameRunning() {
        return gameRunning;
    }

    /**
     * Helper method that generates a random int from the min to the max,
     * inclusive.
     * @param min, the lowest possible int that could be generated.
     * @param max, the largest possible int that could be generated.
     * @return randNum, the random int desired.
     */
    public static int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
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
        obstacles.add(new Obstacle(
                        randInt(15, 70), // height
                        randInt(15, 70), // width
                        8,               // x velocity
                        1050,            // x
                        randInt(280,400) // y
                )
        );


    }

    /**
     * Updates the GameState. Determines whether or not the user specified
     * a jump, move left, or move right.
     */
    public void updateInputState(boolean jumpState,
                                 boolean leftState,
                                 boolean rightState) {
        isJumping = jumpState;
        left = leftState;
        right = rightState;
    }

    /**
     * Updates the game, i.e. all the Obstacle objects' and Player object's
     * positions.
     */
    public void updateGameState(double elapsed) {
        // Tells the Player object whether or not the user wants to move the
        // Player object is meant to jump, move left, or move right.
        player.setJumping(isJumping);
        player.setLeft(left);
        player.setRight(right);
        player.updatePosition(elapsed);

        // Checks to see if any of the Obstacle objects have collided with the
        // Player object. If any of them have it tells the game to end.
        obstacles.forEach((Obstacle o) -> {
            o.updatePosition(elapsed);
            if (o.isCollision(player)) {
                gameRunning = false;
            }
        });

        if (!gameRunning) {
            modelTimer.stop();
        }
        distance++;
    }

    /**
     * Deletes the obstacles that are offscreen to the left, and generates
     * a new obstacle if there are no obstacles offscreen to the right.
     * @param offscreenLeft a list of the obstacles offscreen to the left,
     *                      which will be deleted from the obstacle list.
     * @param numOffscreenRight the number of obstacle offscreen to the right.
     */
    public void updateOffscreenObstacles(List<Obstacle> offscreenLeft,
                                         int numOffscreenRight) {
        offscreenLeft.forEach(obstacles::remove);

        if (numOffscreenRight < 1) {
            generateNewObstacle();
        }
    }

    public List<Obstacle> getObstacles() { return obstacles; }

    public Player getPlayer() { return player; }

    public int getDistance() { return distance / 10; }

    public void resetDistance() { distance = 0; }
}
