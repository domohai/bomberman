package core.Window.Scenes;

import core.GameObject.GameObject;
import util.Const;
import util.Vector2D;

public class Collision {
    public static final double pw = 32; //player size
    public static final double ph = 50;
    public static final double spx = 16; //sprite offset
    public static final double spy = 13;

    private static int c1,c2,r1,r2;
    //dx xor dy != 0
    public static void stillObject(Vector2D v, double dx, double dy, char[][] map) {
        double x = v.getX() + spx;
        double y = v.getY() + spy;
        x += dx;
        y += dy;
        c1 = (int) x / Const.TILE_W;
        c2 = (int) (x + pw) / Const.TILE_W;
        r1 = (int) y / Const.TILE_H;
        r2 = (int) (y + ph) / Const.TILE_H;
        if (dx < 0 && (map[r1][c1] == '#' || map[r2][c1] == '#')) {
            x = (c1 + 1) * Const.TILE_W;
        }
        if (dx > 0 && (map[r1][c2] == '#' || map[r2][c2] == '#')) {
            x = c2 * Const.TILE_W - pw - 1;
        }
        if (dy < 0 && (map[r1][c1] == '#' || map[r1][c2] == '#')) {
            y = (r1 + 1) * Const.TILE_H;
        }
        if (dy > 0 && (map[r2][c1] == '#' || map[r2][c2] == '#')) {
            y = r2 * Const.TILE_H - ph - 1;
        }
        v.setX(x - spx);
        v.setY(y - spy);
    }

    public static boolean movingObject(Vector2D v, double dx, double dy) {

    }
}
