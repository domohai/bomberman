package core.Window.Scenes;

import core.GameObject.GameObject;
import util.Const;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Renderer {
    private Map<Integer, List<GameObject>> gameObjects;
    private int currentValue;
    
    public Renderer() {
        this.gameObjects = new HashMap<>();
        this.currentValue = 0;
    }
    
    public void submit(GameObject newGameObject) {
        //compute = put but with function execution inside
        this.gameObjects.computeIfAbsent(newGameObject.getTransform().getzIndex(), (x) -> new ArrayList<>());
        this.gameObjects.get(newGameObject.getTransform().getzIndex()).add(newGameObject);
    }
    
    // todo: add destroy function
    
    public void render(Graphics2D g2D) {
        // draw each gameObject base on its zIndex
        for (currentValue = Const.MIN_Z_INDEX ;currentValue <= Const.MAX_Z_INDEX;currentValue++) {
            if (gameObjects.get(currentValue) == null) {
                continue;
            }
            for (GameObject g : gameObjects.get(currentValue)) {
                g.draw(g2D);
            }
        }
    }
}
