package sprites;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Ben on 5/27/2015.
 * A class for representing objects as sprites.
 */
public abstract class GameObject {

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    protected int width;
    protected int height;

    protected boolean jumping;
    protected boolean falling;

    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;

    protected double moveSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpHeight;
    protected double shortJumpSpeed;

    public Rectangle getHitBox() {
        return new Rectangle((int)x - (width/2), (int)y - (width/2),
                             width, height);
    }

    public boolean isCollision(GameObject otherObject) {
        Rectangle thisHitBox = getHitBox();
        Rectangle otherHitBox = otherObject.getHitBox();
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

    public void setLeft(boolean isLeft) {
        left = isLeft;
    }

    public void setRight(boolean isRight) {
        right = isRight;
    }

    public void setUp(boolean isUp) {
        up = isUp;
    }

    public void setDown(boolean isDown) {
        down = isDown;
    }

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

    public boolean isOffScreen(Canvas gameCanvas) {
        if (x + width < 0) return true;
        else if (x > gameCanvas.getWidth()) return true;
        else if (y + height < 0) return true;
        else if (y > gameCanvas.getHeight()) return true;
        else return false;
    }

    public abstract void updatePosition();

    public abstract void draw(Canvas gameCanvas);
}