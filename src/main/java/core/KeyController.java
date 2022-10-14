package core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyController extends KeyAdapter {
    private static KeyController instance = null;
    private boolean[] keyPressed = new boolean[600];
    
    private KeyController() {}
    
    public static KeyController get() {
        if (KeyController.instance == null) {
            KeyController.instance = new KeyController();
        }
        return KeyController.instance;
    }
    
    public static boolean is_keyPressed(int keycode) {
        return get().keyPressed[keycode];
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed[e.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
    }
}
