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
    };

    public Obstacle(int obstacleType, double speed, int x, int y) {
        super();

        this.obstacleType = obstacleTypes[obstacleType];

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
//        if (width > 200){
//            if (height > 200){
//                return "tree";
//            } else {
//                return "bush";
//            }
//
//        } else{
//            if (height < 200){
//                return "stick";
//            } else{
//                return "lightpole";
//            }
//        }
    }
}
