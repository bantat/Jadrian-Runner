package sprites;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

import javax.imageio.ImageIO;

/**
 * Created by Ben on 5/28/2015.
 */
public class Player extends sprites.GameObject {

    private ArrayList<Image[]> sprites;
    private final int[] numFrames = {
            8, 3, 3
    };

    private static final int RUNNING = 0;
    private static final int JUMPING = 1;
    private static final int FALLING = 2;
    private static final int NUM_PLAYER_STATES = 3;

    boolean isInAir;
    boolean isAlive;

    int maxY;
    int minY;

    public Player() {

        super();

        sheetWidth = 40;
        sheetHeight = 40;
        width = 40;
        height = 40;

        fallSpeed = 2;
        maxFallSpeed = 10;
        jumpHeight = -20;

        boolean isAlive = true;
        boolean startJump = false;
        boolean isJumping = false;
        boolean isFalling = false;

        maxY = 450;
        minY = 250;

        x = 10;
        y = maxY;

        dx = 0;
        dy = 0;

        // Loads the sprites
        try {
            sprites = new ArrayList<Image[]>();
            for (int i = 0; i < NUM_PLAYER_STATES; i++) {
                Image[] imageArray;
                if (i == JUMPING) {
                    imageArray = new Image[numFrames[JUMPING]];
                    for (int j = 0; j < numFrames[JUMPING]; j++) {
                        String imagePath = String.format("/Resources/sprites/player/jumping/PlayerJumping-%d.gif", j);
                        Image spriteImage = new Image(imagePath);
                        Image scaledSpriteImage = new Image(
                                imagePath,
                                spriteImage.getWidth() * 4, spriteImage.getHeight() * 4,
                                true, false
                        );
                        imageArray[j] = scaledSpriteImage;
                    }

                } else if (i == FALLING) {
                    imageArray = new Image[numFrames[FALLING]];
                    for (int j = 0; j < numFrames[FALLING]; j++) {
                        String imagePath = String.format("/Resources/sprites/player/falling/PlayerFalling-%d.gif", j);
                        Image spriteImage = new Image(imagePath);
                        Image scaledSpriteImage = new Image(
                                imagePath,
                                spriteImage.getWidth() * 4, spriteImage.getHeight() * 4,
                                true, false
                        );
                        imageArray[j] = scaledSpriteImage;
                    }

                } else {
                    imageArray = new Image[numFrames[RUNNING]];
                    for (int j = 0; j < numFrames[RUNNING]; j++) {
                        String imagePath = String.format("/Resources/sprites/player/running/PlayerRunning-%d.gif", j);
                        Image spriteImage = new Image(imagePath);
                        Image scaledSpriteImage = new Image(
                                imagePath,
                                160, 160,
                                false, false
                        );
                        imageArray[j] = scaledSpriteImage;
                    }
                }

                sprites.add(imageArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new SpriteAnimation();
        animation.setFrames(sprites.get(RUNNING));
    }

    public void setJumping(boolean isInAir) {
        this.isInAir = isInAir;
    }

    @Override
    public void updatePosition() {

        if (isInAir && currentAction != FALLING) {
            currentAction = JUMPING;
        }
        if (!isInAir && currentAction == JUMPING) {
            currentAction = FALLING;
        }

        if (currentAction == JUMPING) {
            if (y > minY) {
                dy = jumpHeight;

            } else {
                currentAction = FALLING;
                dy += fallSpeed;
            }

        } else if (currentAction == FALLING) {
            if (y + dy >= maxY) {
                currentAction = RUNNING;
                dy = 0;
                y = maxY;

            } else {
                dy += fallSpeed;
            }

        }  else {
            dx = 0;
            dy = 0;
        }

        x = x + dx;
        y = y + dy;

        if (currentAction == FALLING) {
            animation.setFrames(sprites.get(FALLING));
            animation.setFrameDelay(100);
            width = 40;
        } else if (currentAction == JUMPING) {
            animation.setFrames(sprites.get(JUMPING));
            animation.setFrameDelay(100);
            width = 40;
        } else {
            animation.setFrames(sprites.get(RUNNING));
            animation.setFrameDelay(40);
            width = 40;
        }

        animation.update();
    }

    @Override
    public void draw(Canvas gameCanvas) {
        GraphicsContext context = gameCanvas.getGraphicsContext2D();
        context.clearRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());

        animation.update();
        context.drawImage(
                animation.getImage(),
                (int) x,
                (int) y,
                width,
                height
        );
    }
}
