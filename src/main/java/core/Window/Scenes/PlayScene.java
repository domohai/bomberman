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
    private final boolean[][] placedBombs = new boolean[12][20];
    private final Map<ObjectType, List<GameObject>> typeListMap;
    private final List<GameObject> toBeRemove;
    private final List<GameObject> pauseMenu;

    public PlayScene() {
        super();
        toBeRemove = new ArrayList<>();
        pauseMenu = new ArrayList<>();
        typeListMap = new HashMap<>();
        for (ObjectType type : ObjectType.values()) {
            typeListMap.put(type, new ArrayList<>());
        }
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
        change_map();
        createButton();
    }

    @Override
    public void update(double dt) {
        if (Stats.isLose() || Stats.isWin()) {
            if (MouseController.isMousePressed(MouseEvent.BUTTON1)) {
                Window.changeScene(SceneType.MENU_SCENE);
            }
        } else {
            if (!Stats.isPause()) updateGame(dt);
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
        if (typeListMap.get(ObjectType.PLAYER).size() < 1) {
            Stats.get().reset();
            change_map();
        }
    }

    private void updateMenuPause(double dt) {
        for (GameObject g : pauseMenu) g.update(dt);
    }

    @Override
    public void draw(Graphics2D g2D) {
        if (Stats.isWin() || Stats.isLose()) {
            if (Stats.isLose()) g2D.drawImage(Const.LOSE, 0,0, Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT, null);
            else g2D.drawImage(Const.WIN, 0, 0, Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT, null);
        } else {
            g2D.drawImage(Const.background, 0, 0, Const.background.getWidth(), Const.background.getHeight(), null);
            renderer.render(g2D);
            drawHeart(g2D);
            if (Stats.isPause()) {
                g2D.drawImage(Const.blur_background, 0, 0, Const.blur_background.getWidth(), Const.blur_background.getHeight(), null);
                renderer.renderButton(g2D);
            }
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
        // sound
        
    }

    public void change_map() {
        if(Stats.currentLevel() > Const.LAST_LEVEL) {
            Stats.setWin(true);
            return;
        }
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
        GameObject audio = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/audio.png"),
                AssetsPool.getButton("src/main/resources/hover_buttons/audio.png"), ButtonType.AUDIO);
        audio.setType(ObjectType.OTHER);
        audio.setTransform(new Transform(new Box2D(Const.SCREEN_WIDTH - 135, 1, Const.SQUARE_BUTTON, Const.SQUARE_BUTTON), 5));
        addGameObject(audio);
        // load new map
        String path = "src/main/resources/Levels/Level" + Stats.currentLevel() + ".txt";
        map = Prefabs.loadMap(path);
        if (map == null) return;
        // generate a temp player
        GameObject temp = Prefabs.generatePlayer();
        temp.setTransform(new Transform(new Box2D(0, 0, 0, 0), 0));
        typeListMap.get(ObjectType.PLAYER).add(temp);
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
                        GameObject rock = Prefabs.generateBlock("src/main/resources/breakable_rock.png");
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
                        typeListMap.get(ObjectType.PLAYER).clear();
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
                    case '3' -> {
                        GameObject bot = Prefabs.generateBot("Fantasma");
                        if (bot == null) {
                            System.out.println("Can not generate Fantasma!");
                            return;
                        }
                        // set position
                        bot.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 42, 60, 11, 0), Const.BOT_ZINDEX));
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


}
