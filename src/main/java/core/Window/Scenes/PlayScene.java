package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.components.SpriteSheet;
import util.AssetsPool;
import util.Prefabs;

import java.awt.Graphics2D;

public class PlayScene extends Scene {
    // test your code in this class
    private GameObject test;
    
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
        SpriteSheet doctorBombRunUp = new SpriteSheet("src/main/resources/DoctorBombUp.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunUp.getPath(), doctorBombRunUp);
        SpriteSheet doctorBombRunLeft = new SpriteSheet("src/main/resources/DoctorBombLeft.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunLeft.getPath(), doctorBombRunLeft);
        SpriteSheet doctorBombRunRight = new SpriteSheet("src/main/resources/DoctorBombRight.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunRight.getPath(), doctorBombRunRight);
        SpriteSheet doctorBombRunDown = new SpriteSheet("src/main/resources/DoctorBombDown.png", 0, 0, 9);
        AssetsPool.addSpriteSheet(doctorBombRunDown.getPath(), doctorBombRunDown);
        
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
}
