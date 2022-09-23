package core;

import util.Vector2D;

public class Transform {
    private Vector2D position;
    private int zIndex ;

    Transform(){
        this.zIndex = 0;
        this.position = new Vector2D(0,0);

    }
    Transform(Vector2D position, int zIndex){
        this.position = position;
        this.zIndex = zIndex;
    }

    /**
     * Set and get
     *
     */
    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }




}
