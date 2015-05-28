import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by torebanta on 5/27/15.
 */

public class View {
    private Model model;
    private Canvas canvas;
    private GraphicsContext context;

    public View(Canvas gameCanvas, Model gameModel) {
        canvas = gameCanvas;
        model = gameModel;
        context = canvas.getGraphicsContext2D();
    }

    public void drawGameScreen() {
        //...
    }
}
