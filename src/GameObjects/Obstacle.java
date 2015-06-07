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
            "Stick"
    };

    // Instantiates an unmodifiable map that maps each obstacleType to an int
    // array describing its dimensions.
    private static final Map<String, int[]> obstacleDimensions;
    static {
        Map<String, int[]> temp = new HashMap<>();
        temp.put("Stick", new int[]{13, 75});

        obstacleDimensions = Collections.unmodifiableMap(temp);
    }

    /**
     * Instatiates a new obstacle
     * @param obstacleType an int representing the type of obstacle
     * @param speed the speed at which the obstacle moves across the screen
     * @param x the x position of the obstacle
     * @param y the y position of the obstacle
     */
    public Obstacle(int obstacleType, double speed, int x, int y) {
        super();

        this.obstacleType = obstacleTypes[obstacleType];

        width = obstacleDimensions.get(this.obstacleType)[0];
        height = obstacleDimensions.get(this.obstacleType)[1];

        this.dx = -1 * speed;
        this.x = x;
        this.y = y;
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
