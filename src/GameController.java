import javafx.scene.input.KeyEvent;

/**
 * A class for listening for and processing user input.
 *
 * @author Tore Banta
 * @author Ben Withbroe
 * @author Alex Griese
 * @author Greg Erlandson
 */
public class GameController {
    // Keeps track of the model in order to update instance variables in model,
    // user input (move left, move right or jump).
    private Model model;
    private boolean jump;
    private boolean left;
    private boolean right;

    /**
     * Takes reference to game model as argument for the purpose of calling
     * methods based on user input.
     */
    public GameController(Model model) {
        if (model != null) {
            this.model = model;
            jump = false;
            left = false;
            right = false;
        }
    }

    /**
     * Resets the various boolean controls in the case of a new game, for
     * example.
     */
    public void reset() {
        jump = false;
        left = false;
        right = false;
    }

    /**
     * Determines which key the user has pressed and then alerts the model.
     * @param keyEvent javafx object that describes the key pressed.
     */
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent != null) {
            switch (keyEvent.getCode()) {
                case UP:
                    // fall through
                case SPACE:
                    jump = true;
                    break;

                case LEFT:
                    left = true;
                    break;

                case RIGHT:
                    right = true;
            }

            keyEvent.consume();
            model.updateInputState(jump, left, right);
        }
    }

    /**
     * Tells the model which key has been released.
     * @param keyEvent javafx object that describes the key pressed.
     */
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent != null) {
            switch (keyEvent.getCode()) {
                case UP:
                    // fall through
                case SPACE:
                    jump = false;
                    break;

                case LEFT:
                    left = false;
                    break;

                case RIGHT:
                    right = false;
            }


            keyEvent.consume();
            model.updateInputState(jump, left, right);
        }
    }
}
