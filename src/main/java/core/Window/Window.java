package core.Window;

import core.GameObject.components.SpriteSheet;
import core.KeyController;
import core.MouseController;
import core.Window.Scenes.MenuScene;
import core.Window.Scenes.PlayScene;
import core.Window.Scenes.Scene;
import core.Window.Scenes.SceneType;
import util.AssetsPool;
import util.Const;
import util.Time;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

/**
 * We are implementing a system called Entity component system (ECS)
 * to build this game and if you're wondering what it is
 * I have a few links for more information about ECS
 * link to a video: <a href="https://www.youtube.com/watch?v=HkG8ZdhoXhs">...</a>
 * link to a book: <a href="https://gameprogrammingpatterns.com/component.html">...</a>
 * have it your way:)
 */
public class Window extends JFrame implements Runnable {
    private static Window window = new Window(); // the only window
    private boolean isRunning;
    private Scene currentScene = null;
    private Image bufferImage = null;
    private Graphics bufferGraphics = null;
    
    /**
     * we don't want any other class to call this constructor,
     * so we only have one window open
     * that's the reason why it's private:)
     */
    private Window() {
        this.setSize(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        this.setTitle(Const.SCREEN_TITLE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(MouseController.get()); // add mouseListener
        this.addMouseMotionListener(MouseController.get());
        this.addKeyListener(KeyController.get()); // add keyListener
        this.setVisible(true);
        this.isRunning = true;
    }
    
    /**
     * initialize stuff:)
     */
    public void init() {
        load_resources();
        bufferImage = createImage(getWidth(), getHeight());
        bufferGraphics = bufferImage.getGraphics();
        bufferGraphics.setColor(Color.BLACK);
        Window.changeScene(SceneType.PLAY_SCENE);
//        Window.changeScene(SceneType.MENU_SCENE);
    }

    public static Window get() {
        return Window.window;
    }

    public void update(double delta_time) {
//        System.out.println(1/delta_time + " fps");
        window.currentScene.update(delta_time);
        this.draw(getGraphics());
    }
    
    public void draw(Graphics g) {
        renderOffScreen(bufferGraphics);
        g.drawImage(bufferImage, 7, 30, getWidth(), getHeight(), null);
    }
    
    /**
     * this function draws all the objects to an image called bufferImage
     * and then the bufferImage will be drawn to the window (JFrame)
     * so the movement of objects will be more smooth.:)
     * @param g bufferGraphics
     */
    public void renderOffScreen(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
//        g2D.fillRect(0, 0, getWidth(), getHeight());
        window.currentScene.draw(g2D);
    }
    
    /**
     * switch between menuScene and playScene...:)
     * @param type type of new scene
     */
    public static void changeScene(SceneType type) {
        switch (type) {
            case MENU_SCENE -> window.currentScene = new MenuScene();
            case PLAY_SCENE -> window.currentScene = new PlayScene();
            default -> System.out.println("Invalid scene!");
        }
        window.currentScene.init();
        window.currentScene.start();
    }
    
    /**
     * Game loop
     */
    @Override
    public void run() {
        // variables for calculating delta time
        double lastFrameTime = 0.0;
        double time;
        double delta_time;
        try {
            while (isRunning) {
                time = Time.getTime();
                delta_time = time - lastFrameTime;
                lastFrameTime = time;
                update(delta_time);
                Thread.sleep(25);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Scene getCurrentScene() {
        return window.currentScene;
    }
    
    public void load_resources() {
        // game sprites
        String[] nameList = {"DoctorBomb","BoarGuard","RedLizard"};
        for(String name : nameList) {
            SpriteSheet RunUp = new SpriteSheet("src/main/resources/" + name + "Up.png", 0, 0, 9);
            AssetsPool.addSpriteSheet(RunUp.getPath(), RunUp);
            SpriteSheet RunLeft = new SpriteSheet("src/main/resources/" + name + "Left.png", 0, 0, 9);
            AssetsPool.addSpriteSheet(RunLeft.getPath(), RunLeft);
            SpriteSheet RunRight = new SpriteSheet("src/main/resources/" + name + "Right.png", 0, 0, 9);
            AssetsPool.addSpriteSheet(RunRight.getPath(), RunRight);
            SpriteSheet RunDown = new SpriteSheet("src/main/resources/" + name + "Down.png", 0, 0, 9);
            AssetsPool.addSpriteSheet(RunDown.getPath(), RunDown);
        }
        SpriteSheet wall = new SpriteSheet("src/main/resources/Wall.png", 0, 0, 1);
        AssetsPool.addSpriteSheet(wall.getPath(), wall);
        SpriteSheet bomb = new SpriteSheet("src/main/resources/bomb_scaled.png", 0, 0, 40, 52, 6);
        AssetsPool.addSpriteSheet(bomb.getPath(), bomb);
        SpriteSheet explosion = new SpriteSheet("src/main/resources/Flame.png", 0, 0, 48, 48, 18);
        AssetsPool.addSpriteSheet(explosion.getPath(), explosion);
        SpriteSheet rock = new SpriteSheet("src/main/resources/breakable_rock_large.png", 0, 0, 52, 52, 1);
        AssetsPool.addSpriteSheet(rock.getPath(), rock);
        // menu sprites
//        AssetsPool.addButton("src/main/resources/idle_buttons/play.png");
//        AssetsPool.addButton("src/main/resources/hover_buttons/play.png");
//        AssetsPool.addButton("src/main/resources/idle_buttons/square_settings.png");
//        AssetsPool.addButton("src/main/resources/hover_buttons/square_settings.png");
        // maps
        AssetsPool.addMap("src/main/resources/Level0.txt");
        AssetsPool.addMap("src/main/resources/Level1.txt");
    }
}
