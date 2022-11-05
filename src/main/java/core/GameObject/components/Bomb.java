package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Scenes.Stats;
import core.Window.Sound;
import core.Window.Window;
import util.Const;
import util.Prefabs;
import util.Box2D;

import java.util.List;
import java.util.Map;

public class Bomb extends Component {
    private double countDownTime = 3.0;
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private char[][] map = null;
    private boolean[][] placedBombs;
    private PlayScene scene = null;
    private Box2D box2d = null;

    public Bomb() {
    }

    @Override
    public void start() {
        scene = (PlayScene) Window.getCurrentScene();
        typeListMap = scene.getTypeListMap();
        map = scene.getMap();
        placedBombs = scene.getPlacedBombs();
        box2d = gameObject.getTransform().getPosition();
    }

    private void addFlame(double x, double y) {
        GameObject newFlame = Prefabs.generateFlame();
        newFlame.setTransform(new Transform(new Box2D(x + 8, y + 8, Const.FLAME_SIZE, Const.FLAME_SIZE), Const.FLAME_ZINDEX));
        scene.addGameObject(newFlame);
    }

    @Override
    public void update(double dt) {
        countDownTime -= dt;
        map[box2d.getCoordY()][box2d.getCoordX()] = 'o';
        List<GameObject> flame = typeListMap.get(ObjectType.FLAME);
        for (GameObject obj : flame) {
            if (Collision.boxCollision(obj.getTransform().getPosition(), box2d))
                countDownTime = Math.min(0.05, countDownTime);
        }
        if (countDownTime <= 0) {
            // spawn explosion
            gameObject.setAlive(false);
            Stats.increaseBombNumber();
            int i = box2d.getCoordY();
            int j = box2d.getCoordX();
            map[i][j] = ' ';
            placedBombs[i][j] = false;
            addFlame(j * Const.TILE_W, i * Const.TILE_H);
            Sound.play(Const.EXPLOSION_SOUND);
            int flameLength = Stats.get().getFlameSize();
            int ip, jp;
            //up
            for (ip = i - 1; ip >= Math.max(i - flameLength, 0); ip--)
                if (map[ip][j] != '#' && !isBreakable(map[ip][j])) {
                    addFlame(j * Const.TILE_W, ip * Const.TILE_H);
                } else break;
            if (ip >= Math.max(i - flameLength, 0) && isBreakable(map[ip][j])) {
                addFlame(j * Const.TILE_W, ip * Const.TILE_H);
                if (isHidingItem(map[ip][j]))
                    addItem(ip, j);
            }
            //down
            for (ip = i + 1; ip <= Math.min(i + flameLength, 12); ip++)
                if (map[ip][j] != '#' && !isBreakable(map[ip][j])) {
                    addFlame(j * Const.TILE_W, ip * Const.TILE_H);
                } else break;
            if (ip <= Math.min(i + flameLength, 12) && isBreakable(map[ip][j])) {
                addFlame(j * Const.TILE_W, ip * Const.TILE_H);
                if (isHidingItem(map[ip][j]))
                    addItem(ip, j);
            }
            //left
            for (jp = j - 1; jp >= Math.max(j - flameLength, 0); jp--)
                if (map[i][jp] != '#' && !isBreakable(map[i][jp])) {
                    addFlame(jp * Const.TILE_W, i * Const.TILE_H);
                } else break;
            if (jp >= Math.max(j - flameLength, 0) && isBreakable(map[i][jp])) {
                addFlame(jp * Const.TILE_W, i * Const.TILE_H);
                if (isHidingItem(map[i][jp]))
                    addItem(i, jp);
            }
            //right
            for (jp = j + 1; jp <= Math.min(j + flameLength, 20); jp++)
                if (map[i][jp] != '#' && !isBreakable(map[i][jp])) {
                    addFlame(jp * Const.TILE_W, i * Const.TILE_H);
                } else break;
            if (jp <= Math.min(j + flameLength, 20) && isBreakable(map[i][jp])) {
                addFlame(jp * Const.TILE_W, i * Const.TILE_H);
                if (isHidingItem(map[i][jp]))
                    addItem(i, jp);
            }
        }
    }

    public boolean isHidingItem(char x) {
        return x == 'B' || x == 'F' || x == 'S' || x == 'X';
    }

    public boolean isBreakable(char x) {
        return x == '*' || isHidingItem(x);
    }

    public void addItem(int i, int j) {
        String type = "PUBomb";
        if (map[i][j] == 'B') type = "PUBomb";
        if (map[i][j] == 'F') type = "PUFlame";
        if (map[i][j] == 'S') type = "PUSpeed";
        if (map[i][j] == 'X') type = "Portal";
        map[i][j] = ' ';
        GameObject item = Prefabs.generateItem(type);
        item.setTransform(new Transform(new Box2D(Const.TILE_W * j + 7, Const.TILE_H * i, 38, 56, 5, 4), 0));
        scene.addGameObject(item);
    }
}
