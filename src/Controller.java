import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * A class for listening for and processing user input.
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public class Controller {
    /*
    Keep track of the model in order to update instance variables in model,
    user input (move left, move rightor jump).
     */
    private Model model;
    private boolean jump = false;
    private boolean left = false;
    private boolean right = false;

    /**
     * Takes reference to game model as argument for the purpose of calling
     * methods based on user input.
     */
    public Controller(Model model) {
        this.model = model;
    }

    /**
     * A user has pressed a key. Determines which key the user has pressed
     * and then tells the model which key has been pressed.
     * @param keyEvent javafx object that tells the program if the user presses
     *                 a key.
     */
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

    /**
     * Tells the model which key has been released.
     * @param keyEvent javafx object that tells the program if the user presses
     *                 a key.
     */
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
