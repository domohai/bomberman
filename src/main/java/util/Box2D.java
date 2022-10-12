package util;

public class Box2D {
    private double x; //sprite position
    private double y;
    private double width;
    private double height;
    private double spriteOffsetX;
    private double spriteOffsetY;
    private double centerX;
    private double centerY;

    public Box2D() {
        this(0, 0, 0, 0);
        updateCenter();
    }

    public Box2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Box2D(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        updateCenter();
    }

    public Box2D(double x, double y, double width, double height, double spriteOffsetX, double spriteOffsetY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteOffsetX = spriteOffsetX;
        this.spriteOffsetY = spriteOffsetY;
        updateCenter();
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void updateCenter() {
        centerX = x + width / 2;
        centerY = y + height / 2;
    }

    public double getSpriteOffsetX() {
        return spriteOffsetX;
    }

    public double getSpriteOffsetY() {
        return spriteOffsetY;
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

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
