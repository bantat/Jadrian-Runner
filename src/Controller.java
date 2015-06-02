import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by torebanta on 5/27/15.
 */
public class Controller {
    private Model model;
    private boolean jump = false;
    private boolean left = false;
    private boolean right = false;

    /**
     * Creates instance variables to store state of user input. Takes reference
     * to game model as argument for the purpose of calling methods based on user
     * input.
     */
    public Controller(Model model) {
        this.model = model;
    }

    public void keyPressed(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.UP || code == KeyCode.SPACE) {
            jump = true;
            keyEvent.consume();
            model.updateInputState(jump, left, right);
        }
        if (code == KeyCode.LEFT) {
            left = true;
            keyEvent.consume();
            model.updateInputState(jump, left, right);
        }
        if (code == KeyCode.RIGHT) {
            right = true;
            keyEvent.consume();
            model.updateInputState(jump, left, right);
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.UP || code == KeyCode.SPACE) {
            jump = false;
            keyEvent.consume();
            model.updateInputState(jump, left, right);
        }
        if (code == KeyCode.LEFT) {
            left = false;
            keyEvent.consume();
            model.updateInputState(jump, left, right);
        }
        if (code == KeyCode.RIGHT) {
            right = false;
            keyEvent.consume();
            model.updateInputState(jump, left, right);
        }
    }
}
