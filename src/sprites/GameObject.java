package sprites;

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
     *
     * @return Rectangle2D hitBox
     */
    public Rectangle2D getHitBox() {
        return new Rectangle2D((int)x, (int)y,
                             width, height);
    }

    /**
     * Method for checking if two GameObjects hitboxes intersect.
     *
     * @param otherObject
     * @return boolean isCollsion
     */
    public boolean isCollision(GameObject otherObject) {
        Rectangle2D thisHitBox = getHitBox();
        Rectangle2D otherHitBox = otherObject.getHitBox();
        return thisHitBox.intersects(otherHitBox);
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getWidth() {
        return width;
    }

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

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setDirection(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getDirectionX() {
        return this.dx;
    }
    public double getDirectionY() {
        return this.dy;
    }

    /**
     * Method for checking if a GameObject is within the boundaries of a Canvas.
     *
     * @param gameCanvas
     * @return boolean isOffScreen
     */
    public boolean isOffScreen(Canvas gameCanvas) {
        if (x + width < 0) return true;
        else if (x > gameCanvas.getWidth()) return true;
        else if (y + height < 0) return true;
        else if (y > gameCanvas.getHeight()) return true;
        else return false;
    }

    public abstract void updatePosition();

    public SpriteAnimation getAnimation() {
        return animation;
    }
}
