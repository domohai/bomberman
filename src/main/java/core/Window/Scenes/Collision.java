package core.Window.Scenes;

import util.Const;
import util.Box2D;

public class Collision {
    //dx xor dy != 0
    public static void stillObject(Box2D box2d, double dx, double dy, char[][] map) {
        double pw = box2d.getWidth();
        double ph = box2d.getHeight();
        double spx = box2d.getSpriteOffsetX();
        double spy = box2d.getSpriteOffsetY();
        double x = box2d.getX() + spx;
        double y = box2d.getY() + spy;
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
        box2d.setX(x - spx);
        box2d.setY(y - spy);
        box2d.updateCenter();
    }


    /*
    check surrounding tile
        ###
        #*#
        ###
    */
    /*
        Collide class:
            Vector2D for adjusted position
            Boolean for hit
            Boolean for enemy hit

     */
    public static boolean movingObject(Box2D playerBox2d, Box2D botBox2D) {
        // c1, c2 is the center of player
        double px = playerBox2d.getCenterX();
        double py = playerBox2d.getCenterY();
        double pw = playerBox2d.getWidth();
        double ph = playerBox2d.getHeight();
        // r1, r2 is the center of bot
        double bx = botBox2D.getCenterX();
        double by = botBox2D.getCenterY();
        double bw = botBox2D.getWidth();
        double bh = botBox2D.getHeight();
        return (Math.abs(px - bx) <= pw / 2 + bw / 2) && (Math.abs(py - by) <= ph / 2 + bh / 2);
    }
}
