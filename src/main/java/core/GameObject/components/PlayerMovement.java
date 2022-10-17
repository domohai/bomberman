package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.KeyController;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Const;
import util.Prefabs;
import util.Box2D;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

public class PlayerMovement extends Component {
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Map<ObjectType, List<GameObject>> typeListMap = null;
    private Box2D box2d;
    private char[][] map;
    private PlayScene scene = null;
    private double cooldown = 1.0;

    public PlayerMovement() {
    }

    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
        box2d = gameObject.getTransform().getPosition();
        scene = (PlayScene) Window.getCurrentScene();
        map = scene.getMap();
        typeListMap = scene.getTypeListMap();
    }

    @Override
    public void update(double dt) {
        if (KeyController.is_keyPressed(KeyEvent.VK_UP)) {
            stateMachine.changeState("runUp");
            Collision.stillObject(box2d, 0, -(Const.PLAYER_SPEED * dt), map);
            previousDirection = Direction.UP;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_DOWN)) {
            stateMachine.changeState("runDown");
            Collision.stillObject(box2d, 0, (Const.PLAYER_SPEED * dt), map);
            previousDirection = Direction.DOWN;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_LEFT)) {
            stateMachine.changeState("runLeft");
            Collision.stillObject(box2d, -(Const.PLAYER_SPEED * dt), 0, map);
            previousDirection = Direction.LEFT;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_RIGHT)) {
            stateMachine.changeState("runRight");
            Collision.stillObject(box2d, (Const.PLAYER_SPEED * dt), 0, map);
            previousDirection = Direction.RIGHT;
        } else {
            switch (previousDirection) {
                case UP -> stateMachine.changeState("idleUp");
                case DOWN -> stateMachine.changeState("idleDown");
                case LEFT -> stateMachine.changeState("idleLeft");
                case RIGHT -> stateMachine.changeState("idleRight");
            }
        }
        box2d.updateCenter();
        int i = (int) box2d.getCenterY() / Const.TILE_H;
        int j = (int) box2d.getCenterX() / Const.TILE_W;
        // check if space key is pressed
        cooldown -= dt;
        if (cooldown <= 0 && KeyController.is_keyPressed(KeyEvent.VK_SPACE)) {
            GameObject newBomb = Prefabs.generateBomb();
            newBomb.setTransform(new Transform(new Box2D(j * Const.TILE_W + (Const.HALF_TILE_W - Const.HALF_BOMB_W),
                    i * Const.TILE_H + (Const.HALF_TILE_H - Const.HALF_BOMB_H)), -1));
            scene.addGameObject(newBomb);
            cooldown = 2.0;
        }
        map[i][j] = 'p';
        List<GameObject> botList = gameObjectMap.get(ObjectType.MOVING);
        for (GameObject bot : botList) {
            if (Collision.movingObject(box2d, bot.getTransform().getPosition())) {
                gameObject.setAlive(false);
            }
        }
        for (GameObject flame : typeListMap.get(ObjectType.FLAME)) {
            if (Collision.movingObject(box2d, flame.getTransform().getPosition())) {
                System.out.println("colliding");
                gameObject.setAlive(false);
            }
        }
    }
}
