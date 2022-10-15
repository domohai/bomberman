package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Const;
import util.Prefabs;
import util.Box2D;
import java.util.List;
import java.util.Map;

public class Bomb extends Component {
    private double countDownTime = 3.0;
    private Map<ObjectType, List<GameObject>> gameObjects = null;
    private char[][] map = null;
    private PlayScene scene = null;
    private Box2D box2d = null;

    public Bomb() {
    }

    @Override
    public void start() {
        scene = (PlayScene) Window.getCurrentScene();
        gameObjects = scene.getGameObject();
        map = scene.getMap();
        box2d = gameObject.getTransform().getPosition();
    }

    private void addFlame(double x, double y) {
        GameObject newFlame = Prefabs.generateFlame();
        newFlame.setTransform(new Transform(new Box2D(x + 8, y + 8), 0));
        scene.addGameObject(newFlame);
    }

    @Override
    public void update(double dt) {
        countDownTime -= dt;
        if (countDownTime <= 0) {
            // spawn explosion
            gameObject.setAlive(false);

            int i = (int) box2d.getY() / Const.TILE_H;
            int j = (int) box2d.getX() / Const.TILE_W;
            addFlame(j * Const.TILE_W, i * Const.TILE_H);
            int flameLength = 10; //=scene.getPlayerStat().getFlameLength();
            for (int ip = i + 1; ip <= Math.min(i + flameLength, 12); ip++)
                if (map[ip][j] != '#') {
                    addFlame(j * Const.TILE_W, ip * Const.TILE_H);
                } else break;
            for (int ip = i - 1; ip >= Math.max(i - flameLength, 0); ip--)
                if (map[ip][j] != '#') {
                    addFlame(j * Const.TILE_W, ip * Const.TILE_H);
                } else break;
            for (int jp = j + 1; jp <= Math.min(j + flameLength, 20); jp++)
                if (map[i][jp] != '#') {
                    addFlame(jp * Const.TILE_W, i * Const.TILE_H);
                } else break;
            for (int jp = j - 1; jp >= Math.max(j - flameLength, 0); jp--)
                if (map[i][jp] != '#') {
                    addFlame(jp * Const.TILE_W, i * Const.TILE_H);
                } else break;
        }
    }
}
