package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.GameObject.components.SpriteSheet;
import util.AssetsPool;
import util.Const;
import util.Prefabs;
import util.Vector2D;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayScene extends Scene {
    // test your code in this class
    private char[][] map;
    private Map<ObjectType, List<GameObject>> gameObject;
    private List<GameObject> toBeRemove;
    
    public PlayScene() {
        super();
        toBeRemove = new ArrayList<>();
        gameObject = new HashMap<>();
        gameObject.put(ObjectType.PLAYER, new ArrayList<>());
        gameObject.put(ObjectType.MOVING, new ArrayList<>());
        gameObject.put(ObjectType.UNSTABLE, new ArrayList<>());
        gameObject.put(ObjectType.OTHER, new ArrayList<>());
    }
    
    @Override
    public void init() {
        GameObject player = Prefabs.generatePlayer();
        if (player == null) {
            System.out.println("Can not generate player!");
            return;
        }
        addGameObject(player);
        GameObject bot = Prefabs.generateBot();
        if (bot == null) {
            System.out.println("Can not generate bot!");
            return;
        }
        bot.setTransform(new Transform(new Vector2D(64 * 3, 64 * 4), 0));
        addGameObject(bot);
    }
    
    @Override
    public void start() {
        for (ObjectType type : gameObject.keySet()) {
            for (GameObject g : gameObject.get(type)) {
                g.start();
            }
        }
    }
    
    @Override
    public void load_resources() {
        // sprite sheet must be load first
        loadSpriteSheet();
        loadMap();
    }
    
    @Override
    public void update(double dt) {
        // reset the map
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != '#' && map[i][j] != '*' && map[i][j] != ' ') {
                    map[i][j] = ' ';
                }
            }
        }
        for (ObjectType type : gameObject.keySet()) {
            for (GameObject g : gameObject.get(type)) {
                g.update(dt);
                if (!g.isAlive()) toBeRemove.add(g);
            }
        }
        if (toBeRemove.size() > 0) {
            for (GameObject g : toBeRemove) {
                remove(g);
            }
            toBeRemove.clear();
        }
    }
    
    @Override
    public void draw(Graphics2D g2D) {
        renderer.render(g2D);
    }
    
    @Override
    public void addGameObject(GameObject object) {
        gameObject.get(object.getType()).add(object);
        if (isRunning) object.start();
        renderer.submit(object);
    }
    
    public void remove(GameObject g) {
        gameObject.get(g.getType()).remove(g);
        renderer.remove(g);
    }

    private void loadMap() {
        map = Prefabs.loadMap("src/main/resources/Level1.txt");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '#') {
                    GameObject block = Prefabs.generateBlock();
                    if (block == null) {
                        System.out.println("Error when generate block");
                        return;
                    }
                    block.setTransform(new Transform(new Vector2D(Const.TILE_W * j, Const.TILE_H * i), 0));
                    block.setType(ObjectType.STILL);
                    super.addGameObject(block);
                }
            }
        }
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
    }

    public char[][] getMap() {
        return map;
    }
    
    public Map<ObjectType, List<GameObject>> getGameObject() {
        return gameObject;
    }
}
