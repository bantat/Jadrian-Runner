package sprites;

import javafx.scene.image.Image;

/**
 * Created by Ben on 5/31/2015.
 */
public class SpriteAnimation {

    private Image[] frames;
    private int currentFrame;
    private int numFrames;

    private long startTime;
    private long frameDelay;

    private boolean playedOnce;

    private int lastIndex;

    public SpriteAnimation() {
        playedOnce = false;
    }

    public void setFrames(Image[] frames) {
        if (frames == this.frames) {
            return;
        }
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setFrameDelay(long d) { frameDelay = d; }
    public void setFrame(int i) { currentFrame = i; }

    /**
     * Updates the state of the animation if the time elapsed since the frame
     * rate was last set is greater than the frame delay.
     */
    public void update() {

        if (frameDelay == -1) { return; }

        long elapsed = (System.nanoTime() - startTime) / 1000000L ;
        if (elapsed > frameDelay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame >= frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public int getFrame() { return currentFrame; }
    public Image getImage() { return frames[currentFrame]; }
    public boolean hasPlayedOnce() { return playedOnce; }

}
