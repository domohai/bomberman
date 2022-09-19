package core;

import util.Const;
import util.Time;
import javax.swing.JFrame;

public class Window extends JFrame implements Runnable {
    private static Window window = null; // the only window
    private boolean isRunning;

    private Window() {
        this.setSize(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        this.setTitle(Const.SCREEN_TITLE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(MouseController.get()); // add mouseListener
        this.addKeyListener(KeyController.get()); // add keyListener
        this.setVisible(true);
        this.isRunning = true;
    }
    
    public void init() {
    
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void update(double delta_time) {
        //System.out.println(1/delta_time + "fps");
        
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        double time;
        double delta_time;
        try {
            while (isRunning) {
                time = Time.getTime();
                delta_time = time - lastFrameTime;
                lastFrameTime = time;
                update(delta_time);
                Thread.sleep(16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
