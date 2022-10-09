package core.GameObject.components;

import util.Const;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class StateMachine extends Component {
    private Map<String, State> states;
    private transient State currentState;
    
    public StateMachine() {
        this.states = new HashMap<>();
        this.currentState = null;
    }
    
    public void changeState(String newStateTitle) {
        State newState = states.get(newStateTitle);
        if(newState != null) currentState = newState;
    }
    
    @Override
    public void update(double dt) {
        this.currentState.update(dt);
    }
    
    @Override
    public void draw(Graphics2D g2D) {
        g2D.drawImage(currentState.getCurrentFrameImage(), gameObject.getPositionX(), gameObject.getPositionY(), Const.TILE_W, Const.TILE_H, null);
    }
    
    /**
     * set default state.
     * @param state state's name.
     */
    public void setDefaultState(String state) {
        this.currentState = this.states.get(state);
    }
    
    public void addState(State state) {
        this.states.putIfAbsent(state.getState(), state);
    }
    
    public Map<String, State> getStates() {
        return states;
    }
}
