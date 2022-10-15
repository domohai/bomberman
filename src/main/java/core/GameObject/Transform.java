package core.GameObject;

import util.Box2D;

public class Transform {
    private Box2D position;
    private int zIndex;
    
    public Transform() {
        this(new Box2D(0, 0), 0);
    }
    
    public Transform(Box2D position, int zIndex) {
        this.position = position;
        this.zIndex = zIndex;
    }
    
    public Box2D getPosition() {
        return position;
    }
    
    public int getzIndex() {
        return zIndex;
    }
}
