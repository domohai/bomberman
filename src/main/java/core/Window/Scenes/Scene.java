package core.Window.Scenes;

import core.GameObject.GameObject;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected boolean isRunning = false;
    protected List<GameObject> gameObjects;
    protected Renderer renderer;
    
    public Scene() {
        this.gameObjects = new ArrayList<>();
        this.renderer = new Renderer();
    }
    
    public void init() {}
    public void load_resources() {}
    
    public void start() {
        for (GameObject g : this.gameObjects) {
            g.start();
        }
        this.isRunning = true;
    }
    
    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2D);
    
    public void addGameObject(GameObject newGameObject) {
        this.gameObjects.add(newGameObject);
        if (this.isRunning) newGameObject.start();
        this.renderer.submit(newGameObject);
    }
}
