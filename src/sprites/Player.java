package sprites;

import java.awt.*;

import javafx.scene.paint.*;
import sprites.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;


/**
 * Created by Ben on 5/28/2015.
 */
public class Player extends GameObject {
    boolean isAlive;
    boolean startJump;
    boolean isJumping;
    boolean isFalling;

    int maxY;

    public Player() {

        super();

        width = 30;
        height = 38;

        fallSpeed = 0.18;
        maxFallSpeed = 4.0;
        jumpHeight = -4.8;
        shortJumpSpeed = 0.3;

        boolean isAlive = true;
        boolean startJump = false;
        boolean isJumping = false;
        boolean isFalling = false;

        maxY = 300;

    }

    public void setJumping(boolean jumpState) {
        if (jumpState && !isJumping) {
            startJump = true;
            isJumping = false;
        }
    }

    @Override
    public void updatePosition() {
        if (startJump && !isFalling) {
            dy = jumpHeight;
            isJumping = true;
            startJump = false;
        }
        else if (isJumping) {
            if (dy < 0 && dy + 1 < 0) {
                dy++;
            }
            else if (dy < 0) {
                dy = 0;
            }
            else {
                dy = fallSpeed;
                isFalling = true;
            }
        }
        else if (isFalling) {
            if (y >= maxY) {
                y = maxY;
                dy = 0;
                isFalling = false;
            }
            else if (dy < maxFallSpeed && dy + fallSpeed < maxFallSpeed) {
                dy += fallSpeed;
            }
            else {
                dy = maxFallSpeed;
            }
        }
    }


    @Override
    public void draw(Canvas gameCanvas) {
        GraphicsContext context = gameCanvas.getGraphicsContext2D();
        context.setFill(javafx.scene.paint.Color.BROWN);
        context.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
