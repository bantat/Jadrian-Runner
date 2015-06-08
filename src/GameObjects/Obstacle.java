package GameObjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for representing obstacles in the game.
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public class Obstacle extends GameObject {
    private String obstacleType;
    private static final String[] obstacleTypes = new String[] {
            "Stick", "Log", "Bush"
    };

    /**
     * Instantiates a new obstacle
     * @param width the requested width of the obstacle
     * @param height the requested height of the obstacle
     * @param speed the speed at which the obstacle moves across the screen
     * @param x the x position of the obstacle
     * @param y the y position of the obstacle
     */
    public Obstacle(int width, int height, double speed, int x, int y) {
        super();

        this.width = width;
        this.height = height;

        determineType();

        this.dx = -1 * speed;
        this.x = x;
        this.y = y;
    }

    private void determineType() {
        if (width / height < .5) {
            obstacleType = "Stick";
        } else if (width / height > 2) {
            obstacleType = "Log";
        } else {
            obstacleType = "Bush";
        }
    }

    /**
     * Updates the position of the Obstacle object based on its velocity.
     */
    public void updatePosition(double elapsed) {
        x += elapsed * dx;
    }

    public static String[] getObstacleTypes() {
        return obstacleTypes;
    }

    public String getObstacleType() {
        return obstacleType;
    }
}
