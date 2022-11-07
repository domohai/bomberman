package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.Transform;
import core.GameObject.components.ButtonType;
import core.GameObject.components.Rect;
import core.MouseController;
import util.AssetsPool;
import util.Const;
import util.Prefabs;
import util.Box2D;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuScene extends Scene {
    private boolean tutorial = false;
    private List<GameObject> tutorialList;

    public MenuScene() {
        super();
        tutorialList = new ArrayList<>();
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
        quit.setTransform(new Transform(new Box2D((Const.SCREEN_WIDTH - Const.BUTTON_WIDTH)/2.0 , (Const.SCREEN_HEIGHT - 2* Const.BUTTON_HEIGHT)/2.0 + Const.BUTTON_OFFSET , Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT), 0));
        // add object
        addGameObject(play);
        addGameObject(quit);

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

    public void setTutorial(boolean tutorial) {
        this.tutorial = tutorial;
    }
}
