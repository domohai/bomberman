package core.GameObject.components;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.KeyController;
import core.Window.Scenes.Collision;
import core.Window.Scenes.PlayScene;
import core.Window.Scenes.Stats;
import core.Window.Sound;
import core.Window.Window;
import util.Const;
import util.Prefabs;
import util.Box2D;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

public class PlayerMovement extends Component {
    private transient StateMachine stateMachine = null;
    private Direction previousDirection = Direction.DOWN;
    private transient Map<ObjectType, List<GameObject>> typeListMap = null;
    private transient Box2D box2d = null;
    private transient char[][] map = null;
    private transient boolean[][] placedBombs = null;
    private transient PlayScene scene = null;
    private double sp = 0;
    private boolean bombCooldown = true;
    private static boolean debug = true;

    public PlayerMovement() {
    }

    @Override
    public void start() {
        stateMachine = gameObject.getComponent(StateMachine.class);
        box2d = gameObject.getTransform().getPosition();
        scene = (PlayScene) Window.getCurrentScene();
        map = scene.getMap();
        typeListMap = scene.getTypeListMap();
        placedBombs = scene.getPlacedBombs();
    }

    @Override
    public void update(double dt) {
        map[box2d.getCoordY()][box2d.getCoordX()] = ' ';
        sp = (Const.PLAYER_SPEED * dt) * Stats.get().getSpeedMultiplier();
        if (KeyController.is_keyPressed(KeyEvent.VK_UP)) {
            stateMachine.changeState("runUp");
            Collision.mapObject(box2d, 0, -sp, map, false);
            previousDirection = Direction.UP;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_DOWN)) {
            stateMachine.changeState("runDown");
            Collision.mapObject(box2d, 0, sp, map, false);
            previousDirection = Direction.DOWN;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_LEFT)) {
            stateMachine.changeState("runLeft");
            Collision.mapObject(box2d, -sp, 0, map, false);
            previousDirection = Direction.LEFT;
        } else if (KeyController.is_keyPressed(KeyEvent.VK_RIGHT)) {
            stateMachine.changeState("runRight");
            Collision.mapObject(box2d, sp, 0, map, false);
            previousDirection = Direction.RIGHT;
        } else {
            switch (previousDirection) {
                case UP -> stateMachine.changeState("idleUp");
                case DOWN -> stateMachine.changeState("idleDown");
                case LEFT -> stateMachine.changeState("idleLeft");
                case RIGHT -> stateMachine.changeState("idleRight");
            }
        }
        box2d.updateCenter();
        int i = box2d.getCoordY();
        int j = box2d.getCoordX();
        if (map[i][j] == ' ')
            map[i][j] = 'p';

        if (bombCooldown && !placedBombs[i][j] && Stats.get().getBombNumber() > 0 && KeyController.is_keyPressed(KeyEvent.VK_SPACE)) {
            placedBombs[i][j] = true;
            GameObject newBomb = Prefabs.generateBomb();
            newBomb.setTransform(new Transform(new Box2D(j * Const.TILE_W + (Const.HALF_TILE_W - Const.HALF_BOMB_W),
                    i * Const.TILE_H + (Const.HALF_TILE_H - Const.HALF_BOMB_H),
                    Const.BOMB_WIDTH,
                    Const.BOMB_HEIGHT), -1));
            scene.addGameObject(newBomb);
            Stats.decreaseBombNumber();
            bombCooldown = false;
        }
        if(!KeyController.is_keyPressed(KeyEvent.VK_SPACE))
            bombCooldown = true;
        List<GameObject> botList = typeListMap.get(ObjectType.BOT);
        for (GameObject bot : botList) {
            if (Collision.movingObject(box2d, bot.getTransform().getPosition())) {
                die();
            }
        }
        List<GameObject> flameList = typeListMap.get(ObjectType.FLAME);
        for (GameObject flame : flameList) {
            if (Collision.movingObject(box2d, flame.getTransform().getPosition())) {
                die();
            }
        }

        if(KeyController.is_keyPressed(KeyEvent.VK_ENTER)) {
            if (debug) {
//                printDebug(map);
                Stats.setLevel(Stats.currentLevel() + 1);
                scene.change_map();
                debug = false;
            }
        } else debug = true;
    }

    private void die() {
        gameObject.setAlive(false);
        Sound.play(Const.DIE_SOUND);
        Stats.decreaseHP();
        if (Stats.get().getHP() <= 0) Stats.setLose(true);
    }

    private void printDebug(char[][] map) {
        for (char[] chars : map) {
            for (int j = 0; j < chars.length; j++) System.out.print(chars[j]);
            System.out.println();
        }
    }
}
