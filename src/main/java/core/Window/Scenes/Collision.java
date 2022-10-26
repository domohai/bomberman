package core.Window.Scenes;

import util.Const;
import util.Box2D;

public class Collision {

    // dx xor dy != 0
    public static void mapObject(Box2D box2d, double dx, double dy, char[][] map) {
        double pw = box2d.getWidth();
        double ph = box2d.getHeight();
        double x = box2d.getX() + box2d.getSpriteOffsetX();
        double y = box2d.getY() + box2d.getSpriteOffsetY();
        x += dx;
        y += dy;
        int c1 = (int) x / Const.TILE_W;
        int c2 = (int) (x + pw) / Const.TILE_W;
        int r1 = (int) y / Const.TILE_H;
        int r2 = (int) (y + ph) / Const.TILE_H;
        if (dx < 0 && (isAbleToCollide(map[r1][c1]) || isAbleToCollide(map[r2][c1]))) {
            x = (c1 + 1) * Const.TILE_W + 1;
        }
        if (dx > 0 && (isAbleToCollide(map[r1][c2]) || isAbleToCollide(map[r2][c2]))) {
            x = c2 * Const.TILE_W - pw - 1;
        }
        if (dy < 0 && (isAbleToCollide(map[r1][c1]) || isAbleToCollide(map[r1][c2]))) {
            y = (r1 + 1) * Const.TILE_H + 1;
        }
        if (dy > 0 && (isAbleToCollide(map[r2][c1]) || isAbleToCollide(map[r2][c2]))) {
            y = r2 * Const.TILE_H - ph - 1;
        }
        box2d.setX(x - box2d.getSpriteOffsetX());
        box2d.setY(y - box2d.getSpriteOffsetY());
        box2d.fixOutOfBound();
    }

    public static boolean movingObject(Box2D playerBox2d, Box2D botBox2D) {
        return boxCollision(playerBox2d, botBox2D);
    }

    public static boolean isAbleToCollide(char x) {
        return x == '#' || x == 'o' || x == '*';
    }
    public static boolean boxCollision(Box2D box1, Box2D box2) {
        double px = box1.getX() + box1.getSpriteOffsetX();
        double py = box1.getY() + box1.getSpriteOffsetY();
        double pw = box1.getWidth();
        double ph = box1.getHeight();

        double bx = box2.getX() + box2.getSpriteOffsetX();
        double by = box2.getY() + box2.getSpriteOffsetY();
        double bw = box2.getWidth();
        double bh = box2.getHeight();
        if (bx + bw < px || px + pw < bx) return false;
        if (by + bh < py || py + ph < by) return false;
        return true;
    }

    public static boolean sameTile(Box2D box1, Box2D box2) {
        return (box1.getCordX() == box2.getCordX()) && (box1.getCordY() == box2.getCordY());
    }
}
