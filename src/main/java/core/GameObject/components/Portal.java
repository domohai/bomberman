package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.Window.Scenes.PlayScene;
import core.Window.Scenes.Stats;
import core.Window.Sound;
import core.Window.Window;
import util.Box2D;
import util.Const;
import java.util.List;
import java.util.Map;

public class Portal extends Component {
    private transient StateMachine stateMachine = null;
    private transient Box2D box2d = null;
    private transient Box2D playerBox = null;
    private transient PlayScene scene = null;
    private transient Map<ObjectType, List<GameObject>> typeListMap = null;

    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
        box2d = gameObject.getTransform().getPosition();
        scene = (PlayScene) Window.getCurrentScene();
        typeListMap = scene.getTypeListMap();
        playerBox = typeListMap.get(ObjectType.PLAYER).get(0).getTransform().getPosition();
    }

    @Override
    public void update(double dt) {
        if(stateMachine.getCurrentState().getState().equals("Close")) {
            if (typeListMap.get(ObjectType.BOT).size() < 1) {
                stateMachine.changeState("Open");
                Sound.play(Const.DOOR_SOUND);
            }
        } else {
            if(box2d.sameTile(playerBox)) {
                gameObject.setAlive(false);
                Stats.setLevel(Stats.currentLevel() + 1);
                Stats.setNextLevel(true);
            }
        }
    }
}
