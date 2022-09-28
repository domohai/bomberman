package core.GameObject.components;

import core.GameObject.Transform;
import core.KeyController;

import java.awt.event.KeyEvent;

public class PlayerMovement extends Component {
    private StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private Transform transform;
    
    public PlayerMovement() {}
    
    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
        transform = gameObject.getTransform();
    }
    
    @Override
    public void update(double dt) {
        if (KeyController.is_keyPressed(KeyEvent.VK_UP)) {
            stateMachine.changeState("runUp");
            transform.move(0, -(100 * dt));
            previousDirection = Direction.UP;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_DOWN)) {
            stateMachine.changeState("runDown");
            transform.move(0, (100 * dt));
            previousDirection = Direction.DOWN;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_LEFT)) {
            stateMachine.changeState("runLeft");
            transform.move(-(100*dt), 0);
            previousDirection = Direction.LEFT;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_RIGHT)) {
            stateMachine.changeState("runRight");
            transform.move((100*dt), 0);
            previousDirection = Direction.RIGHT;
        } else {
            switch (previousDirection) {
                case UP -> stateMachine.changeState("idleUp");
                case DOWN -> stateMachine.changeState("idleDown");
                case LEFT -> stateMachine.changeState("idleLeft");
                case RIGHT -> stateMachine.changeState("idleRight");
            }
        }
    }
}
