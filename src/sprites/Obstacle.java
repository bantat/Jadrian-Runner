package sprites;

import sprites.GameObject;


import java.awt.*;

/**
 * Created by alexgriese on 5/29/15.
 */
public class Obstacle extends GameObject {

    public Obstacle() {
    }

    public void update() {
        updatePosition();
    }

    public void draw(Canvas gameCanvas){}

    public void updatePosition() {
        setDirection(-5,0);
        setPosition(getX() + Obstacle.dx,getY());
    }
}
