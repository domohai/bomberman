package core.Window;

import core.KeyController;
import core.MouseController;
import core.Window.Scenes.MenuScene;
import core.Window.Scenes.PlayScene;
import core.Window.Scenes.Scene;
import core.Window.Scenes.SceneType;
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
    private static Window window = null; // the only window
    private boolean isRunning;
    private static Scene currentScene = null;
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
        this.addKeyListener(KeyController.get()); // add keyListener
        this.setVisible(true);
        this.isRunning = true;
    }
    
    /**
     * initialize stuff:)
     */
    public void init() {
        bufferImage = createImage(getWidth(), getHeight());
        bufferGraphics = bufferImage.getGraphics();
        bufferGraphics.setColor(Color.BLACK);
        Window.changeScene(SceneType.PLAY_SCENE);
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void update(double delta_time) {
//        System.out.println(1/delta_time + " fps");
        Window.currentScene.update(delta_time);
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
        Window.currentScene.draw(g2D);
    }
    
    /**
     * switch between menuScene and playScene...:)
     * @param type type of new scene
     */
    public static void changeScene(SceneType type) {
        switch (type) {
            case MENU_SCENE -> Window.currentScene = new MenuScene();
            case PLAY_SCENE -> Window.currentScene = new PlayScene();
            default -> System.out.println("Invalid scene!");
        }
        Window.currentScene.load_resources();
        Window.currentScene.init();
        Window.currentScene.start();
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
        return currentScene;
    }
}
