package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Scenes.Stats;
import core.Window.Sound;
import core.Window.Window;
import util.Const;

public class Item extends Component {
    private int type; // 1 2 3 4 = bomb, flame, speed, portal
    private transient GameObject player = null;
    
    public Item() {
    }
    
    public Item(String typeName) {
        type = switch (typeName) {
            case "PUBomb" -> 1;
            case "PUFlame" -> 2;
            case "PUSpeed" -> 3;
            case "Portal" -> 4;
            default -> 0;
        };
    }
    
    @Override
    public void start() {
        player = ((PlayScene) Window.getCurrentScene()).getTypeListMap().get(ObjectType.PLAYER).get(0);
    }
    
    @Override
    public void update(double dt) {
        if (player == null) return;
        if (Collision.boxCollision(player.getTransform().getPosition(), gameObject.getTransform().getPosition())) {
            switch (type) {
                case 1 -> {
                    Stats.increaseBombNumber();
                    Sound.play(Const.ITEM_SOUND);
                }
                case 2 -> {
                    Stats.increaseFlameSize();
                    Sound.play(Const.ITEM_SOUND);
                }
                case 3 -> {
                    Stats.increaseSpeedMultiplier();
                    Sound.play(Const.ITEM_SOUND);
                }
            }
            gameObject.setAlive(false);
        }
    }

    public int getType() {
        return type;
    }
}
