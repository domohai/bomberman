package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.GameObject.components.Breakable;
import core.GameObject.components.SpriteSheet;
import util.AssetsPool;
import util.Const;
import util.Prefabs;
import util.Box2D;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayScene extends Scene {
    // test your code in this class
    private char[][] map;
    private Map<ObjectType, List<GameObject>> typeListMap;
    private List<GameObject> toBeRemove;

    public PlayScene() {
        super();
        toBeRemove = new ArrayList<>();
        typeListMap = new HashMap<>();
        for (ObjectType type : ObjectType.values()) {
            typeListMap.put(type, new ArrayList<>());
        }
    }


    @Override
    public void start() {
        for (ObjectType type : typeListMap.keySet()) {
            for (GameObject g : typeListMap.get(type)) {
                g.start();
            }
        }
        isRunning = true;
    }

    @Override
    public void load_resources() {
        // sprite sheet must be load first
        loadSpriteSheet();
        loadMap();
        load_sound();
    }

    @Override
    public void update(double dt) {
        for (ObjectType type : typeListMap.keySet()) {
            //if (type == ObjectType.MOVING) continue;
            List<GameObject> list = typeListMap.get(type);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).update(dt);
                if (!list.get(i).isAlive()) toBeRemove.add(list.get(i));
            }
        }
        if (toBeRemove.size() > 0) {
            for (GameObject g : toBeRemove) {
                remove(g);
            }
            toBeRemove.clear();
        }
//        if (KeyController.is_keyPressed(KeyEvent.VK_H)) {
//            Sound.play(Const.TEST_AUDIO, 0.05f);
//        }
    }

    @Override
    public void init() {
//        Sound.play(Const.BACKGROUND_AUDIO, 0.8f);
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.drawImage(Const.background, 0, 0, Const.background.getWidth(), Const.background.getHeight(), null);
        renderer.render(g2D);
    }

    @Override
    public void addGameObject(GameObject object) {
        typeListMap.get(object.getType()).add(object);
        if (isRunning) object.start();
        renderer.submit(object);
    }

    public void remove(GameObject g) {
        typeListMap.get(g.getType()).remove(g);
        renderer.remove(g);
    }

    private void loadMap() {
        map = Prefabs.loadMap("src/main/resources/Level0.txt");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '#') {
                    GameObject block = Prefabs.generateBlock("src/main/resources/Wall.png");
                    if (block == null) {
                        System.out.println("Error when generate block");
                        return;
                    }
                    block.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 64, 64), 0));
                    super.addGameObject(block);
                } else if (map[i][j] == '*') {
                    GameObject rock = Prefabs.generateBlock("src/main/resources/breakable_rock_large.png");
                    if (rock == null) {
                        System.out.println("Error when generate block");
                        return;
                    }
                    rock.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 64, 64), 0));
                    rock.setType(ObjectType.UNSTABLE);
                    rock.addComponent(new Breakable());
                    addGameObject(rock);
                }
                if (map[i][j] == 'p') {
                    GameObject player = Prefabs.generatePlayer();
                    if (player == null) {
                        System.out.println("Can not generate player!");
                        return;
                    }
                    // set position
                    player.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 30, 42, 16, 15), 0));
                    addGameObject(player);
                }
                if (map[i][j] == 'b') {
                    GameObject bot = Prefabs.generateBot();
                    if (bot == null) {
                        System.out.println("Can not generate bot!");
                        return;
                    }
                    // set position
                    bot.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 30, 42, 16, 15), 0));
                    addGameObject(bot);
                }
            }
        }
    }

    private void load_sound() {
//        AssetsPool.addAudio(Const.BACKGROUND_AUDIO, true);
//        AssetsPool.addAudio(Const.TEST_AUDIO, false);
    }

    private void loadSpriteSheet() {
        SpriteSheet doctorBombRunUp = new SpriteSheet("src/main/resources/DoctorBombUp.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunUp.getPath(), doctorBombRunUp);
        SpriteSheet doctorBombRunLeft = new SpriteSheet("src/main/resources/DoctorBombLeft.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunLeft.getPath(), doctorBombRunLeft);
        SpriteSheet doctorBombRunRight = new SpriteSheet("src/main/resources/DoctorBombRight.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunRight.getPath(), doctorBombRunRight);
        SpriteSheet doctorBombRunDown = new SpriteSheet("src/main/resources/DoctorBombDown.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunDown.getPath(), doctorBombRunDown);
        SpriteSheet redOverlordRunUp = new SpriteSheet("src/main/resources/RedOverlordUp.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(redOverlordRunUp.getPath(), redOverlordRunUp);
        SpriteSheet redOverlordRunLeft = new SpriteSheet("src/main/resources/RedOverlordLeft.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(redOverlordRunLeft.getPath(), redOverlordRunLeft);
        SpriteSheet redOverlordRunRight = new SpriteSheet("src/main/resources/RedOverlordRight.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(redOverlordRunRight.getPath(), redOverlordRunRight);
        SpriteSheet redOverlordRunDown = new SpriteSheet("src/main/resources/RedOverlordDown.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(redOverlordRunDown.getPath(), redOverlordRunDown);
        SpriteSheet wall = new SpriteSheet("src/main/resources/Wall.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(wall.getPath(), wall);
        SpriteSheet bomb = new SpriteSheet("src/main/resources/bomb_scaled.png", 0, 0, 40, 52, 6);
        AssetsPool.addSpriteSheet(bomb.getPath(), bomb);
        SpriteSheet explosion = new SpriteSheet("src/main/resources/Flame.png", 0, 0, 48, 48, 18);
        AssetsPool.addSpriteSheet(explosion.getPath(), explosion);
        SpriteSheet rock = new SpriteSheet("src/main/resources/breakable_rock_large.png", 0, 0, 52, 52, 1);
        AssetsPool.addSpriteSheet(rock.getPath(), rock);
    }

    public char[][] getMap() {
        return map;
    }

    public Map<ObjectType, List<GameObject>> getTypeListMap() {
        return typeListMap;
    }
}
