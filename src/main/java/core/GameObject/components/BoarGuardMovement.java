package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Const;
import util.Box2D;
import util.RandomMove;
import java.util.List;
import java.util.Map;

public class BoarGuardMovement extends Component {
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private Box2D box2d = null;
    private char[][] map;
    private int dir = 0;
    private boolean wasCollided = false;
    private double sp;

    public BoarGuardMovement() {
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
        int turnBit = RandomMove.getTurnBit(box2d,map);
        if (turnBit != 0) {
            dir = RandomMove.randomDirection(dir,wasCollided,turnBit);
        }
        sp = Const.PLAYER_SPEED * 0.8 * dt;
        switch (dir) {
            case 1:
                stateMachine.changeState("runUp");
                wasCollided = Collision.mapObject(box2d, 0, -sp, map, false);
                previousDirection = Direction.UP;
                break;
            case 2:
                stateMachine.changeState("runDown");
                wasCollided = Collision.mapObject(box2d, 0, sp, map, false);
                previousDirection = Direction.DOWN;
                break;
            case 3:
                stateMachine.changeState("runLeft");
                wasCollided = Collision.mapObject(box2d, -sp, 0, map, false);
                previousDirection = Direction.LEFT;
                break;
            case 4:
                stateMachine.changeState("runRight");
                wasCollided = Collision.mapObject(box2d, sp, 0, map, false);
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
        box2d.updateCenter();
        map[box2d.getCoordY()][box2d.getCoordX()] = '1';
        List<GameObject> flameList = typeListMap.get(ObjectType.FLAME);
        for (GameObject flame : flameList) {
            if (Collision.movingObject(box2d, flame.getTransform().getPosition())) {
                gameObject.setAlive(false);
            }
        }
    }

}
