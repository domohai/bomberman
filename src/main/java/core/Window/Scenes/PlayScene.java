package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.GameObject.components.Breakable;
import core.MouseController;
import util.AssetsPool;
import util.Const;
import util.Prefabs;
import util.Box2D;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayScene extends Scene {
    private char[][] map;
    private Map<ObjectType, List<GameObject>> typeListMap;
    private List<GameObject> toBeRemove;
    private boolean pause;
    private List<GameObject> pauseMenu;

    public PlayScene() {
        super();
        toBeRemove = new ArrayList<>();
        pauseMenu = new ArrayList<>();
        typeListMap = new HashMap<>();
        for (ObjectType type : ObjectType.values()) {
            typeListMap.put(type, new ArrayList<>());
        }
        pause = false;
    }
    
    @Override
    public void start() {
        for (ObjectType type : typeListMap.keySet()) {
            List<GameObject> list = typeListMap.get(type);
            for (GameObject g : list) {
                g.start();
            }
        }
        isRunning = true;
    }
    
    @Override
    public void init() {
        change_map("src/main/resources/Level1.txt");
        createButton();
    }

    @Override
    public void update(double dt) {
        if (!pause) updateGame(dt);
        else updateMenuPause(dt);
    }
    
    private void updateGame(double dt) {
        for (ObjectType type : typeListMap.keySet()) {
            List<GameObject> list = typeListMap.get(type);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).update(dt);
                if (!list.get(i).isAlive()) toBeRemove.add(list.get(i));
            }
        }
        if (toBeRemove.size() > 0) {
            for (GameObject g : toBeRemove) remove(g);
            toBeRemove.clear();
        }
    }
    
    private void updateMenuPause(double dt) {
        for (GameObject g : pauseMenu) g.update(dt);
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.drawImage(Const.background, 0, 0, Const.background.getWidth(), Const.background.getHeight(), null);
        renderer.render(g2D);
        if (pause) {
            g2D.drawImage(Const.blur_background, 0,0, Const.blur_background.getWidth(), Const.blur_background.getHeight(), null);
            renderer.renderButton(g2D);
        }
    }

    @Override
    public void addGameObject(GameObject object) {
        typeListMap.get(object.getType()).add(object);
        if (isRunning) object.start();
        renderer.submit(object);
    }

    public void remove(GameObject g) {
        typeListMap.get(g.getType()).remove(g);
        renderer.remove(g);
    }
    
    private void createButton() {
        // setting button
        GameObject setting = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/square_settings.png"),
        AssetsPool.getButton("src/main/resources/hover_buttons/square_settings.png"));
        setting.setType(ObjectType.OTHER);
        setting.setTransform(new Transform(new Box2D(Const.SCREEN_WIDTH - 75, 1, 60, 60), 0));
        addGameObject(setting);
    }

    private void change_map(String path) {
        map = AssetsPool.getMap(path);
        if (map == null) return;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '#') {
                    GameObject block = Prefabs.generateBlock("src/main/resources/Wall.png");
                    if (block == null) {
                        System.out.println("Error when generate block");
                        return;
                    }
                    block.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 64, 64), 0));
                    super.addGameObject(block);
                } else if (map[i][j] == '*') {
                    GameObject rock = Prefabs.generateBlock("src/main/resources/breakable_rock_large.png");
                    if (rock == null) {
                        System.out.println("Error when generate block");
                        return;
                    }
                    rock.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 64, 64), 0));
                    rock.setType(ObjectType.UNSTABLE);
                    rock.addComponent(new Breakable());
                    addGameObject(rock);
                } else if (map[i][j] == 'p') {
                    GameObject player = Prefabs.generatePlayer();
                    if (player == null) {
                        System.out.println("Can not generate player!");
                        return;
                    }
                    // set position
                    player.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 30, 42, 16, 15), 0));
                    addGameObject(player);
                } else if (map[i][j] == 'b') {
                    GameObject bot = Prefabs.generateBot();
                    if (bot == null) {
                        System.out.println("Can not generate bot!");
                        return;
                    }
                    // set position
                    bot.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 30, 42, 16, 15), 0));
                    addGameObject(bot);
                }
            }
        }
    }

    public char[][] getMap() {
        return map;
    }

    public Map<ObjectType, List<GameObject>> getTypeListMap() {
        return typeListMap;
    }
    
    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
