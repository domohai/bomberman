package core.GameObject.components;

public class Item extends Component{
    private int type;//1 2 3 = bomb, flame, speed
    public Item(String typeName) {
        type = switch (typeName) {
            case "PUBomb" -> 1;
            case "PUFlame" -> 2;
            case "PUSpeed" -> 3;
            default -> 0;
        };
    }

    public int getType() {
        return type;
    }
}
