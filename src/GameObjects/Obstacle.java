package GameObjects;

/**
 * A class for representing obstacles in the game.
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public class Obstacle extends GameObject {

    public Obstacle(int width, int height, double speed, int x, int y) {
        super();

        this.width = width;
        this.height = height;
        this.dx = -1 * speed;
        this.x = x;
        this.y = y;
    }

    /**
     * Updates the position of the Obstacle object based on its velocity.
     */
    public void updatePosition(double elapsed) { x += elapsed * dx; }

    public String getObstacleType(){
        if (width > 200){
            if (height > 200){
                return "tree";
            } else {
                return "bush";
            }

        } else{
            if (height < 200){
                return "stick";
            } else{
                return "lightpole";
            }
        }
    }
}
