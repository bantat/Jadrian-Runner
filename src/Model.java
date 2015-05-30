import sprites.GameObject;

/**
 * Created by torebanta on 5/27/15.
 * @author Tore Banta
 * @author Ben Withbroe
 */

public class Model {

    /**
     * Updates the positions of each of the moving sprites currently on the
     * screen, based on the amount of time passed since the last calculation.
     * @param dTime the amount of time since the last position calculation
     */
    public void updatePositions(int dTime) {

    }

    /**
     * Updates the player's position based on the amount of time passed since
     * the last calculation.
     * @param dTime the amount of time since the last position calculation
     */
    private void updateSpritePosition(int dTime, GameObject gameObject) {

    }

    /**
     * Makes the player jump if doing so is valid.
     */
    public void jump() {

    }

    /**
     * Checks if it is valid for the player to jump.
     * @return True if it is valid for the player to jump.
     */
    private boolean canJump() {
        return true;
    }

    /**
     * Generates the next section of track for the user to play on.
     */
    public void genNewTrack() {

    }

    public void updateInput() {
        //...
    }
}
