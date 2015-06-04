package sprites;

/**
 * A class for representing obstacles in the game.
 */
public class Obstacle extends GameObject {

    /*
    Takes and sets the width, height, speed and x,y position of the given
    object. Inherits? the methods of the GameObject abstract class. Se
     */
    public Obstacle(int width, int height, int speed, int x, int y) {

        super();

        this.width = width;
        this.height = height;
        this.dx = -1 * speed;
        this.x = x;
        this.y = y;

    }

    /**
     * Updates the position of the Obstacle object based on its speed.
     */
    public void updatePosition() {
        x = x + dx;
    }
}
