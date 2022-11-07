package core.Window.Scenes;

import util.Const;
import util.Box2D;

public class Collision {

    private static final int slideOffset = 16;

    // dx xor dy != 0
    public static boolean mapObject(Box2D box2d, double dx, double dy, char[][] map, boolean ghost) {
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
        boolean wasCollide = false;
        //check collide if continue to move
        if (dx < 0 && (isAbleToCollide(map[r1][c1], ghost) || isAbleToCollide(map[r2][c1], ghost))) {
            if (isAbleToCollide(map[r1][c1], ghost) && !isAbleToCollide(map[r2][c1], ghost) && y > (r1 + 1) * Const.TILE_H - slideOffset)
                y = (r1 + 1) * Const.TILE_H;
            else if (!isAbleToCollide(map[r1][c1], ghost) && isAbleToCollide(map[r2][c1], ghost) && y + ph - 1 < r2 * Const.TILE_H + slideOffset - 1)
                y = r2 * Const.TILE_H - 1 - ph;
            else {
                x = (c1 + 1) * Const.TILE_W + 1;
                wasCollide = true;
            }
        }
        if (dx > 0 && (isAbleToCollide(map[r1][c2], ghost) || isAbleToCollide(map[r2][c2], ghost))) {
            if (isAbleToCollide(map[r1][c2], ghost) && !isAbleToCollide(map[r2][c2], ghost) && y > (r1 + 1) * Const.TILE_H - slideOffset)
                y = (r1 + 1) * Const.TILE_H;
            else if (!isAbleToCollide(map[r1][c2], ghost) && isAbleToCollide(map[r2][c2], ghost) && y + ph - 1 < r2 * Const.TILE_H + slideOffset - 1)
                y = r2 * Const.TILE_H - 1 - ph;
            else {
                x = c2 * Const.TILE_W - 1 - pw;
                wasCollide = true;
            }
        }
        if (dy < 0 && (isAbleToCollide(map[r1][c1], ghost) || isAbleToCollide(map[r1][c2], ghost))) {
            if (isAbleToCollide(map[r1][c1], ghost) && !isAbleToCollide(map[r1][c2], ghost) && x > (c1 + 1) * Const.TILE_W - slideOffset)
                x = (c1 + 1) * Const.TILE_W;
            else if (!isAbleToCollide(map[r1][c1], ghost) && isAbleToCollide(map[r1][c2], ghost) && x + pw - 1 < c2 * Const.TILE_W + slideOffset - 1)
                x = c2 * Const.TILE_W - 1 - pw;
            else {
                y = (r1 + 1) * Const.TILE_H + 1;
                wasCollide = true;
            }
        }
        if (dy > 0 && (isAbleToCollide(map[r2][c1], ghost) || isAbleToCollide(map[r2][c2], ghost))) {
            if (isAbleToCollide(map[r2][c1], ghost) && !isAbleToCollide(map[r2][c2], ghost) && x > (c1 + 1) * Const.TILE_W - slideOffset)
                x = (c1 + 1) * Const.TILE_W;
            else if (!isAbleToCollide(map[r2][c1], ghost) && isAbleToCollide(map[r2][c2], ghost) && x + pw - 1 < c2 * Const.TILE_W + slideOffset - 1)
                x = c2 * Const.TILE_W - 1 - pw;
            else {
                y = r2 * Const.TILE_H - 1 - ph;
                wasCollide = true;
            }
        }
        box2d.setX(x - box2d.getSpriteOffsetX());
        box2d.setY(y - box2d.getSpriteOffsetY());
        box2d.fixOutOfBound();
        return wasCollide;
    }

    public static boolean movingObject(Box2D playerBox2d, Box2D botBox2D) {
        return boxCollision(playerBox2d, botBox2D);
    }

    public static boolean isAbleToCollide(char x, boolean ghost) {
        return x == '#' || (!ghost && (x == 'o' || x == '*' || x == 'B' || x == 'F' || x == 'S' || x == 'X'));
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



}
