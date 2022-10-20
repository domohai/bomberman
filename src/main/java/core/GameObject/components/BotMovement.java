package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Const;
import util.PathFinder;
import util.Time;
import util.Box2D;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class BotMovement extends Component {
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private Box2D box2d = null;
    private char[][] map;
    private int dir;
    private double lastUpdTime;

    public BotMovement() {
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
//        if (Time.getTime() - lastUpdTime >= 2) {
//            dir = (int) Math.floor(Math.random() * 5); //0 1 2 3 4 = idle up down left right
//            lastUpdTime = Time.getTime();
//        }
        dir = PathFinder.pathFinder(box2d.getCordX(),box2d.getCordY(),map);
        if(dir != 0)System.out.println(dir);
        switch (dir) {
            case 1:
                stateMachine.changeState("runUp");
                Collision.stillObject(box2d, 0, -(Const.PLAYER_SPEED * dt), map);
                previousDirection = Direction.UP;
                break;
            case 2:
                stateMachine.changeState("runDown");
                Collision.stillObject(box2d, 0, (Const.PLAYER_SPEED * dt), map);
                previousDirection = Direction.DOWN;
                break;
            case 3:
                stateMachine.changeState("runLeft");
                Collision.stillObject(box2d, -(Const.PLAYER_SPEED * dt), 0, map);
                previousDirection = Direction.LEFT;
                break;
            case 4:
                stateMachine.changeState("runRight");
                Collision.stillObject(box2d, (Const.PLAYER_SPEED * dt), 0, map);
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
        map[(int) box2d.getCenterY() / Const.TILE_H][(int) box2d.getCenterX() / Const.TILE_W] = 'b';
        List<GameObject> flameList = typeListMap.get(ObjectType.FLAME);
        for (GameObject flame : flameList) {
            if (Collision.movingObject(box2d, flame.getTransform().getPosition())) {
                gameObject.setAlive(false);
            }
        }
        List<GameObject> unstableObjectList = typeListMap.get(ObjectType.UNSTABLE);
        for (GameObject uObj : unstableObjectList) {
            if (Collision.movingObject(box2d, uObj.getTransform().getPosition())) {
                gameObject.setAlive(false);
            }
        }

    }
}
