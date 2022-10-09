package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.KeyController;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Const;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

public class PlayerMovement extends Component {
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Map<ObjectType, List<GameObject>> gameObjectMap = null;
    private Transform transform;
    private char[][] map;
    private int x = 0;
    private int y = 0;

    public PlayerMovement() {
    }

    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
        transform = gameObject.getTransform();
        PlayScene scene = (PlayScene) Window.getCurrentScene();
        map = scene.getMap();
        gameObjectMap = scene.getGameObject();
    }

    @Override
    public void update(double dt) {
        x = ((gameObject.getPositionY() + 32) / Const.TILE_H);
        y = ((gameObject.getPositionX() + 32) / Const.TILE_W);
        if (KeyController.is_keyPressed(KeyEvent.VK_UP)) {
            stateMachine.changeState("runUp");
            Collision.stillObject(transform.getPosition(), 0, -(Const.PLAYER_SPEED * dt), map);
            previousDirection = Direction.UP;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_DOWN)) {
            stateMachine.changeState("runDown");
            Collision.stillObject(transform.getPosition(), 0, (Const.PLAYER_SPEED * dt), map);
            previousDirection = Direction.DOWN;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_LEFT)) {
            stateMachine.changeState("runLeft");
            Collision.stillObject(transform.getPosition(), -(Const.PLAYER_SPEED * dt), 0, map);
            previousDirection = Direction.LEFT;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_RIGHT)) {
            stateMachine.changeState("runRight");
            Collision.stillObject(transform.getPosition(), (Const.PLAYER_SPEED * dt), 0, map);
            previousDirection = Direction.RIGHT;
        } else {
            switch (previousDirection) {
                case UP -> stateMachine.changeState("idleUp");
                case DOWN -> stateMachine.changeState("idleDown");
                case LEFT -> stateMachine.changeState("idleLeft");
                case RIGHT -> stateMachine.changeState("idleRight");
            }
        }
        map[x][y] = 'p';
        for (GameObject bot : gameObjectMap.get(ObjectType.MOVING)) {
            if (Collision.movingObject(gameObject, bot)) {
                System.out.println("colliding");
                gameObject.setAlive(false);
            }
        }
    }
}
