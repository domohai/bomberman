package core.Window.Scenes;

import util.Const;
import util.Box2D;

public class Collision {

    // dx xor dy != 0
    public static void stillObject(Box2D box2d, double dx, double dy, char[][] map) {
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
        if (dx < 0 && (map[r1][c1] == '#' || map[r2][c1] == '#')) {
            x = (c1 + 1) * Const.TILE_W + 1;
        }
        if (dx > 0 && (map[r1][c2] == '#' || map[r2][c2] == '#')) {
            x = c2 * Const.TILE_W - pw - 1;
        }
        if (dy < 0 && (map[r1][c1] == '#' || map[r1][c2] == '#')) {
            y = (r1 + 1) * Const.TILE_H + 1;
        }
        if (dy > 0 && (map[r2][c1] == '#' || map[r2][c2] == '#')) {
            y = r2 * Const.TILE_H - ph - 1;
        }
        box2d.setX(x - box2d.getSpriteOffsetX());
        box2d.setY(y - box2d.getSpriteOffsetY());
        box2d.fixOutOfBound();
    }


    public static boolean movingObject(Box2D playerBox2d, Box2D botBox2D) {
        return boxCollision(playerBox2d, botBox2D);
    }

    // dx xor dy != 0
    public static void unstableObject(Box2D playerBox2d, double dx, double dy, Box2D objBox2d) {
//        if (sameTile(playerBox2d, objBox2d)) return;
//        double pw = playerBox2d.getWidth();
//        double ph = playerBox2d.getHeight();
//        double px = playerBox2d.getX() + playerBox2d.getSpriteOffsetX();
//        double py = playerBox2d.getY() + playerBox2d.getSpriteOffsetY();
//
//        double ow = objBox2d.getWidth();
//        double oh = objBox2d.getHeight();
//        double ox = objBox2d.getX() + objBox2d.getSpriteOffsetX();
//        double oy = objBox2d.getY() + objBox2d.getSpriteOffsetY();
//
//        // check if collision when continue moving
//        Box2D moved = new Box2D(px + dx, py + dy, pw, ph, playerBox2d.getSpriteOffsetX(), playerBox2d.getSpriteOffsetY());
//        if (!boxCollision(moved, objBox2d)) {
//            playerBox2d.setX(px + dx - playerBox2d.getSpriteOffsetX());
//            playerBox2d.setY(py + dy - playerBox2d.getSpriteOffsetY());
//            return;
//        }
//        if (dx < 0) {
//            px = ox + ow + 1;
//        } else if (dx > 0) {
//            px = ox - pw - 1;
//        } else if (dy < 0) {
//            py = oy + oh + 1;
//        } else if (dy > 0) {
//            py = oy - ph - 1;
//        }
//
//        playerBox2d.setX(px - playerBox2d.getSpriteOffsetX());
//        playerBox2d.setY(py - playerBox2d.getSpriteOffsetY());
//        playerBox2d.fixOutOfBound();

    }
    public static void unstableObject(Box2D box2d, double dx, double dy, char[][] map) {
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
        if (dx < 0 && (map[r1][c1] == 'o' || map[r2][c1] == 'o')) {
            x = (c1 + 1) * Const.TILE_W + 1;
        }
        if (dx > 0 && (map[r1][c2] == 'o' || map[r2][c2] == 'o')) {
            x = c2 * Const.TILE_W - pw - 1;
        }
        if (dy < 0 && (map[r1][c1] == 'o' || map[r1][c2] == 'o')) {
            y = (r1 + 1) * Const.TILE_H + 1;
        }
        if (dy > 0 && (map[r2][c1] == 'o' || map[r2][c2] == 'o')) {
            y = r2 * Const.TILE_H - ph - 1;
        }
        box2d.setX(x - box2d.getSpriteOffsetX());
        box2d.setY(y - box2d.getSpriteOffsetY());
        box2d.fixOutOfBound();
    }

    public static boolean boxCollision(Box2D box1, Box2D box2) {
//        double px = box1.getCenterX();
//        double py = box1.getCenterY();
//        double pw = box1.getWidth();
//        double ph = box1.getHeight();
//
//        double bx = box2.getCenterX();
//        double by = box2.getCenterY();
//        double bw = box2.getWidth();
//        double bh = box2.getHeight();
//        return (Math.abs(px - bx) <= (pw + bw) / 2) && (Math.abs(py - by) <= (ph + bh) / 2);
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
