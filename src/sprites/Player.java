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

    boolean jumpState;
    boolean isAlive;
    boolean isJumping;
    boolean isFalling;

    int maxY;
    int minY;

    public Player() {

        super();

        width = 30;
        height = 38;

        fallSpeed = 2;
        maxFallSpeed = 10;
        jumpHeight = -20;

        boolean isAlive = true;
        boolean startJump = false;
        boolean isJumping = false;
        boolean isFalling = false;

        maxY = 500;
        minY = 350;

        x = 10;
        y = maxY;

        dx = 0;
        dy = 0;

    }

    public void setJumping(boolean jumpState) {
        this.jumpState = jumpState;
    }

    @Override
    public void updatePosition() {

        if (jumpState && !isFalling) {
            isJumping = true;
        }

        if (!jumpState && isJumping) {
            isJumping = false;
            isFalling = true;
        }

        if (isJumping) {
            if (y > minY) {
                dy = jumpHeight;
            }

            else {
                isJumping = false;
                isFalling = true;
                dy += fallSpeed;
            }
        }

        else if (isFalling) {
            if (y + dy >= maxY) {
                isFalling = false;
                dy = 0;
                y = maxY;
            }
            else {
                dy += fallSpeed;
            }
        }

        else {
            dx = 0;
            dy = 0;
        }

        x = x + dx;
        y = y + dy;
    }


    @Override
    public void draw(Canvas gameCanvas) {
        GraphicsContext context = gameCanvas.getGraphicsContext2D();
        context.clearRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
        context.setFill(javafx.scene.paint.Color.BROWN);
        context.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
