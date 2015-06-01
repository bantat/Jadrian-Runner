package sprites;

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

    private ArrayList<Timeline> timelines;
    private final int[] numFrames = {
            2, 8, 1, 2, 4, 2, 5
    };

    private static final int IDLE = 0;
    private static final int RUNNING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int NUM_PLAYER_STATES = 4;

    boolean jumpState;
    boolean isAlive;
    boolean isJumping;
    boolean isFalling;

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
