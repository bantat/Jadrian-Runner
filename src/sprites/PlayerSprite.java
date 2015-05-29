/**
 * Created by Ben on 5/28/2015.
 */
public class PlayerSprite extends ModelSprite {
    private int runState = 0;

    public PlayerSprite(PlayerViewSprite viewSprite) {
        super(viewSprite);
        )
    }

    public void animateRun() {
        if (runState == 0) {
            runState = (runState + 1) % 2;
        }

        PlayerModelSprite.animateRun();
    }
}
