package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

/**
 * Created by alexgriese on 5/29/15.
 */
public class Obstacle extends GameObject {

    public Obstacle(int width, int height, int speed, int x, int y) {

        super();

        this.width = width;
        this.height = height;
        this.dx = -1 * speed;
        this.x = x;
        this.y = y;

    }

    public void updatePosition() {
        x = x + dx;
    }

    @Override
    public void draw(Canvas gameCanvas) {
        GraphicsContext context = gameCanvas.getGraphicsContext2D();
        context.setFill(javafx.scene.paint.Color.GREEN);
        context.fillRect(x, y, width, height);
    }

}
