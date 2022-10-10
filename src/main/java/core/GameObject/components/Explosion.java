package core.GameObject.components;

public class Explosion extends Component {
    private double countDownTime = 3.0;
    
    public Explosion() {
    
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
