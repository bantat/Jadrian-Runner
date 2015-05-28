/**
 * Created by torebanta on 5/27/15.
 */
public class Controller {
    private Model model;
    private boolean spaceBar = false;
    /**
     * Creates instance variables to store state of user input. Takes reference
     * to game model as argument for the purpose of calling methods based on user
     * input.
     */
    public Controller(Model model) {
        this.model = model;
    }

    /**
     * Calls corresponding method of model to respond to user input when user
     * input has occurred.
     */
    public void updateModel() {
        if (spaceBar) {
            spaceBar = false;
        }
        //model.updateModel();
    }

    /**
     * Resets instance variable for state of user input after model has been
     * updated.
     */
    public void resetInputState() {
        if (!spaceBar){
            spaceBar = true;
        }
    }



}
