package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import java.util.List;
import java.util.Map;

public class Breakable extends Component {
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private PlayScene scene = null;
    
    public Breakable() {}
    
    @Override
    public void start() {
        scene = (PlayScene) Window.getCurrentScene();
        typeListMap = scene.getTypeListMap();
    }
    
    @Override
    public void update(double dt) {
        List<GameObject> flames = typeListMap.get(ObjectType.FLAME);
        for (GameObject flame : flames) {
            if (Collision.boxCollision(gameObject.getTransform().getPosition(), flame.getTransform().getPosition())) {
                gameObject.setAlive(false);
                char[][] map = scene.getMap();
                // update map before remove rock
                // spawn item
                
                map[gameObject.getTransform().getPosition().getCoordY()]
                    [gameObject.getTransform().getPosition().getCoordX()] = ' ';
            }
        }
    }
}
