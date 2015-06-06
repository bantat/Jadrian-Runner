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
    // Keeps track of the Obstacle and Player objects, whether or not the game
    // is running, the specified user input (move left, move right, orjump),
    // and the distance.
    private ArrayList<Obstacle> obstacles;
    private Player player;
    private boolean isRunning;
    private boolean isJumping = false;
    private boolean left;
    private boolean right;
    private int distance;

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
     * Helper method that generates a random int from the min to the max,
     * inclusive.
     * @param min, the lowest possible int that could be generated.
     * @param max, the largest possible int that could be generated.
     * @return randNum, the random int desired.
     */
    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
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
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isCollision(player)) {
                isRunning = false;
            }
            obstacle.updatePosition();
        }

        player.updatePosition();

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
        for (Obstacle obstacle: offscreenLeft) {
            obstacles.remove(obstacle);
        }

        if (numOffscreenRight < 1) {
            generateNewObstacle();
        }
    }

    public List<Obstacle> getObstacles() { return obstacles; }

    public Player getPlayer() { return player; }

    public int getDistance() { return distance/10; }

    public void resetDistance() { distance = 0; }
}
