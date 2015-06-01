import javafx.stage.Stage;

import sprites.GameObject;
import sprites.Obstacle;
import java.util.Random;
import sprites.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by torebanta on 5/27/15.
 * @author Tore Banta
 * @author Ben Withbroe
 */

public class Model {
    private ArrayList<Obstacle> obstacles;
    private Player player;
    private boolean isRunning;
    private boolean isJumping;
    private boolean quit = false;

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

        int minSpeed = 1;
        int maxSpeed = 15;

        int minX = 800;
        int maxX = 1500;

        int minY = 250;
        int maxY = 500;

        obstacles.add(new Obstacle(
                randInt(minWidth, maxWidth),
                randInt(minHeight, maxHeight),
                8,
                randInt(minX, maxX),
                randInt(minY, maxY)
                ));
    }

    /**
     * Updates the GameState. Gets the new positions of the GameObjects,
     * based on the amount of time passed. Receives time passed in case player
     * presses space bar in between time increments.
     */
    public void updateInputState(boolean inputState) {
        isJumping = inputState;
    }

    public void updateGameState() {
        player.setJumping(isJumping);
        player.updatePosition();
        for (Obstacle obstacle : obstacles) {
            obstacle.updatePosition();
            if (obstacle.isCollision(player)) {
                setQuit();
            }
        }
    }

    private void setQuit() {
        quit = true;
    }

    public boolean getQuit() {
        return quit;
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


}
