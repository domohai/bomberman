package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Const;
import util.Box2D;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BoarGuardMovement extends Component {
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private Box2D box2d = null;
    private char[][] map;
    private int dir = 0;
    private final static Random rnd = new Random();

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
        if(dt != 0) return;
        map[box2d.getCoordY()][box2d.getCoordX()] = ' ';
        if (turnAble(box2d)) {
            int p = Math.abs(rnd.nextInt()) % 10;
            //60% will keep direction
//            if (p <= 5) dir = 0;//60% will stay still
            if (p == 6) dir = 1;
            if (p == 7) dir = 2;
            if (p == 8) dir = 3;
            if (p == 9) dir = 4;
        }
        switch (dir) {
            case 1:
                stateMachine.changeState("runUp");
                Collision.mapObject(box2d, 0, -(Const.PLAYER_SPEED * dt), map);
                previousDirection = Direction.UP;
                break;
            case 2:
                stateMachine.changeState("runDown");
                Collision.mapObject(box2d, 0, (Const.PLAYER_SPEED * dt), map);
                previousDirection = Direction.DOWN;
                break;
            case 3:
                stateMachine.changeState("runLeft");
                Collision.mapObject(box2d, -(Const.PLAYER_SPEED * dt), 0, map);
                previousDirection = Direction.LEFT;
                break;
            case 4:
                stateMachine.changeState("runRight");
                Collision.mapObject(box2d, (Const.PLAYER_SPEED * dt), 0, map);
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

    public boolean turnAble(Box2D box) {
        int i = box.getCoordY();
        int j = box.getCoordX();
        if (!box.isInbound()) return false;
        return (map[i - 1][j] == ' ' || map[i + 1][j] == ' ' || map[i][j - 1] == ' ' || map[i][j + 1] == ' ');
    }
}
