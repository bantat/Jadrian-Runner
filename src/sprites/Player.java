package sprites;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

/**
 * Class for the user to control.
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

    boolean shouldJump;
    boolean left;
    boolean right;
    boolean isAlive;

    int maxY;
    int minY;
    int minX;
    int maxX;

    public Player() {

        super();

        sheetWidth = 40;
        sheetHeight = 40;
        width = 40;
        height = 40;

        fallSpeed = 2;
        moveSpeed = 5;
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

        // Loads the sprites
        try {
            sprites = new ArrayList<Image[]>();
            loadSprites();
        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new SpriteAnimation();
        animation.setFrames(sprites.get(RUNNING));
    }

    /**
     * Loads the sprite images to the animation ArrayList.
     */
    public void loadSprites() {
        for (int i = 0; i < NUM_PLAYER_STATES; i++) {
            Image[] imageArray;

            if (i == JUMPING) {
                imageArray = new Image[numFrames[JUMPING]];
                for (int j = 0; j < numFrames[JUMPING]; j++) {
                    String imagePath = String.format("/Resources/sprites/player/jumping/PlayerJumping-%d.gif", j);
                    imageArray[j] = loadScaledImage(imagePath, 4);
                }

            } else if (i == FALLING) {
                imageArray = new Image[numFrames[FALLING]];
                for (int j = 0; j < numFrames[FALLING]; j++) {
                    String imagePath = String.format("/Resources/sprites/player/falling/PlayerFalling-%d.gif", j);
                    imageArray[j] = loadScaledImage(imagePath, 4);
                }

            } else {
                imageArray = new Image[numFrames[RUNNING]];
                for (int j = 0; j < numFrames[RUNNING]; j++) {
                    String imagePath = String.format("/Resources/sprites/player/running/PlayerRunning-%d.gif", j);
                    imageArray[j] = loadScaledImage(imagePath, 4);
                }
            }

            sprites.add(imageArray);
        }
    }

    /**
     * Loads the image, requesting it be of size width x height.
     * @param imagePath the filepath to the image
     * @param width the requested width
     * @param height the requested height
     * @return the scaled image
     */
    public Image loadScaledImage(String imagePath, int width, int height) {
        Image scaledSpriteImage = new Image(
                imagePath,
                width, height,
                false, false
        );
        return scaledSpriteImage;
    }

    /**
     * Loads the image scaled by scalingFactor to retain the original aspect
     * ratio.
     * @param imagePath the filepath to the image
     * @param scalingFactor the scaling factor for the image
     * @return the scaled image
     */
    public Image loadScaledImage(String imagePath, double scalingFactor) {
        Image spriteImage = new Image(imagePath);
        Image scaledSpriteImage = new Image(
                imagePath,
                spriteImage.getWidth() * scalingFactor,
                spriteImage.getHeight() * scalingFactor,
                true,    // preserveRatio
                false    // smooth
        );
        return scaledSpriteImage;
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
            if (dx - moveSpeed > 0) {
                dx += -1 * moveSpeed;
            }
            else {
                dx = 0;
            }
        }

        if (!left && !right && dx < 0) {
            if (dx + moveSpeed < 0) {
                dx += moveSpeed;
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
            dx = 0;
            dy = 0;
            y = maxY;
        }

        x = x + dx;
        y = y + dy;

        // Sets the animation to correspond to the correct action.
        if (dy > 0) {
            animation.setFrames(sprites.get(FALLING));
            animation.setFrameDelay(100);
            width = 40;

        } else if (dy < 0) {
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

    /**
     * Draws the player onto the given canvas.
     * @param gameCanvas
     */
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
