<<<<<<< HEAD
import javafx.stage.Stage;

import java.util.ArrayList;

=======
>>>>>>> ee495ba722183a35ffa52ad6059aeef5aaf55d29
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
<<<<<<< HEAD
    public void updateGameState(int dTime) {
        //...
=======
    private void updateSpritePosition(int dTime, GameObject gameObject) {

    }

    /**
     * Makes the player jump if doing so is valid.
     */
    public void jump() {

>>>>>>> ee495ba722183a35ffa52ad6059aeef5aaf55d29
    }

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
