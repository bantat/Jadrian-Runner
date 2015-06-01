package sprites;

import java.awt.*;
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
import sprites.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

import javax.imageio.ImageIO;


/**
 * Created by Ben on 5/28/2015.
 */
public class Player extends GameObject {

    private ArrayList<Timeline> timelines;
    private final int[] numFrames = {
            2, 8, 1, 2, 4, 2, 5
    };

    private static final int IDLE = 0;
    private static final int RUNNING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int NUM_PLAYER_STATES = 4;

    boolean isAlive;
    boolean startJump;
    boolean isJumping;
    boolean isFalling;

    int maxY;

    public Player() {

        super();

        sheetWidth = 40;
        sheetHeight = 40;
        width = 40;
        height = 40;

        fallSpeed = 0.18;
        maxFallSpeed = 4.0;
        jumpHeight = -4.8;
        shortJumpSpeed = 0.3;

        boolean isAlive = true;
        boolean startJump = false;
        boolean isJumping = false;
        boolean isFalling = false;

        maxY = 300;

        // Loads the sprites
        try {
            Image spriteSheet = new Image(
                    "/Resources/sprites/player/PlayerSprites.gif"
            );

            for (int i = 0; i < NUM_PLAYER_STATES; i++) {
                ImageView[] imageArray = new ImageView[numFrames[i]];
                KeyFrame[] keyFrames = new KeyFrame[numFrames[i]];

                for (int j = 0; j < numFrames[i]; j++) {
                    ImageView imageView = new ImageView(spriteSheet);

                    imageArray[j] = new ImageView(spriteSheet);
                    imageArray[i].setViewport(
                            new Rectangle2D(
                                    j * width,
                                    i * height,
                                    sheetWidth,
                                    sheetHeight
                            )
                    );

                    keyFrames[i] = new KeyFrame(Duration.seconds(0),
                                                new KeyValue(
                                                    imageView.imageProperty(),
                                                    spriteSheet));
                }

                Timeline timeline = new Timeline(40, keyFrames);
                timelines.add(timeline);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
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
