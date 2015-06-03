import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sprites.GameObject;
import sprites.Obstacle;
import sprites.Player;

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
    private ArrayList<Obstacle> obstacles;
    private Player player;
    private boolean isRunning;
    private boolean isJumping = false;
    private boolean left;
    private boolean right;
    private int score;

    /**
     * Initializes our game by creating a GameObject for the player and a
     * GameObject for the first obstacles encountered in our game.
     */
    public void init() {
        player = new Player();
        obstacles = new ArrayList<Obstacle>();
        isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * Returns an ArrayList of Obstacle objects to be used at the beginning
     * of the game.
     */
    public void generateNewObstacle() {
        int minWidth = 20;
        int maxWidth = 80;

        int minHeight = 20;
        int maxHeight = 80;

        int minSpeed = 5;
        int maxSpeed = 7;

        int minX = 800;
        int maxX = 1500;

        int minY = 280;
        int maxY = 500;

        boolean obstacleNotMade = true;
        Obstacle tempObstacle;
        obstacles.add(new Obstacle(50,100,8,1050,randInt(240,400)));

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
    }

    /**
     * Updates the GameState. Gets the new positions of the GameObjects,
     * based on the amount of time passed. Receives time passed in case player
     * presses space bar in between time increments.
     */
    public void updateInputState(boolean jumpState, boolean leftState,
                                 boolean rightState) {
        isJumping = jumpState;
        left = leftState;
        right = rightState;
    }

    public void updateGameState() {
        player.setJumping(isJumping);
        player.setLeft(left);
        player.setRight(right);
        for (int j = 0; j < obstacles.size(); j++) {
            if (obstacles.get(j).isCollision(player)) {
                isRunning = false;
            }
        }
        player.updatePosition();
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
