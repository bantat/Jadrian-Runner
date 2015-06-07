package GameObjects;

import javafx.geometry.Rectangle2D;

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

    protected int currentAction;

//    protected boolean left;
//    protected boolean right;
//    protected boolean up;
//    protected boolean down;

    protected double moveSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpHeight;

    /**
     * Returns an Rectangle2D representation of the GameObject's
     * collision box.
     * @return Rectangle2D hitBox
     */
    public Rectangle2D getHitBox() {
//        return new Rectangle2D((int) x + width / 2, (int) y + height / 2,
//                               width * 1.5, height * 1.5);
        return new Rectangle2D((int) x, (int) y,
                               width, height);
    }

    /**
     * Checks if two GameObjects hitboxes intersect.
     * @param otherObject the object being checked for the collision
     * @return boolean isCollsion
     */
    public boolean isCollision(GameObject otherObject) {
        return getHitBox().intersects(otherObject.getHitBox());
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

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

    public double getXVelocity() { return this.dx; }

    public double getYVelocity() { return this.dy; }

    /**
     * An abstract method implemented by implementations of the GameObject
     * class. Will update the GameObjects position.
     */
    public abstract void updatePosition(double elapsed);
}
