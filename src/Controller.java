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
    // Keeps track of the model in order to update instance variables in model,
    // user input (move left, move right or jump).
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
     * Determines which key the user has pressed and then alerts the model.
     * @param keyEvent javafx object that describes the key pressed.
     */
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case SPACE:
                jump = true;
                keyEvent.consume();
                model.updateInputState(jump, left, right);
                break;

            case LEFT:
                left = true;
                keyEvent.consume();
                model.updateInputState(jump, left, right);
                break;

            case RIGHT:
                right = true;
                keyEvent.consume();
                model.updateInputState(jump, left, right);
        }
    }

    /**
     * Tells the model which key has been released.
     * @param keyEvent javafx object that describes the key pressed.
     */
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case SPACE:
                jump = false;
                keyEvent.consume();
                model.updateInputState(jump, left, right);
                break;

            case LEFT:
                left = false;
                keyEvent.consume();
                model.updateInputState(jump, left, right);
                break;

            case RIGHT:
                right = false;
                keyEvent.consume();
                model.updateInputState(jump, left, right);
        }
    }
}
