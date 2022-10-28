package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Const;
import util.PathFinder;
import util.Box2D;

import java.util.List;
import java.util.Map;

public class RedLizardMovement extends Component {
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private Box2D box2d = null;
    private char[][] map;
    private int dir;
    private int prevDir;
    private double sp;

    public RedLizardMovement() {
    }

    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
        box2d = gameObject.getTransform().getPosition();
        PlayScene scene = (PlayScene) Window.getCurrentScene();
        typeListMap = scene.getTypeListMap();
        map = scene.getMap();
    }

    @Override
    public void update(double dt) {
        map[box2d.getCoordY()][box2d.getCoordX()] = ' ';
        prevDir = dir;
        dir = PathFinder.pathFinderBFS(box2d.getCoordY(), box2d.getCoordX(), map);
        sp = (Const.PLAYER_SPEED * dt);
        if (!box2d.isInbound()) {
            dir = prevDir;
        }
        switch (dir) {
            case 1:
                stateMachine.changeState("runUp");
                Collision.mapObject(box2d, 0, -sp, map);
                previousDirection = Direction.UP;
                break;
            case 2:
                stateMachine.changeState("runDown");
                Collision.mapObject(box2d, 0, sp, map);
                previousDirection = Direction.DOWN;
                break;
            case 3:
                stateMachine.changeState("runLeft");
                Collision.mapObject(box2d, -sp, 0, map);
                previousDirection = Direction.LEFT;
                break;
            case 4:
                stateMachine.changeState("runRight");
                Collision.mapObject(box2d, sp, 0, map);
                previousDirection = Direction.RIGHT;
                break;
            case 0:
                switch (previousDirection) {
                    case UP -> stateMachine.changeState("idleUp");
                    case DOWN -> stateMachine.changeState("idleDown");
                    case LEFT -> stateMachine.changeState("idleLeft");
                    case RIGHT -> stateMachine.changeState("idleRight");
                }
                break;
        }
        map[box2d.getCoordY()][box2d.getCoordX()] = '2';
        List<GameObject> flameList = typeListMap.get(ObjectType.FLAME);
        for (GameObject flame : flameList) {
            if (Collision.movingObject(box2d, flame.getTransform().getPosition())) {
                gameObject.setAlive(false);
            }
        }
    }
}
