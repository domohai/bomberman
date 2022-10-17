package core.GameObject.components;

import util.Const;

public class Flame extends Component {
    private double countDownTime = 18 * Const.DEFAULT_FRAME_TIME;
    
    public Flame() {
    }
    
    @Override
    public void update(double dt) {
        countDownTime -= dt;
        if (countDownTime <= 0) {
            gameObject.setAlive(false);
        }
    }
}
