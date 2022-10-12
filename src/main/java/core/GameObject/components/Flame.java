package core.GameObject.components;

public class Flame extends Component {
    private double countDownTime = 3.0;
    
    public Flame() {
    
    }
    
    @Override
    public void update(double dt) {
        countDownTime -= dt;
        if (countDownTime <= 0) {
            // todo
            gameObject.setAlive(false);
        }
    }
}
