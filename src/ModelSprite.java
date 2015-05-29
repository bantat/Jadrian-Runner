import java.util.List;
import java.util.ArrayList;

/**
 * Created by Ben on 5/28/2015.
 */
public class ModelSprite extends Sprite{
    private List<Integer> position = new ArrayList<Integer>();
    private ViewSprite viewSprite;

//    public void Sprite() {
//
//    }

    /**
     * Sets the current position of the sprite within the cannonical Model
     * coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    @Override
    public void setPosition(int x, int y) {
        position = new ArrayList<Integer>();
        position.add(x);
        position.add(y);

        viewSprite.
    }
}