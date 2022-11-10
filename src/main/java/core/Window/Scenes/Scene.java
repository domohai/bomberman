package core.Window.Scenes;

import core.GameObject.GameObject;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected boolean isRunning;
    protected List<GameObject> gameObjects;
    protected Renderer renderer;
    protected boolean dataLoaded;
    
    public Scene() {
        this.gameObjects = new ArrayList<>();
        this.renderer = new Renderer();
        this.dataLoaded = false;
        isRunning = true;
    }
    
    public void init() {}
    
    public void start() {
        for (GameObject g : this.gameObjects) {
            g.start();
        }
    }
    
    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2D);
    public void load() {}
    public void save() {}
    
    public void addGameObject(GameObject newGameObject) {
        this.gameObjects.add(newGameObject);
        if (this.isRunning) newGameObject.start();
        this.renderer.submit(newGameObject);
    }
}
