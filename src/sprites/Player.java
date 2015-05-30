package sprites;

import sprites.GameObject;

import java.awt.*;

/**
 * Created by Ben on 5/28/2015.
 */
public class Player extends GameObject {
    private int runState = 0;

    public Player() {
        super();
    }

    public void animateRun() {
        if (runState == 0) {
            runState = (runState + 1) % 2;
        }

    }

    @Override
    public void draw(Canvas gameCanvas) {
        //...
    }
}
