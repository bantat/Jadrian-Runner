package sprites;

import sprites.Sprite;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Ben on 5/28/2015.
 * A class for storing sprites within the model. Each instance has an associated
 * ViewSprite that gets updated whenever the ModelSprite gets updated.
 */
public abstract class ModelSprite extends Sprite {
    private List<Integer> position = new ArrayList<Integer>();
    private ViewSprite viewSprite;

    public ModelSprite(ViewSprite viewSprite) {
        this.viewSprite = viewSprite;
    }

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

        viewSprite.setPosition(x, y);
    }
}