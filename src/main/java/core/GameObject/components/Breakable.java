package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Box2D;
import util.Const;
import util.Prefabs;

import java.util.List;
import java.util.Map;

public class Breakable extends Component {
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private char[][] map = null;
    private PlayScene scene = null;
    private Box2D box2d = null;

    public Breakable() {
    }

    @Override
    public void start() {
        scene = (PlayScene) Window.getCurrentScene();
        typeListMap = scene.getTypeListMap();
        map = scene.getMap();
        box2d = gameObject.getTransform().getPosition();
    }

    @Override
    public void update(double dt) {
        int i = box2d.getCoordY();
        int j = box2d.getCoordX();
        List<GameObject> flames = typeListMap.get(ObjectType.FLAME);
        for (GameObject flame : flames) {
            if (Collision.boxCollision(box2d, flame.getTransform().getPosition())) {
                gameObject.setAlive(false);
                if (isHidingItem(map[i][j]))
                    addItem(i, j);
                map[box2d.getCoordY()][box2d.getCoordX()] = ' ';
            }
        }
    }


    public boolean isHidingItem(char x) {
        return x == 'B' || x == 'F' || x == 'S' || x == 'X';
    }

    public void addItem(int i, int j) {
        String type = "PUBomb";
        if (map[i][j] == 'B') type = "PUBomb";
        if (map[i][j] == 'F') type = "PUFlame";
        if (map[i][j] == 'S') type = "PUSpeed";
        if (map[i][j] == 'X') type = "Portal";
        if (type.equals("Portal")) {
            GameObject portal = Prefabs.generatePortal();
            portal.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 64, 64, 0, 0), Const.ITEM_ZINDEX));
            scene.addGameObject(portal);
        } else {
            GameObject item = Prefabs.generateItem(type);
            item.setTransform(new Transform(new Box2D(Const.TILE_W * j + 7, Const.TILE_H * i, 38, 56, 5, 4), Const.ITEM_ZINDEX));
            scene.addGameObject(item);
        }
    }
}
