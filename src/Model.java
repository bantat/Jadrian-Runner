
import javafx.stage.Stage;

import sprites.GameObject;
import java.util.ArrayList;

/**
 * Created by torebanta on 5/27/15.
 * @author Tore Banta
 * @author Ben Withbroe
 */

public class Model {

    /**
     * Updates the GameState. Gets the new positions of the GameObjects,
     * based on the amount of time passed.
     */
    public void updateGameState() {}


    /**
     * Gets the GameObjects needed to play the game. Returns an array list
     * of these objects.
     */
    public ArrayList<GameObject> getGameObjects()   {
        return null;
    }

    /**
     * Generates the next section of track for the user to play on.
     */
    public void genNewTrack() {

    }

    /**
     * Initializes our game by creating a GameObject for the player and a
     * GameObject for the first obstacles encountered in our game.
     */
    public void init() {
        //...
    }
}
