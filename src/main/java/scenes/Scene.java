package scenes;

import java.awt.Graphics2D;

public abstract class Scene {
    
    public Scene() {}
    
    public void init() {}
    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2D);
}
