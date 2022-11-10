package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.Transform;
import core.GameObject.components.ButtonType;
import util.AssetsPool;
import util.Const;
import util.Prefabs;
import util.Box2D;
import java.awt.Graphics2D;

public class MenuScene extends Scene {

    public MenuScene() {
        super();
    }

    @Override
    public void init() {
        // setting button
        // add button image in load_resources in Window class first
        GameObject play = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/play.png"),
        AssetsPool.getButton("src/main/resources/hover_buttons/play.png"), ButtonType.PLAY);
        GameObject quit = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/quit.png"),
        AssetsPool.getButton("src/main/resources/hover_buttons/quit.png"), ButtonType.QUIT);
        // setting position for button on menu scene
        play.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0, (Const.SCREEN_HEIGHT - 2* Const.BUTTON_HEIGHT)/2.0, Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 0));
        quit.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0 , (Const.SCREEN_HEIGHT - 2* Const.BUTTON_HEIGHT)/2.0 + 2*Const.BUTTON_OFFSET , Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 0));
        // add img
        GameObject img = Prefabs.generateImage("pixelBomberman");
        AssetsPool.getSpriteSheet("src/main/resources/pixelBomberman.png");
        img.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - 400) / 2.0, (Const.SCREEN_HEIGHT - 3 * Const.BUTTON_HEIGHT )/2.0- 182 - 50, 400, 182),5));
        // background
        GameObject menuBG = Prefabs.generateImage("map");
        AssetsPool.getSpriteSheet("src/main/resources/map.png");
        menuBG.setTransform(new Transform(new Box2D(0, 0, 1298,805),-1));
        
        GameObject load = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/load.png"),
        AssetsPool.getButton("src/main/resources/hover_buttons/load.png"), ButtonType.LOAD);
        load.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0,
        (Const.SCREEN_HEIGHT - 2*Const.BUTTON_HEIGHT)/2.0 + Const.BUTTON_OFFSET, Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 0));
        
        // add object
        addGameObject(play);
        addGameObject(quit);
        addGameObject(img);
        addGameObject(menuBG);
        addGameObject(load);
    }

    @Override
    public void update(double dt) {
        for (GameObject g : gameObjects) {
            g.update(dt);
        }
//        if (KeyController.is_keyPressed(KeyEvent.VK_S)) {
//            Stats.setLoad(true);
//            Window.changeScene(SceneType.PLAY_SCENE);
//        }
    }

    @Override
    public void draw(Graphics2D g2D) {
         renderer.render(g2D);
    }
}
