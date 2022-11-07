package core.Window;

import core.GameObject.components.SpriteSheet;
import core.KeyController;
import core.MouseController;
import core.Window.Scenes.*;
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
    private static final Window window = new Window(); // the only window
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
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(MouseController.get()); // add mouseListener
        this.addMouseMotionListener(MouseController.get());
        this.addKeyListener(KeyController.get()); // add keyListener
        this.setVisible(true);
        this.isRunning = true;
        
    }
    
    public static Window get() {
        return Window.window;
    }
    
    /**
     * switch between menuScene and playScene...:)
     *
     * @param type type of new scene
     */
    public static void changeScene(SceneType type) {
        MouseController.get().reset();
        Stats.get().setHP(Const.INITIAL_HP);
        Stats.setPause(false);
        switch (type) {
            case MENU_SCENE -> window.currentScene = new MenuScene();
            case PLAY_SCENE -> window.currentScene = new PlayScene();
            default -> System.out.println("Invalid scene!");
        }
        window.currentScene.init();
        window.currentScene.start();
    }
    
    public static Scene getCurrentScene() {
        return window.currentScene;
    }
    
    /**
     * initialize stuff:)
     */
    public void init() {
        load_resources();
        bufferImage = createImage(getWidth(), getHeight());
        bufferGraphics = bufferImage.getGraphics();
        bufferGraphics.setColor(Color.BLACK);
//        Sound.play(Const.BACKGROUND_MUSIC);
        Window.changeScene(SceneType.MENU_SCENE);
    }
    
    public void update(double delta_time) {
//        System.out.println(1/delta_time + " fps");
        window.setTitle("Bomberman | " + (int) (1.0 / delta_time) + " fps");
        window.currentScene.update(delta_time);
        this.draw(getGraphics());
    }
    
    public void draw(Graphics g) {
        if (g == null) return;
        renderOffScreen(bufferGraphics);
        g.drawImage(bufferImage, 7, 30, getWidth(), getHeight(), null);
    }
    
    /**
     * this function draws all the objects to an image called bufferImage
     * and then the bufferImage will be drawn to the window (JFrame)
     * so the movement of objects will be more smooth.:)
     *
     * @param g bufferGraphics
     */
    public void renderOffScreen(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.fillRect(0, 0, getWidth(), getHeight());
        window.currentScene.draw(g2D);
    }
    
    /**
     * Game loop
     */
    @Override
    public void run() {
        // variables for calculating delta time
//        double lastFrameTime = 0.0;
        double time;
        double delta_time = 0;
        try {
            while (isRunning) {
                time = Time.getTime();
                update(delta_time);
                Thread.sleep(Math.max(0, (int)(16.66666666667 - (Time.getTime() - time)*1000)));
                delta_time = Time.getTime() - time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void load_resources() {
        // game sprites
        String[] nameList = {"DoctorBomb", "BoarGuard", "RedLizard", "Fantasma"};
        for (String name : nameList) {
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
        SpriteSheet bomb = new SpriteSheet("src/main/resources/Bomb.png", 0, 0, 40, 52, 6);
        AssetsPool.addSpriteSheet(bomb.getPath(), bomb);
        SpriteSheet explosion = new SpriteSheet("src/main/resources/Flame.png", 0, 0, 48, 48, 18);
        AssetsPool.addSpriteSheet(explosion.getPath(), explosion);
        SpriteSheet rock = new SpriteSheet("src/main/resources/breakable_rock.png", 0, 0, 52, 52, 1);
        AssetsPool.addSpriteSheet(rock.getPath(), rock);
        // PowerUps
        SpriteSheet bombPU = new SpriteSheet("src/main/resources/PUBomb.png", 0, 0, 50, 64, 6);
        AssetsPool.addSpriteSheet(bombPU.getPath(), bombPU);
        SpriteSheet flamePU = new SpriteSheet("src/main/resources/PUFlame.png", 0, 0, 50, 64, 6);
        AssetsPool.addSpriteSheet(flamePU.getPath(), flamePU);
        SpriteSheet speedPU = new SpriteSheet("src/main/resources/PUSpeed.png", 0, 0, 50, 64, 6);
        AssetsPool.addSpriteSheet(speedPU.getPath(), speedPU);
        // door
        SpriteSheet door = new SpriteSheet("src/main/resources/Portal.png", 0, 0, Const.TILE_W, Const.TILE_H, 2);
        AssetsPool.addSpriteSheet(door.getPath(), door);
        // menu sprites
        AssetsPool.addButton("src/main/resources/idle_buttons/play.png");
        AssetsPool.addButton("src/main/resources/hover_buttons/play.png");
        AssetsPool.addButton("src/main/resources/idle_buttons/square_settings.png");
        AssetsPool.addButton("src/main/resources/hover_buttons/square_settings.png");
        AssetsPool.addButton("src/main/resources/hover_buttons/quit.png");
        AssetsPool.addButton("src/main/resources/idle_buttons/quit.png");
        AssetsPool.addButton("src/main/resources/hover_buttons/resume.png");
        AssetsPool.addButton("src/main/resources/idle_buttons/resume.png");
        AssetsPool.addButton("src/main/resources/idle_buttons/menu.png");
        AssetsPool.addButton("src/main/resources/hover_buttons/menu.png");
        AssetsPool.addButton("src/main/resources/pause_menu_bg.png");
        AssetsPool.addButton("src/main/resources/idle_buttons/controls.png");
        AssetsPool.addButton("src/main/resources/hover_buttons/controls.png");
        //menu img
        SpriteSheet img = new SpriteSheet("src/main/resources/pixelBomberman.png",0,0,400,182,1);
        AssetsPool.addSpriteSheet("src/main/resources/pixelBomberman.png",img);
        SpriteSheet menuBG = new SpriteSheet("src/main/resources/map.png",0,0,1298,805,1);
        AssetsPool.addSpriteSheet("src/main/resources/map.png",menuBG);
        // audios
        AssetsPool.addAudio(Const.EXPLOSION_SOUND, false, Const.DEFAULT_VOLUME);
        AssetsPool.addAudio(Const.BACKGROUND_MUSIC, true, Const.DEFAULT_VOLUME);
        AssetsPool.addAudio(Const.ITEM_SOUND, false, Const.DEFAULT_VOLUME + 0.1f);
        AssetsPool.addAudio(Const.DOOR_SOUND, false, Const.DEFAULT_VOLUME + 0.1f);
        AssetsPool.addAudio(Const.DIE_SOUND, false, Const.DEFAULT_VOLUME + 0.1f);

    }
    
    public void exit() {
        isRunning = false;
        window.setVisible(false);
        window.dispose();
    }
}
