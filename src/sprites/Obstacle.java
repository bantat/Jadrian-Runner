package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
/**
 * Created by alexgriese on 5/29/15.
 */
public class Obstacle extends GameObject {

    public Obstacle() {
        super();

        width = 50;
        height = 100;
    }

    public void update() {
        updatePosition();
    }

    public void updatePosition() {
        setDirection(-5,0);
        setPosition(getX() + getDirectionX(), getY());
    }

    @Override
    public void draw(Canvas gameCanvas) {
        GraphicsContext context = gameCanvas.getGraphicsContext2D();
        context.setFill(javafx.scene.paint.Color.GREEN);
        context.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}
