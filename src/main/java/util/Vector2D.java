package util;

public class Vector2D {
    private double x;
    private double y;
    
    public Vector2D() {
        this(0.0, 0.0);
    }
    
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
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
