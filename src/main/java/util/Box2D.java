package util;

public class Box2D {
    private double x, y; //sprite position
    private double width, height;
    private double spriteOffsetX;
    private double spriteOffsetY;
    private double centerX, centerY;
    private double wAndOffsetX, hAndOffsetY; // less calculation

    public Box2D() {
        this(0, 0, 0, 0);
    }

    public Box2D(double x, double y, double width, double height) {
        this(x, y, width, height, 0, 0);
    }

    public Box2D(double x, double y, double width, double height, double spriteOffsetX, double spriteOffsetY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteOffsetX = spriteOffsetX;
        this.spriteOffsetY = spriteOffsetY;
        this.wAndOffsetX = spriteOffsetX + width / 2;
        this.hAndOffsetY = spriteOffsetY + height / 2;
        updateCenter();
    }

    public void updateCenter() {
        centerX = x + wAndOffsetX;
        centerY = y + hAndOffsetY;
    }

    public void fixOutOfBound() {
        x = Math.max(x, 0);
        x = Math.min(x, Const.SCREEN_WIDTH - width);
        y = Math.max(y, 0);
        y = Math.min(y, Const.SCREEN_HEIGHT - height - 64);
        updateCenter();
    }

    public boolean isInbound() {
        int tileX = getCoordX() * Const.TILE_W;
        int tileY = getCoordY() * Const.TILE_H;

        if (x + spriteOffsetX < tileX || y + spriteOffsetX < tileY) return false;
        if (x + spriteOffsetX + width > tileX + Const.TILE_W || y + spriteOffsetY + height > tileY + Const.TILE_H)
            return false;
        return true;
    }

    public double euclideanDistance(Box2D box) {
        return Math.sqrt((x - box.getX()) * (x - box.getX()) + (y - box.getY()) * (y - box.getY()));
    }
    public boolean sameTile(Box2D box) {
        return (getCoordX() == box.getCoordX()) && (getCoordY() == box.getCoordY());
    }
    public int getCoordX() {
        return (int) centerX / Const.TILE_W;
    }

    public int getCoordY() {
        return (int) centerY / Const.TILE_H;
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


    public double getCenterY() {
        return centerY;
    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
