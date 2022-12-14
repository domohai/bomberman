package core.GameObject.components;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class State {
    private List<Frame> frames;
    private String state;
    private String path;
    private boolean loop = false;
    private transient int currentFrame = 0;
    private transient double tracker = 0.0;
    
    public State() {
        this("", "");
    }
    
    public State(String state, String path) {
        this.state = state;
        this.path = path;
        this.frames = new ArrayList<>();
    }
    
    public void update(double dt) {
        if (currentFrame < frames.size()) {
            tracker -= dt;
            if (tracker <= 0.0f) {
                if ((currentFrame != frames.size() - 1) || loop) {
                    currentFrame = (currentFrame + 1) % frames.size();
                }
                tracker = frames.get(currentFrame).getDisplayTime();
            }
        }
    }
    
    public BufferedImage getCurrentFrameImage() {
        return frames.get(currentFrame).getImage();
    }
    
    public void addFrame(Frame frame) {
        frames.add(frame);
    }
    
    public void setLoop(boolean setLoop) {
        this.loop = setLoop;
    }
    
    public String getState() {
        return this.state;
    }
    
    public List<Frame> getFrames() {
        return frames;
    }
    
    public String getPath() {
        return path;
    }
}
