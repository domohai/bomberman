package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.GameObject.components.Breakable;
import core.GameObject.components.ButtonType;
import core.KeyController;
import core.MouseController;
import core.Window.Window;
import util.AssetsPool;
import util.Const;
import util.Prefabs;
import util.Box2D;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayScene extends Scene {
    private char[][] map;
    private boolean[][] placedBombs = new boolean[12][20];
    private Map<ObjectType, List<GameObject>> typeListMap;
    private List<GameObject> toBeRemove;
    private boolean pause, lose, win;
    private List<GameObject> pauseMenu;
    private int currentLevel = 1;
    private String level1;


    public PlayScene() {
        super();
        toBeRemove = new ArrayList<>();
        pauseMenu = new ArrayList<>();
        typeListMap = new HashMap<>();
        for (ObjectType type : ObjectType.values()) {
            typeListMap.put(type, new ArrayList<>());
        }
        pause = false;
        lose = false;
        win = false;
    }

    @Override
    public void start() {
        for (ObjectType type : typeListMap.keySet()) {
            List<GameObject> list = typeListMap.get(type);
            for (GameObject g : list) g.start();
        }
        isRunning = true;
    }

    @Override
    public void init() {
        change_map("src/main/resources/Levels/Level3.txt");
        createButton();
    }

    @Override
    public void update(double dt) {
        if (lose || win) {
            if (MouseController.isMousePressed(MouseEvent.BUTTON1)) {
                Window.changeScene(SceneType.MENU_SCENE);
            }
        } else {
            if (!pause) updateGame(dt);
            else updateMenuPause(dt);
        }
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
        // todo: change map when all bots die
        if (KeyController.is_keyPressed(KeyEvent.VK_G)) {
            change_map(level1);
        }
    }

    private void updateMenuPause(double dt) {
        for (GameObject g : pauseMenu) g.update(dt);
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.drawImage(Const.background, 0, 0, Const.background.getWidth(), Const.background.getHeight(), null);
        renderer.render(g2D);
        drawHeart(g2D);
        if (pause) {
            g2D.drawImage(Const.blur_background, 0, 0, Const.blur_background.getWidth(), Const.blur_background.getHeight(), null);
            renderer.renderButton(g2D);
        }
    }
    
    private void drawHeart(Graphics2D g2D) {
        for (int i = 1; i <= Stats.get().getHP(); i++) {
            g2D.drawImage(Const.heart, 20 * i + 10 * i, 20, Const.heart.getWidth(), Const.heart.getHeight(), null);
        }
    }

    @Override
    public void addGameObject(GameObject object) {
        typeListMap.get(object.getType()).add(object);
        if (isRunning) object.start();
        renderer.submit(object);
    }

    private void addButton(GameObject button) {
        button.start();
        pauseMenu.add(button);
        renderer.submitButton(button);
    }

    public void remove(GameObject g) {
        typeListMap.get(g.getType()).remove(g);
        renderer.remove(g);
    }

    private void createButton() {
        // Add to Game Object Menu
        GameObject resume = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/resume.png"),
        AssetsPool.getButton("src/main/resources/hover_buttons/resume.png"), ButtonType.RESUME);
        resume.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0 + 20, (Const.SCREEN_HEIGHT - 3* Const.BUTTON_HEIGHT)/2.0, Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 1));
        addButton(resume);
        // quit
        GameObject quit = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/quit.png"),
        AssetsPool.getButton("src/main/resources/hover_buttons/quit.png"), ButtonType.QUIT);
        quit.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0 + 20, (Const.SCREEN_HEIGHT - 3* Const.BUTTON_HEIGHT)/2.0 + Const.BUTTON_OFFSET * 2, Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 1));
        addButton(quit);
        // menu button
        GameObject menu = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/menu.png"),
        AssetsPool.getButton("src/main/resources/hover_buttons/menu.png"), ButtonType.MENU);
        menu.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0 + 20, (Const.SCREEN_HEIGHT - 3* Const.BUTTON_HEIGHT)/2.0 + Const.BUTTON_OFFSET, Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 1));
        addButton(menu);

    }

    private void change_map(String path) {
        // clear old map
        for (ObjectType type : ObjectType.values()) typeListMap.get(type).clear();
        gameObjects.clear();
        toBeRemove.clear();
        renderer.clear();
        GameObject setting = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/square_settings.png"),
                AssetsPool.getButton("src/main/resources/hover_buttons/square_settings.png"), ButtonType.SETTING);
        setting.setType(ObjectType.OTHER);
        setting.setTransform(new Transform(new Box2D(Const.SCREEN_WIDTH - 75, 1, Const.SQUARE_BUTTON, Const.SQUARE_BUTTON), 5));
        addGameObject(setting);
        // load new map
        map = AssetsPool.getMap(path);
        if (map == null) return;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case '#' -> {
                        GameObject block = Prefabs.generateBlock("src/main/resources/Wall.png");
                        if (block == null) {
                            System.out.println("Cannot generate block");
                            return;
                        }
                        block.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, Const.TILE_W, Const.TILE_H), Const.STILL_OBJECT_ZINDEX));
                        super.addGameObject(block);
                    }
                    case '*', 'B', 'F', 'S', 'X' -> {
                        GameObject rock = Prefabs.generateBlock("src/main/resources/breakable_rock_large.png");
                        if (rock == null) {
                            System.out.println("Can not generate block");
                            return;
                        }
                        rock.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, Const.TILE_W, Const.TILE_H), Const.BREAKABLE_ROCK_ZINDEX));
                        rock.setType(ObjectType.UNSTABLE);
                        rock.addComponent(new Breakable());
                        addGameObject(rock);
                    }
                    case 'p' -> {
                        GameObject player = Prefabs.generatePlayer();
                        if (player == null) {
                            System.out.println("Can not generate player!");
                            return;
                        }
                        // set position
                        player.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 30, 42, 16, 15), Const.PLAYER_ZINDEX));
                        addGameObject(player);
                    }
                    case '1' -> {
                        GameObject bot = Prefabs.generateBot("BoarGuard");
                        if (bot == null) {
                            System.out.println("Can not generate BoarGuard!");
                            return;
                        }
                        // set position
                        bot.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 30, 42, 16, 15), Const.BOT_ZINDEX));
                        addGameObject(bot);
                    }
                    case '2' -> {
                        GameObject bot = Prefabs.generateBot("RedLizard");
                        if (bot == null) {
                            System.out.println("Can not generate RedLizard!");
                            return;
                        }
                        // set position
                        bot.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 30, 42, 16, 15), Const.BOT_ZINDEX));
                        addGameObject(bot);
                    }
                    case 'b', 'f', 's' -> {
                        String type = "PUBomb";
                        if (map[i][j] == 'b') type = "PUBomb";
                        if (map[i][j] == 'f') type = "PUFlame";
                        if (map[i][j] == 's') type = "PUSpeed";
                        GameObject item = Prefabs.generateItem(type);
                        if (item == null) {
                            System.out.println("Can not generate " + type);
                            return;
                        }
                        // set position
                        item.setTransform(new Transform(new Box2D(Const.TILE_W * j + 7, Const.TILE_H * i, 38, 56, 5, 4), Const.ITEM_ZINDEX));
                        addGameObject(item);
                    }
                }
            }
        }
    }

    public char[][] getMap() {
        return map;
    }

    public boolean[][] getPlacedBombs() {
        return placedBombs;
    }

    public Map<ObjectType, List<GameObject>> getTypeListMap() {
        return typeListMap;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
    
    public void setLose(boolean lose) {
        this.lose = lose;
    }
    
    public void setWin(boolean win) {
        this.win = win;
    }
}
