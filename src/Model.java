import javafx.stage.Stage;

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
    private List<Obstacle> obstacles;
    private Player player;
    private boolean isRunning;
    private boolean isJumping;

    /**
     * Initializes our game by creating a GameObject for the player and a
     * GameObject for the first obstacles encountered in our game.
     */
    public void init() {
        //obstacles = getInitObstacles();
        player = new Player();
        isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Returns an ArrayList of Obstacle objects to be used at the beginning
     * of the game.
     */

    public ArrayList<Obstacle> getInitObstacles() {
        ArrayList<Obstacle> obstaclesList = new ArrayList<Obstacle>();
        Random random = new Random();
        int randomInt = random.nextInt(4);
        for (int i = 0; i < randomInt; i++) {
            obstaclesList.add(new Obstacle());
        }
        return obstaclesList;
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
