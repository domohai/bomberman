package core.Window.Scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.GameObject.GameObject;
import core.GameObject.ObjectDeserializer;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.GameObject.components.Breakable;
import core.GameObject.components.ButtonType;
import core.GameObject.components.Component;
import core.GameObject.components.ComponentDeserializer;
import core.GameObject.components.Frame;
import core.GameObject.components.SpriteSheet;
import core.GameObject.components.State;
import core.GameObject.components.StateMachine;
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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    }

    @Override
    public void init() {
        if (!dataLoaded) change_map();
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
        if (Stats.isNextLevel()) {
            Stats.setNextLevel(false);
            change_map();
        }
        if (KeyController.is_keyPressed(KeyEvent.VK_K)) {
            typeListMap.get(ObjectType.BOT).clear();
        }
        if (KeyController.is_keyPressed(KeyEvent.VK_Y)) {
            save();
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
    }

    public void change_map() {
        if (Stats.currentLevel() > Const.LAST_LEVEL) {
            Stats.setWin(true);
            return;
        }
        // clear old map
        for (ObjectType type : ObjectType.values()) typeListMap.get(type).clear();
        gameObjects.clear();
        toBeRemove.clear();
        renderer.clear();
        // create setting button again
        GameObject setting = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/square_settings.png"),
                AssetsPool.getButton("src/main/resources/hover_buttons/square_settings.png"), ButtonType.SETTING);
        setting.setType(ObjectType.OTHER);
        setting.setTransform(new Transform(new Box2D(Const.SCREEN_WIDTH - 75, 1, Const.SQUARE_BUTTON, Const.SQUARE_BUTTON), 5));
        addGameObject(setting);
        Stats.get().reset();
        // load new map
        String path = "src/main/resources/Levels/Level" + Stats.currentLevel() + ".txt";
        map = Prefabs.loadMap(path);
        if (map == null) return;
        // must load player first
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'p') {
                    GameObject player = Prefabs.generatePlayer();
                    if (player == null) {
                        System.out.println("Can not generate player!");
                        return;
                    }
                    // set position
                    player.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, 30, 42, 16, 15), Const.PLAYER_ZINDEX));
                    addGameObject(player);
                }
            }
        }
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
    
    @Override
    public void load() {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new ObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();
        String inFile;
        String level;
        try {
            inFile = new String(Files.readAllBytes(Paths.get("Data/data.txt")));
            level = new String(Files.readAllBytes(Paths.get("Data/level.txt")));
        } catch (IOException e) {
            System.out.println("Failed to load game!");
            e.printStackTrace();
            return;
        }
        if (inFile.equals("") || level.equals("")) return;
        Stats.setLevel(Integer.parseInt(level));
        GameObject[] Objects = gson.fromJson(inFile, GameObject[].class);
        
        String pathMap = "src/main/resources/Levels/Level" + Stats.currentLevel() + ".txt";
        map = Prefabs.loadMap(pathMap);
        if (map == null) return;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '#') {
                    GameObject block = Prefabs.generateBlock("src/main/resources/Wall.png");
                    if (block == null) {
                        System.out.println("Cannot generate block");
                        return;
                    }
                    block.setTransform(new Transform(new Box2D(Const.TILE_W * j, Const.TILE_H * i, Const.TILE_W, Const.TILE_H), Const.STILL_OBJECT_ZINDEX));
                    super.addGameObject(block);
                } else if (map[i][j] == ' ') {
                
                } else {
                    if (map[i][j] == 'p' || map[i][j] == '*'
                    || (map[i][j] >= '0' && map[i][j] <= '9')) {
                        map[i][j] = ' ';
                    }
                }
            }
        }
        
        for (int i = 0; i < Objects.length; i++) {
            if (Objects[i].getType() == ObjectType.PLAYER) {
                StateMachine stateMachine = Objects[i].getComponent(StateMachine.class);
                if (stateMachine != null) {
                    Map<String, State> states = stateMachine.getStates();
                    for (String s : states.keySet()) {
                        String path = states.get(s).getPath();
                        SpriteSheet sheet = AssetsPool.getSpriteSheet(path);
                        if (states.get(s).getFrames().size() == 1) {
                            states.get(s).getFrames().clear();
                            states.get(s).addFrame(new Frame(sheet.getSprite(0), -1));
                        } else {
                            states.get(s).getFrames().clear();
                            for (int j = 1; j < sheet.size(); j++) {
                                states.get(s).addFrame(new Frame(sheet.getSprite(j), Const.DEFAULT_FRAME_TIME));
                            }
                        }
                    }
                }
                addGameObject(Objects[i]);
                break;
            }
        }
        for (int i = 0; i < Objects.length; i++) {
            if (Objects[i].getType() == ObjectType.PLAYER) continue;
            StateMachine stateMachine = Objects[i].getComponent(StateMachine.class);
            if (stateMachine != null) {
                Map<String, State> states = stateMachine.getStates();
                if (Objects[i].getType() == ObjectType.BOT) {
                    for (String s : states.keySet()) {
                        String path = states.get(s).getPath();
                        SpriteSheet sheet = AssetsPool.getSpriteSheet(path);
                        if (states.get(s).getFrames().size() == 1) {
                            states.get(s).getFrames().clear();
                            states.get(s).addFrame(new Frame(sheet.getSprite(0), -1));
                        } else {
                            states.get(s).getFrames().clear();
                            for (int j = 1; j < sheet.size(); j++) {
                                states.get(s).addFrame(new Frame(sheet.getSprite(j), Const.DEFAULT_FRAME_TIME));
                            }
                        }
                    }
                } else if (Objects[i].getType() == ObjectType.PORTAL) {
                    for (String s : states.keySet()) {
                        String path = states.get(s).getPath();
                        SpriteSheet sheet = AssetsPool.getSpriteSheet(path);
                        states.get(s).getFrames().clear();
                        if (s.equals("Close")) states.get(s).addFrame(new Frame(sheet.getSprite(0), -1));
                        else states.get(s).addFrame(new Frame(sheet.getSprite(1), -1));
                    }
                } else {
                    for (String s : states.keySet()) {
                        String path = states.get(s).getPath();
                        SpriteSheet sheet = AssetsPool.getSpriteSheet(path);
                        if (states.get(s).getFrames().size() == 1) {
                            states.get(s).getFrames().clear();
                            states.get(s).addFrame(new Frame(sheet.getSprite(0), -1));
                        } else {
                            states.get(s).getFrames().clear();
                            for (int j = 0; j < sheet.size(); j++) {
                                states.get(s).addFrame(new Frame(sheet.getSprite(j), Const.BOMB_TIME));
                            }
                        }
                    }
                    if (map[Objects[i].getTransform().getPosition().getCoordY()]
                            [Objects[i].getTransform().getPosition().getCoordX()] != 'B'
                    && map[Objects[i].getTransform().getPosition().getCoordY()]
                            [Objects[i].getTransform().getPosition().getCoordX()] != 'S'
                    && map[Objects[i].getTransform().getPosition().getCoordY()]
                            [Objects[i].getTransform().getPosition().getCoordX()] != 'F') {
                        map[Objects[i].getTransform().getPosition().getCoordY()]
                                [Objects[i].getTransform().getPosition().getCoordX()] = '*';
                    }
                    
                }
            }
            addGameObject(Objects[i]);
        }
        GameObject setting = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/square_settings.png"),
                AssetsPool.getButton("src/main/resources/hover_buttons/square_settings.png"), ButtonType.SETTING);
        setting.setType(ObjectType.OTHER);
        setting.setTransform(new Transform(new Box2D(Const.SCREEN_WIDTH - 75, 1, Const.SQUARE_BUTTON, Const.SQUARE_BUTTON), 5));
        addGameObject(setting);
        this.dataLoaded = true;
    }
    
    @Override
    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new ObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();
        // add all objects we want to save in a list
        List<GameObject> list = new ArrayList<>();
        for (ObjectType t : typeListMap.keySet()) {
            if (t == ObjectType.FLAME || t == ObjectType.OTHER
            || t == ObjectType.ITEM) continue;
            list.addAll(typeListMap.get(t));
        }
        try (FileWriter writer = new FileWriter("Data/data.txt");
        FileWriter writer1 = new FileWriter("Data/level.txt")) {
            writer1.write(String.valueOf(Stats.currentLevel()));
            writer.write(gson.toJson(list));
        } catch (IOException e) {
            System.out.println("Failed to save game!");
            e.printStackTrace();
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
