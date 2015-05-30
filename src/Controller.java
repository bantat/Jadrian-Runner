import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by torebanta on 5/27/15.
 */
public class Controller {
    private Model model;
    private boolean jump = false;

    /**
     * Creates instance variables to store state of user input. Takes reference
     * to game model as argument for the purpose of calling methods based on user
     * input.
     */
    public Controller(Model model) {
        this.model = model;
    }

    /**
     * Resets instance variable for state of user input after model has been
     * updated.
     */
    public void resetInputState() {
        if (!jump){
            jump = true;
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.UP || code == KeyCode.SPACE) {
            jump = true;
            keyEvent.consume();
            model.updateInput();
            resetInputState();
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        //...
    }
}
