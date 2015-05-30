
import javafx.stage.Stage;

import sprites.GameObject;
import sprites.Player;
import java.util.Random;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by torebanta on 5/27/15.
 * @author Tore Banta
 * @author Ben Withbroe
 */

public class Model {
    private List<GameObject> gameObjects;

    /**
     * Initializes our game by creating a GameObject for the player and a
     * GameObject for the first obstacles encountered in our game.
     */
    public void init() {
        gameObjects = new ArrayList<GameObject>();
        gameObjects.add(new Player());
        gameObjects.add(getInitObstacles();
    }

    /**
     * Returns an ArrayList of Obstacle objects to be used at the beginning
     * of the game.
     */
    public List<Obstacles> getInitObstacles() {
        List<Obstacles> obstaclesList = new ArrayList<Obstacles>()
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
    public void updateGameState(int dtime) {
        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects[i].getClass.equals(Player.class)) Player.update(dtime);
            else if (gameObjects[i].getClass.equals(List.class)) {
                for (int j = 0; j < gameObjects[1].size(); j++){
                    gameObjects[i][j].update(dtime);
                }
            }
        }
    }


    /**
     * Gets the GameObjects needed to play the game. Returns an array list
     * of these objects.
     */
    public ArrayList<GameObject> getGameObjects()   {
        return gameObjects;
    }

    /**
     * Generates the next section of track for the user to play on.
     */
    public void genNewTrack() {

    }


}
