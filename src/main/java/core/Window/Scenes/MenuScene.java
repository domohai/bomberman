package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.Transform;
import core.GameObject.components.SpriteSheet;
import core.MouseController;
import util.AssetsPool;
import util.Prefabs;
import util.Box2D;

import java.awt.Graphics2D;

public class MenuScene extends Scene {

    public MenuScene() {
        super();
    }

    @Override
    public void init() {
        //init menu Start
        GameObject menuStart = Prefabs.generateButton(Prefabs.readImage("src/main/resources/menuImg/hover_buttons/start.png"),
                Prefabs.readImage("src/main/resources/menuImg/idle_buttons/start.png"));
        menuStart.setTransform(new Transform(new Box2D(300, 300, 0, 0), 0));
        addGameObject(menuStart);

        //init menu Exit

        GameObject menuExit = Prefabs.generateButton(Prefabs.readImage("src/main/resources/menuImg/hover_buttons/exit.png"),
                Prefabs.readImage("src/main/resources/menuImg/idle_buttons/start.png"));
        menuExit.setTransform(new Transform(new Box2D(300, 450, 0, 0), 0));
        addGameObject(menuExit);

    }

    @Override
    public void update(double dt) {
        for (GameObject g : gameObjects) {
            g.update(dt);
        }
    }

    @Override
    public void draw(Graphics2D g2D) {
        renderer.render(g2D);
    }

    @Override
    public void load_resources() {
        SpriteSheet startButtonBlack = new SpriteSheet("src/main/resources/menuImg/hover_buttons/start.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(startButtonBlack.getPath(), startButtonBlack);
        SpriteSheet exitButtonBlack = new SpriteSheet("src/main/resources/menuImg/hover_buttons/exit.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(exitButtonBlack.getPath(), startButtonBlack);
        SpriteSheet startButtonRed = new SpriteSheet("src/main/resources/menuImg/idle_buttons/start.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(startButtonRed.getPath(), startButtonRed);
        SpriteSheet exitButtonRed = new SpriteSheet("src/main/resources/menuImg/idle_buttons/exit.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(exitButtonRed.getPath(), exitButtonRed);
    }
}
