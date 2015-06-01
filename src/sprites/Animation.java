package sprites;

import javafx.scene.image.Image;

/**
 * Created by Ben on 5/31/2015.
 */
public class Animation {

    private Image[] frames;
    private int currentFrame;

    private long startTime;
    private long frameDelay;

    private boolean playedOnce;

    public void Animation() {
        playedOnce = false;
    }

    public void setFrames(Image[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long d) { frameDelay = d; }
    public void setFrame(int i) { currentFrame = i; }

    public void update() {
        if (frameDelay == -1) { return; }

        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > frameDelay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public int getFrame() { return currentFrame; }
    public Image getImage() { return frames[currentFrame]; }
    public boolean hasPlayedOnce() { return playedOnce; }

}
