package util;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(){
        this.x = 0.0;
        this.y = 0.0;

    }
    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


}
