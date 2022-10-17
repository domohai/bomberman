package core.GameObject.components;

import util.Vector2D;

public class Bounds {
    private int width;
    private int height;
    private Vector2D center;
    public Bounds(){

    }
    public Bounds(int width, int height){
        this.width = width;
        this.height = height;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Vector2D getCenter() {
        return center;
    }

    public void setCenter(Vector2D center) {
        this.center = center;
    }

}
