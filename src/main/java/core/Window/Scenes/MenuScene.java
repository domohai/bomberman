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
        // controls button
        GameObject controls = Prefabs.generateButton(AssetsPool.getButton("src/main/resources/idle_buttons/controls.png"),
        AssetsPool.getButton("src/main/resources/hover_buttons/controls.png"), ButtonType.CONTROLS);

        // setting position for button on menu scene
        play.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0, (Const.SCREEN_HEIGHT - 3* Const.BUTTON_HEIGHT)/2.0, Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 0));
        quit.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0 , (Const.SCREEN_HEIGHT - 3* Const.BUTTON_HEIGHT)/2.0 + Const.BUTTON_OFFSET * 2, Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 0));
        controls.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0, (Const.SCREEN_HEIGHT - 3* Const.BUTTON_HEIGHT)/2.0 + Const.BUTTON_OFFSET , Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 1));

        // add object
        addGameObject(play);
        addGameObject(quit);
        addGameObject(controls);
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
}
