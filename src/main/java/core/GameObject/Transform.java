package core.GameObject;

import util.Vector2D;

public class Transform {
    private Vector2D position;
    private int zIndex;
    
    public Transform() {
        this(new Vector2D(0, 0), 0);
    }
    
    public Transform(Vector2D position, int zIndex) {
        this.position = position;
        this.zIndex = zIndex;
    }
    
    public void move(double dx, double dy) {
        this.position.move(dx, dy);
    }
    
    public Vector2D getPosition() {
        return position;
    }
    
    public int getzIndex() {
        return zIndex;
    }
    
    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }
}
