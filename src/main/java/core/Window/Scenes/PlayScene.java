package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.Transform;
import core.GameObject.components.SpriteSheet;
import util.AssetsPool;
import util.Const;
import util.Prefabs;
import util.Vector2D;

import java.awt.Graphics2D;

public class PlayScene extends Scene {
    // test your code in this class
    private GameObject test;
    private char[][] map;
    
    public PlayScene() {
        super();
//        this.test = new GameObject();
    }
    
    @Override
    public void init() {
        test = Prefabs.generatePlayer();
        this.addGameObject(test);
    }
    
    @Override
    public void load_resources() {
        // sprite sheet must be load first
        SpriteSheet doctorBombRunUp = new SpriteSheet("src/main/resources/DoctorBombUp.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunUp.getPath(), doctorBombRunUp);
        SpriteSheet doctorBombRunLeft = new SpriteSheet("src/main/resources/DoctorBombLeft.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunLeft.getPath(), doctorBombRunLeft);
        SpriteSheet doctorBombRunRight = new SpriteSheet("src/main/resources/DoctorBombRight.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunRight.getPath(), doctorBombRunRight);
        SpriteSheet doctorBombRunDown = new SpriteSheet("src/main/resources/DoctorBombDown.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunDown.getPath(), doctorBombRunDown);
        SpriteSheet wall = new SpriteSheet("src/main/resources/Wall.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(wall.getPath(), wall);
        loadMap();
    }
    
    @Override
    public void update(double dt) {
        for (GameObject g : gameObjects) {
            g.update(dt);
        }
//        System.out.println(Const.SCREEN_WIDTH + ", " + Const.SCREEN_HEIGHT);
    }
    
    @Override
    public void draw(Graphics2D g2D) {
        renderer.render(g2D);
    }

    private void loadMap() {
        map = Prefabs.loadMap("src/main/resources/Level1.txt");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '#') {
                    GameObject block = Prefabs.generateBlock();
                    block.setTransform(new Transform(new Vector2D(Const.TILE_W * j, Const.TILE_H * i), 0));
                    addGameObject(block);
                }
            }
        }
    }

    public char[][] getMap() {
        return map;
    }
}
