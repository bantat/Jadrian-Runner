package sprites;

import sprites.GameObject;

import java.awt.*;

/**
 * Created by Ben on 5/28/2015.
 */
public class Player extends GameObject {
    boolean isAlive;
    boolean isJumping;
    boolean isFalling;

    public Player() {

        super();

        width = 30;
        height = 38;

        fallSpeed = 0.18;
        maxFallSpeed = 4.0;
        jumpHeight = -4.8;
        shortJumpSpeed = 0.3;

        boolean isAlive = true;
        boolean isJumping = false;
        boolean isFalling = false;

    }

    public void setJumping(boolean jumpState) {
        isJumping = jumpState;
    }

    @Override
    public void updatePosition() {
        
    }

    @Override
    public void draw(Canvas gameCanvas) {
        //...
    }
}
