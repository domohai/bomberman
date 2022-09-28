package core.Window.Scenes;

import core.GameObject.GameObject;
import java.awt.Graphics2D;

public class PlayScene extends Scene {
    // test your code in this class
    private GameObject test;
    
    public PlayScene() {
        super();
        this.test = new GameObject();
    }
    
    @Override
    public void init() {
        this.addGameObject(test);
    }
    
    @Override
    public void update(double dt) {
    
    }
    
    @Override
    public void draw(Graphics2D g2D) {
    
    }
}
