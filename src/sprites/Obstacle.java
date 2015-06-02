package sprites;

/**
 * A class for representing obstacles in the game.
 */
public class Obstacle extends GameObject {

    public Obstacle(int width, int height, int speed, int x, int y) {

        super();

        this.width = width;
        this.height = height;
        this.dx = -1 * speed;
        this.x = x;
        this.y = y;

    }

    public void updatePosition() {
        x = x + dx;
    }
}
