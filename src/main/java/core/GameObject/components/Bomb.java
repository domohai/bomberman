package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Prefabs;
import util.Vector2D;

import java.util.List;
import java.util.Map;

public class Bomb extends Component {
    private double countDownTime = 3.0;
    private Map<ObjectType, List<GameObject>> gameObjects = null;
    private char[][] map = null;
    private PlayScene scene = null;
    
    public Bomb() {
    
    }
    
    @Override
    public void start() {
        scene = (PlayScene) Window.getCurrentScene();
        gameObjects = scene.getGameObject();
        map = scene.getMap();
    }
    
    @Override
    public void update(double dt) {
        countDownTime -= dt;
        if (countDownTime <= 0) {
            // spawn explosion
            GameObject newExplosion = Prefabs.generateExplosion();
            newExplosion.setTransform(new Transform(new Vector2D(gameObject.getPositionX(), gameObject.getPositionY()), 0));
            scene.addGameObject(newExplosion);
            gameObject.setAlive(false);
        }
    }
}
