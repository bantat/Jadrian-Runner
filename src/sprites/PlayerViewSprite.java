/**
 * Created by Ben on 5/28/2015.
 */
public class PlayerViewSprite extends ViewSprite {
    private int runState;
    private int image;


    public PlayerViewSprite() {
        super();
    }

    public void animateRun() {
        runState = (runState + 1) % 2;

        //maybe change this to instead use a javafx Transition animation?
        switch(runState) {
            case 0:
                // Set the image
                break;
            case 1:
                // Set the other image
                break;
        }
    }
}
