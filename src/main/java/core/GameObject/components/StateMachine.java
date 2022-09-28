package core.GameObject.components;

import util.Const;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StateMachine extends Component {
    private Map<String, State> states;
    private transient State currentState;
    private Map<Trigger, String> triggerMap;
    
    public StateMachine() {
        this.states = new HashMap<>();
        this.triggerMap = new HashMap<>();
    }
    
    public void changeState(String triggerKey) {
        State newState = states.get(triggerMap.get(new Trigger(currentState.getState(), triggerKey)));
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
     *
     * @param state state's name.
     */
    public void setDefaultState(String state) {
        this.currentState = this.states.get(state);
    }
    
    public void addTriggerMap(String srcState, String trigger, String desState) {
        this.triggerMap.putIfAbsent(new Trigger(srcState, trigger), desState);
    }
    
    public void addState(State state) {
        this.states.putIfAbsent(state.getState(), state);
    }
    
    public Map<String, State> getStates() {
        return states;
    }
    
    private class Trigger {
        private String state;
        private String triggerKey;
        
        public Trigger() {
        }
        
        public Trigger(String state, String triggerKey) {
            this.state = state;
            this.triggerKey = triggerKey;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Trigger trigger = (Trigger) o;
            return Objects.equals(state, trigger.state) && Objects.equals(triggerKey, trigger.triggerKey);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(state, triggerKey);
        }
        
        public String getState() {
            return state;
        }
        
        public String getTriggerKey() {
            return triggerKey;
        }
    }
}
