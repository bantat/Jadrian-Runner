package sprites;

/**
 * Class for the user to control.
 *
 * Template from Youtuber ForeignGuyMike, in his video at the URL:
 * https://www.youtube.com/watch?v=zUOkojY_Ylo&list=PL-2t7SM0vDfcIedoMIghzzgQqZq
 * 45jYGv&index=4
 */
public class Player extends sprites.GameObject {

    private static final int RUNNING = 0;
    private static final int JUMPING = 1;
    private static final int FALLING = 2;
    private static final int NUM_PLAYER_STATES = 3;

    boolean shouldJump;
    boolean left;
    boolean right;
    boolean isAlive;

    int maxY;
    int minY;
    int minX;
    int maxX;

    int slowSpeed;

    public Player() {

        super();

        width = 40;
        height = 40;

        fallSpeed = 2;
        moveSpeed = 8;
        slowSpeed = 3;
        maxFallSpeed = 10;
        jumpHeight = -20;

        boolean isAlive = true;
        boolean startJump = false;

        maxY = 450;
        minY = 250;
        minX = 10;
        maxX = 600;

        x = 10;
        y = maxY;

        dx = 0;
        dy = 0;
    }

    public void setJumping(boolean shouldJump) {
        this.shouldJump = shouldJump;
    }

    public void setLeft(boolean leftState) {
        this.left = leftState;
    }

    public void setRight(boolean rightState) {
        this.right = rightState;
    }

    /**
     * Updates the current position of the player based on the most recent
     * position, and velocity, and whether or not the player jumped since then.
     */
    @Override
    public void updatePosition() {
        // Determines the current action and updated direction vector from
        // the most recent update.
        if (shouldJump && currentAction != FALLING) {
            currentAction = JUMPING;
        }
        if (!shouldJump && currentAction == JUMPING) {
            currentAction = FALLING;
        }

        if (left) {
            if (x - moveSpeed < minX) {
                dx = 0;
                x = minX;
            }
            else {
                dx = -1 * moveSpeed;
            }
        }

        if (right) {
            if (x + moveSpeed > maxX) {
                dx = 0;
                x = maxX;
            }
            else {
                dx = moveSpeed;
            }
        }

        if (!left && !right && dx > 0) {
            if (dx - slowSpeed > 0) {
                dx += -1 * slowSpeed;
            }
            else {
                dx = 0;
            }
        }

        if (!left && !right && dx < 0) {
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

        x = x + dx;
        y = y + dy;
    }
}
