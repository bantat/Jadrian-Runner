package GameObjects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;

/**
 * A class for representing game entities as objects.
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public abstract class GameObject {

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    protected int width;
    protected int height;

    protected SpriteAnimation animation;
    protected int currentAction;

//    protected boolean left;
//    protected boolean right;
//    protected boolean up;
//    protected boolean down;

    protected double moveSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpHeight;
    protected double shortJumpSpeed;

    /**
     * Method for getting an object representation of the GameObject's collision
     * box.
     * @return Rectangle2D hitBox
     */
    public Rectangle2D getHitBox() {
        return new Rectangle2D((int)x, (int)y,
                             width, height);
    }

    /**
     * Method for checking if two GameObjects hitboxes intersect.
     * @param otherObject
     * @return boolean isCollsion
     */
    public boolean isCollision(GameObject otherObject) {
        Rectangle2D thisHitBox = getHitBox();
        Rectangle2D otherHitBox = otherObject.getHitBox();
        return thisHitBox.intersects(otherHitBox);
    }

    /**
     * Gets the x position of the GameObject.
     * @return the x position of the GameObject
     */
    public int getX() {
        return (int) x;
    }

    /**
     * Gets the y position of the GameObject.
     * @return the y position of the GameObject
     */
    public int getY() {
        return (int) y;
    }

    /**
     * Gets the width of the GameObject.
     * @return the width of the GameObject
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the GameObject.
     * @return the height of the GameObject
     */
    public int getHeight() {
        return height;
    }

//    public void setLeft(boolean isLeft) {
//        left = isLeft;
//    }
//
//    public void setRight(boolean isRight) {
//        right = isRight;
//    }
//
//    public void setUp(boolean isUp) {
//        up = isUp;
//    }
//
//    public void setDown(boolean isDown) {
//        down = isDown;
//    }

    /**
     * Sets the x and y position of the GameObject.
     * @param x the new x position of the GameObject.
     * @param y the new y position of the GameObject.
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x and y velocity of the GameObject.
     * @param dx the new x velocity of the GameObject.
     * @param dy the new y velocity of the GameObject.
     */
    public void setDirection(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Gets the x velocity of the GameObject.
     * @return the x velocity of the GameObject
     */
    public double getDirectionX() {
        return this.dx;
    }

    /**
     * Gets the y velocity of the GameObject.
     * @return the y velocity of the GameObject
     */
    public double getDirectionY() {
        return this.dy;
    }

    /**
     * Method for checking if a GameObject is within the boundaries of a Canvas.
     * @param gameCanvas the canvas the GameObject has been drawn on
     * @return boolean isOffScreen true, if the object is off the screen;
     *                             false, if the object is on the screen
     */
    public boolean isOffScreen(Canvas gameCanvas) {
        if (x + width < 0) return true;
        else if (x > gameCanvas.getWidth()) return true;
        else if (y + height < 0) return true;
        else if (y > gameCanvas.getHeight()) return true;
        else return false;
    }

    /**
     * An abstract method implemented by implementations of the GameObject
     * class. Will update the GameObjects position.
     */
    public abstract void updatePosition();

    /**
     * Gets the SpriteAnimation.
     * @return the sprite animation.
     */
    public SpriteAnimation getAnimation() {
        return animation;
    }
}
