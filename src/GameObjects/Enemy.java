package GameObjects;

/**
 * A class for representing enemies in the game.
 */
public class Enemy extends GameObject {
    public Enemy(int width, int height, int speed, int x, int y) {

        super();

        boolean fireball =false;
        int fireballX;
        int fireballY;

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
        //if (fireball==false){
            //shootFireball();
        //} else{
            //stuff
        //}
    }

    public boolean checkFireball() {
        //some criteria
        return true;
    }


}