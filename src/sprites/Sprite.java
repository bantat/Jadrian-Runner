import java.util.List;
import java.util.ArrayList;

/**
 * Created by Ben on 5/27/2015.
 * A class for representing objects as sprites.
 */
public abstract class Sprite {
    private List<Integer> position = new ArrayList<Integer>();

//    public void Sprite() {
//
//    }

    /**
     * Sets the current position of the sprite within the cannonical Model
     * coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void setPosition(int x, int y) {
        position = new ArrayList<Integer>();
        position.add(x);
        position.add(y);
    }

    /**
     * Returns the current position of the sprite in the cannonical Model
     * coordinates.
     * @return The position as an Arraylist
     */
    public List<Integer> position() {
        return position;
    }
}
