package core.GameObject.components;

import core.KeyController;

import java.awt.event.KeyEvent;

public class PlayerMovement extends Component{

    private StateMachine stateMachine = null;
    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
    }
    @Override
    public void update(double dt) {
        if(KeyController.is_keyPressed(KeyEvent.VK_UP)){
            stateMachine.changeState("");
        }
        if(KeyController.is_keyPressed(KeyEvent.VK_DOWN)){
            stateMachine.changeState("");
        }
        if(KeyController.is_keyPressed(KeyEvent.VK_LEFT)){
            stateMachine.changeState("");
        }
        if(KeyController.is_keyPressed(KeyEvent.VK_RIGHT)){
            stateMachine.changeState("");
        }
    }
}
