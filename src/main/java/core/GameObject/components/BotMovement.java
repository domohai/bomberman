package core.GameObject.components;

import core.GameObject.Transform;
import core.KeyController;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import util.Const;
import util.Time;

import java.awt.event.KeyEvent;

public class BotMovement extends Component{
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Transform transform;
    private char[][] map;

    public BotMovement() {
    }

    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
        transform = gameObject.getTransform();
        PlayScene scene = (PlayScene) Window.getCurrentScene();
        map = scene.getMap();
    }

    private static int dir;
    private static double lastUpdTime;
    @Override
    public void update(double dt) {
        if(Time.getTime() - lastUpdTime >= 2) {
            dir = (int)Math.floor(Math.random()*5); //0 1 2 3 4 = idle up down left right
            lastUpdTime = Time.getTime();
        }
        switch (dir){
            case 1:
                stateMachine.changeState("runUp");
                Collision.stillObject(transform.getPosition(), 0, -(Const.PLAYER_SPEED * dt), map);
                previousDirection = Direction.UP;
                break;
            case 2:
                stateMachine.changeState("runDown");
                Collision.stillObject(transform.getPosition(), 0, (Const.PLAYER_SPEED * dt), map);
                previousDirection = Direction.DOWN;
                break;
            case 3:
                stateMachine.changeState("runLeft");
                Collision.stillObject(transform.getPosition(), -(Const.PLAYER_SPEED * dt), 0, map);
                previousDirection = Direction.LEFT;
                break;
            case 4:
                stateMachine.changeState("runRight");
                Collision.stillObject(transform.getPosition(), (Const.PLAYER_SPEED * dt), 0, map);
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
    }
}
