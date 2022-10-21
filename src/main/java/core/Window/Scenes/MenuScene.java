package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.Transform;
import core.GameObject.components.SpriteSheet;
import util.AssetsPool;
import util.Box2D;
import util.Prefabs;

import java.awt.Graphics2D;

public class MenuScene extends Scene {
    
    public MenuScene() {
        super();
    }

    @Override
    public void init() {
    
    }
    
    @Override
    public void update(double dt) {
    
    }
    
    @Override
    public void draw(Graphics2D g2D) {
        renderer.render(g2D);
    }

    @Override
    public void load_resources() {
    
    }
}
