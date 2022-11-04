package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Scenes.Stats;
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
        newFlame.setTransform(new Transform(new Box2D(x + 8, y + 8, Const.FLAME_SIZE, Const.FLAME_SIZE), 0));
        scene.addGameObject(newFlame);
    }
    
    @Override
    public void update(double dt) {
        System.out.println("bomb up");
        countDownTime -= dt;
        map[box2d.getCoordY()][box2d.getCoordX()] = 'o';
        List<GameObject> flame = typeListMap.get(ObjectType.FLAME);
        for(GameObject obj : flame) {
            if(Collision.boxCollision(obj.getTransform().getPosition(),box2d))
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
            int flameLength = Stats.get().getFlameSize();
            int ip, jp;
            for (ip = i + 1; ip <= Math.min(i + flameLength, 12); ip++)
                if (map[ip][j] != '#' && map[ip][j] != '*') {
                    addFlame(j * Const.TILE_W, ip * Const.TILE_H);
                } else break;
            if (ip <= Math.min(i + flameLength, 12) && map[ip][j] == '*')
                addFlame(j * Const.TILE_W, ip * Const.TILE_H);
            for (ip = i - 1; ip >= Math.max(i - flameLength, 0); ip--)
                if (map[ip][j] != '#' && map[ip][j] != '*') {
                    addFlame(j * Const.TILE_W, ip * Const.TILE_H);
                } else break;
            if (ip >= Math.max(i - flameLength, 0) && map[ip][j] == '*')
                addFlame(j * Const.TILE_W, ip * Const.TILE_H);
            for (jp = j + 1; jp <= Math.min(j + flameLength, 20); jp++)
                if (map[i][jp] != '#' && map[i][jp] != '*') {
                    addFlame(jp * Const.TILE_W, i * Const.TILE_H);
                } else break;
            if (jp <= Math.min(j + flameLength, 20) && map[i][jp] == '*')
                addFlame(jp * Const.TILE_W, i * Const.TILE_H);
            for (jp = j - 1; jp >= Math.max(j - flameLength, 0); jp--)
                if (map[i][jp] != '#' && map[i][jp] != '*') {
                    addFlame(jp * Const.TILE_W, i * Const.TILE_H);
                } else break;
            if (jp >= Math.max(j - flameLength, 0) && map[i][jp] == '*')
                addFlame(jp * Const.TILE_W, i * Const.TILE_H);
            
        }
    }
}
