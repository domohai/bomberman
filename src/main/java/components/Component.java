package components;

import core.GameObject;
import java.awt.Graphics2D;

public abstract class Component {
    /**
     * this gameObject is the gameObject that are having this component
     * it'll be helpful when we want components of the gameObject
     * to communicate with each other
     */
    protected transient GameObject gameObject = null;
    
    public void start() {} // initialize stuff in this function
    public void update(double dt) {}
    public void draw(Graphics2D g2D) {}
    
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
