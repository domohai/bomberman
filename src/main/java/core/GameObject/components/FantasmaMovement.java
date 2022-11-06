package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Box2D;
import util.Const;
import util.PathFinder;

import java.util.List;
import java.util.Map;

public class FantasmaMovement extends Component {
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private Box2D box2d = null;
    private char[][] map;
    private int dir;
    private int prevDir;
    private boolean wasCollided = false;
    private double sp;
    private Box2D playerBox;//for calculate distance(player,bot)
    private static final double RANGE = 500;

    public FantasmaMovement() {
    }

    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
        box2d = gameObject.getTransform().getPosition();
        PlayScene scene = (PlayScene) Window.getCurrentScene();
        map = scene.getMap();
        typeListMap = scene.getTypeListMap();
        playerBox = typeListMap.get(ObjectType.PLAYER).get(0).getTransform().getPosition();
    }


    @Override
    public void update(double dt) {
        map[box2d.getCoordY()][box2d.getCoordX()] = ' ';
        prevDir = dir;
        sp = (Const.PLAYER_SPEED / 2.0 * dt);
        dir = 0;
        if (box2d.euclideanDistance(playerBox) <= RANGE) {
            dir = PathFinder.pathFinderBFS(box2d.getCoordY(), box2d.getCoordX(), map, true);
        }
        //make the bot turn only when it is able to
        if (!box2d.isInbound()) {
            dir = prevDir;
        }
        switch (dir) {
            case 1:
                stateMachine.changeState("runUp");
                wasCollided = Collision.mapObject(box2d, 0, -sp, map, true);
                previousDirection = Direction.UP;
                break;
            case 2:
                stateMachine.changeState("runDown");
                wasCollided = Collision.mapObject(box2d, 0, sp, map, true);
                previousDirection = Direction.DOWN;
                break;
            case 3:
                stateMachine.changeState("runLeft");
                wasCollided = Collision.mapObject(box2d, -sp, 0, map, true);
                previousDirection = Direction.LEFT;
                break;
            case 4:
                stateMachine.changeState("runRight");
                wasCollided = Collision.mapObject(box2d, sp, 0, map, true);
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
        map[box2d.getCoordY()][box2d.getCoordX()] = '3';
        List<GameObject> flameList = typeListMap.get(ObjectType.FLAME);
        for (GameObject flame : flameList) {
            if (Collision.movingObject(box2d, flame.getTransform().getPosition())) {
                gameObject.setAlive(false);
            }
        }
    }
}
