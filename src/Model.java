import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GameObjects.GameObject;
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
    private int obstacleSpeed;

    private static final Random random = new Random();

    AnimationTimer modelTimer;
    long lastUpdateTime;

    private Obstacle currentObstacle;
    private Obstacle lastObstacle;
    private int numJumpedOver;

    /**
     * Initializes our game by creating a GameObject for the player and a
     * GameObject for the obstacles encountered in our game. Sets gameRunning
     * to true, since the game gameRunning at this point
     */
    public void init() {
        player = new Player();
        obstacles = new ArrayList<Obstacle>();
        numJumpedOver = 0;
        distance = 0;

        gameRunning = true;
        isJumping = false;

        obstacleSpeed = 8;

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
        if (max < 0) {
            return -1;
        } else if (min < 0) {
            min = 0;
        }
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Returns an ArrayList of Obstacle objects to be used at the beginning
     * of the game.
     */
    public void generateNewObstacle() {

        // Defines min and max possible obstacle dimensions
        int minWidth = 15;
        int maxWidth = 70;

        int minHeight = 15;
        int maxHeight = 70;

        // Assign speed var to obstacleSpeed instance vars, which is incremented
        // over time by updateGameState
        int speed = obstacleSpeed;

        // Always draws obstacle at same offscreen x value
        int x = 1050;

        // Range of values for Y position, where player must avoid obstacles
        int minY = 280;
        int maxY = 400;

        // Spacing between obstacles for player to fit
        int bufferHeight = 100;

        // Instantiates two randomly generated temp obstacles
        Obstacle tempObstacle1 = new Obstacle(
                        randInt(minHeight, maxHeight), // height
                        randInt(minWidth, maxWidth), // width
                        speed,               // x velocity
                        x,            // x
                        randInt(minY, maxY) // y
                        );

        Obstacle tempObstacle2 = new Obstacle(
                        randInt(minHeight, maxHeight), // height
                        randInt(minWidth, maxWidth), // width
                        speed,               // x velocity
                        x,            // x
                        randInt(minY, maxY) // y
                        );

        // Booleans to track if tempObstacles have been initialized to something
        // else
        boolean tempObstacle1Init = false;
        boolean tempObstacle2Init = false;

        // Base case, draws random obstacle if none in game to compare to
        if (obstacles.size() == 0) {
            obstacles.add(new Obstacle(
                            randInt(minHeight, maxHeight), // height
                            randInt(minWidth, maxWidth), // width
                            speed,               // x velocity
                            x,            // x
                            randInt(minY, maxY) // y
                    )
            );
        }

        // Otherwise, tries to change tempObstacles to randomly generated
        // obstacle with buffer distance between previously drawn obstacle
        else {
            Obstacle otherObstacle = obstacles.get(obstacles.size() - 1);
            int tempMaxY = (int) otherObstacle.getY() + bufferHeight;
            if (minY < tempMaxY) {
                tempObstacle1 = new Obstacle(randInt(minWidth, maxWidth),
                        randInt(minHeight, maxHeight),
                        speed,
                        x,
                        randInt(minY, tempMaxY));
                tempObstacle1Init = true;
            }
            int tempMinY = (int) otherObstacle.getY() +
                    otherObstacle.getHeight();
            if (tempMinY < maxY) {
                tempObstacle2 = new Obstacle(randInt(minWidth, maxWidth),
                        randInt(minHeight, maxHeight),
                        speed,
                        x,
                        randInt(tempMinY, maxY));
                tempObstacle2Init = true;
            }

            // Randomly decides which of the temp obstacles to draw in game
            int outcome = randInt(0,1);
            if (outcome == 0 && tempObstacle1Init) {
                obstacles.add(tempObstacle1);
            }
            else if (outcome == 1 && tempObstacle2Init) {
                obstacles.add(tempObstacle2);
            }
            // Possible outcome for drawing multiple obstacles
            else if (tempObstacle1Init && tempObstacle2Init) {
                obstacles.add(tempObstacle1);
                obstacles.add(new Obstacle(
                        randInt(15, 70), // height
                        randInt(15, 70), // width
                        speed,               // x velocity
                        1050,            // x
                        randInt(280,400) // y
                        )
                );
            }
        }
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

            } else if (isOver(player, o)
                    && o != lastObstacle && o != currentObstacle) {
                numJumpedOver++;
                lastObstacle = currentObstacle;
                currentObstacle = o;
            }
        });

        if (!gameRunning) {
            modelTimer.stop();
        }

        distance++;

        // Increases speed of obstacles every 50m travelled in game
        if (distance % 500 == 0) {
            obstacleSpeed++;
        }
    }

    /**
     * Deletes the obstacles that are offscreen to the left, and generates
     * a new obstacle if there are no obstacles offscreen to the right.
     * @param offscreenLeft A list of the obstacles offscreen to the left,
     *                      which will be deleted from the obstacle list.
     * @param numOffscreenRight The number of obstacle offscreen to the right.
     */
    public void updateOffscreenObstacles(List<Obstacle> offscreenLeft,
                                         int numOffscreenRight) {

        if (offscreenLeft != null) {
            offscreenLeft.forEach(obstacles::remove);
        }
        if (numOffscreenRight < 1) {
            generateNewObstacle();
        }
    }

    /**
     * @param player the player
     * @param object the obstacle being checked
     * @return true if the player is over the obstacle
     */
    private boolean isOver(GameObject player, GameObject object) {
        return player.getY() < object.getY()
            && player.getX() > object.getX()
            && player.getX() < object.getX() + object.getWidth();
    }

    public int getNumJumpedOver() {
        return numJumpedOver;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDistance() {
        return distance / 10;
    }

    public void resetDistance() {
        distance = 0;
    }
}
