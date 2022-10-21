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

public class BotMovement extends Component {
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private Box2D box2d = null;
    private char[][] map;

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
        map[box2d.getCordY()][box2d.getCordX()] = ' ';
        int dir = PathFinder.pathFinderBFS(box2d.getCordY(), box2d.getCordX(), map);
        double sp = (Const.PLAYER_SPEED * dt);
        if (dir == 3 || dir == 4) {
            if ((int) box2d.getY() > box2d.getCordY() * Const.TILE_H) {
                box2d.setY((int) box2d.getY() - sp);
                return;
            } else if ((int) box2d.getY() < box2d.getCordY() * Const.TILE_H) {
                box2d.setY((int) box2d.getY() + sp);
                return;
            }
        }
        if (dir == 1 || dir == 2) {
            if ((int) box2d.getX() > box2d.getCordX() * Const.TILE_W) {
                box2d.setX((int) box2d.getX() - sp);
                return;
            } else if ((int) box2d.getX() < box2d.getCordX() * Const.TILE_W) {
                box2d.setX((int) box2d.getX() + sp);
                return;
            }
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
        map[box2d.getCordY()][box2d.getCordX()] = 'b';
        List<GameObject> flameList = typeListMap.get(ObjectType.FLAME);
        for (GameObject flame : flameList) {
            if (Collision.movingObject(box2d, flame.getTransform().getPosition())) {
                gameObject.setAlive(false);
            }
        }
    }
}
