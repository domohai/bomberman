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
    }
    
    @Override
    public void init() {
        this.addGameObject(Prefabs.generatePlayer());
        this.addGameObject(Prefabs.generateBot());

    }
    
    @Override
    public void load_resources() {
        // sprite sheet must be load first
        loadSpriteSheet();
        loadMap();
    }
    
    @Override
    public void update(double dt) {
        for (GameObject g : gameObjects) {
            g.update(dt);
        }
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
                    if (block == null) {
                        System.out.println("Error when generate block");
                        return;
                    }
                    block.setTransform(new Transform(new Vector2D(Const.TILE_W * j, Const.TILE_H * i), 0));
                    addGameObject(block);
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
}
