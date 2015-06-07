package GameObjects;

/**
 * Class for the user to control.
 *
 * Template from Youtuber ForeignGuyMike, in his video at the URL:
 * https://www.youtube.com/watch?v=zUOkojY_Ylo&list=PL-2t7SM0vDfcIedoMIghzzgQqZq
 * 45jYGv&index=4
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public class Player extends GameObject {

    private static final int RUNNING = 0;
    private static final int JUMPING = 1;
    private static final int FALLING = 2;

    private boolean shouldJump;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean isAlive;

    private int maxY;
    private int minY;
    private int minX;
    private int maxX;

    private double slowSpeed;

    public Player() {

        super();

        width = 44;
        height = 76;

        fallSpeed = 2;
        moveSpeed = 8;
        slowSpeed = 3;
        maxFallSpeed = 10;
        jumpHeight = -21;

        isAlive = true;
        shouldJump = false;

        maxY = 450;
        minY = 250;
        minX = 10;
        maxX = 600;

        x = 10;
        y = maxY;

        dx = 0;
        dy = 0;
    }

    /**
     * Method for updating the Player object's jump state.
     *
     * @param shouldJump whether or not the player should try to jump
     */
    public void setJumping(boolean shouldJump) {
        this.shouldJump = shouldJump;
    }

    /**
     * Method for updating the Player object's left movement state.
     *
     * @param movingLeft boolean describing whether or not the character is
     *                   facing left.
     */
    public void setLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    /**
     * Method for updating the Player object's right movement state.
     *
     * @param movingRight boolean describing whether or not the character
     *                    is facing right.
     */
    public void setRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    /**
     * Updates the current position of the player based on the most recent
     * position, and velocity, and whether or not the player jumped since then.
     */
    @Override
    public void updatePosition(double elapsed) {
        // Determines the current action and updated direction vector from
        // the most recent update.
        if (shouldJump && currentAction != FALLING) {
            currentAction = JUMPING;
        }
        if (!shouldJump && currentAction == JUMPING) {
            currentAction = FALLING;
        }

        if (movingLeft) {
            if (x - moveSpeed < minX) {
                dx = 0;
                x = minX;
            }
            else {
                dx = -1 * moveSpeed;
            }
        }

        if (movingRight) {
            if (x + moveSpeed > maxX) {
                dx = 0;
                x = maxX;
            }
            else {
                dx = moveSpeed;
            }
        }

        if (!movingLeft && !movingRight && dx > 0) {
            if (dx - slowSpeed > 0) {
                dx += -1 * slowSpeed;
            }
            else {
                dx = 0;
            }
        }

        if (!movingLeft && !movingRight && dx < 0) {
            if (dx + slowSpeed < 0) {
                dx += slowSpeed;
            }
            else {
                dx = 0;
            }
        }

        if (currentAction == JUMPING) {
            if (y > minY) {
                dy = jumpHeight;
            } else {
                currentAction = FALLING;
                dy += fallSpeed;
            }

        } else if (currentAction == FALLING) {
            if (y + dy <= maxY) {
                dy += fallSpeed;
            } else {
                currentAction = RUNNING;
                dy = 0;
                y = maxY;
            }

        } else  {
            dy = 0;
            y = maxY;
        }

        x += elapsed * dx;
        y += elapsed * dy;
    }
}
