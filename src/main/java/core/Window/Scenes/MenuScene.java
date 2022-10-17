package core.Window.Scenes;

import core.GameObject.GameObject;
import core.GameObject.Transform;
import core.GameObject.components.SpriteSheet;
import core.MouseController;
import util.AssetsPool;
import util.Prefabs;
import util.Vector2D;

import java.awt.Graphics2D;

public class MenuScene extends Scene {
    
    public MenuScene() {
        super();
    }

    @Override
    public void init() {
        GameObject play = Prefabs.generateButton(Prefabs.readImage("src/main/resources/LargeButton/Large_Buttons/Start_Button.png"),
                Prefabs.readImage("src/main/resources/LargeButton/Colored_Large_Buttons/Start_col_Button.png"));
        play.setTransform(new Transform(new Vector2D(300, 300), 0));
        addGameObject(play);
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
        SpriteSheet startButtonBlack = new SpriteSheet("src/main/resources/LargeButton/Large_Buttons/Start_Button.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(startButtonBlack.getPath(), startButtonBlack);
        SpriteSheet exitButtonBlack = new SpriteSheet("src/main/resources/LargeButton/Large_Buttons/Exit_Button.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(exitButtonBlack.getPath(), startButtonBlack);
        SpriteSheet startButtonRed = new SpriteSheet("src/main/resources/LargeButton/Colored_Large_Buttons/Start_col_Button.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(startButtonRed.getPath(), startButtonRed);
        SpriteSheet exitButtonRed = new SpriteSheet("src/main/resources/LargeButton/Colored_Large_Buttons/Exit_col_Button.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(exitButtonRed.getPath(), exitButtonRed);
    }
}
