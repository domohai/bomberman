package core.Window.Scenes;

import core.GameObject.GameObject;
import util.Const;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Renderer {
    private final Map<Integer, List<GameObject>> gameObjects;
    private final List<GameObject> buttons;
    private int currentValue;
    
    public Renderer() {
        gameObjects = new HashMap<>();
        buttons = new ArrayList<>();
        currentValue = 0;
    }
    
    public void submit(GameObject newGameObject) {
        //compute = put but with function execution inside
        gameObjects.computeIfAbsent(newGameObject.getTransform().getzIndex(), (x) -> new ArrayList<>());
        gameObjects.get(newGameObject.getTransform().getzIndex()).add(newGameObject);
    }
    
    public void submitButton(GameObject gameObject) {
        buttons.add(gameObject);
    }
    
    public void remove(GameObject g) {
        gameObjects.get(g.getTransform().getzIndex()).remove(g);
    }
    
    public void render(Graphics2D g2D) {
        // draw each gameObject base on its zIndex
        for (currentValue = Const.MIN_Z_INDEX; currentValue <= Const.MAX_Z_INDEX; currentValue++) {
            if (gameObjects.get(currentValue) == null) continue;
            List<GameObject> list = gameObjects.get(currentValue);
            for (GameObject g : list) g.draw(g2D);
        }
    }

    public void clear() {
        for (currentValue = Const.MIN_Z_INDEX; currentValue <= Const.MAX_Z_INDEX; currentValue++) {
            if (gameObjects.get(currentValue) == null) continue;
            gameObjects.get(currentValue).clear();
        }
    }
    
    public void renderButton(Graphics2D g2D) {
        for (GameObject g : buttons) {
            g.draw(g2D);
        }
    }
}
