package GameObjects;

import javafx.scene.image.Image;

/**
 * A class for representing multiple images as an animation onscreen.
 *
 * Template from Youtuber ForeignGuyMike, in his video at the URL:
 *     https://www.youtube.com/watch?v=zUOkojY_Ylo&list=PL-2t7SM0vDfcIedoMIghzzgQqZq45jYGv&index=4
 */
public class SpriteAnimation {

    private Image[] frames;
    private int currentFrame;

    private long startTime;
    private long frameDelay;

    private boolean playedOnce;

    public SpriteAnimation() {
        playedOnce = false;
    }

    /**
     * Sets up the frames to be displayed on the game screen.
     * @param frames an array of images to be displayed.
     */
    public void setFrames(Image[] frames) {
        if (frames == this.frames) {
            return;
        }
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    /**
     * Sets the frame delay.
     * @param d desired frame delay
     */
    public void setFrameDelay(long d) { frameDelay = d; }

    /**
     * Sets the current frame.
     * @param i the desired frame.
     */
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

    /**
     * Gets the image being displayed for the current frame.
     * @return image being displayed.
     */
    public Image getImage() { return frames[currentFrame]; }
}
