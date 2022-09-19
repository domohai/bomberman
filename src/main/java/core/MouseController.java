package core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseController extends MouseAdapter {
    private static MouseController instance = null; // the only mouseController
    private double xPos, yPos, dx, dy;
    private boolean[] mouseButton = new boolean[9];
    private boolean mouseDragged;
    
    private MouseController() {
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.dx = 0.0;
        this.dy = 0.0;
        this.mouseDragged = false;
    }
    
    public static MouseController get() {
        if (MouseController.instance == null) {
            MouseController.instance = new MouseController();
        }
        return MouseController.instance;
    }
    
    public static boolean isMousePressed(int key) {
        return get().mouseButton[key];
    }
    
    public static double getX() {
        return get().xPos;
    }
    
    public static double getY() {
        return get().yPos;
    }
    
    public static double getDx() {
        return get().dx;
    }
    
    public static double getDy() {
        return get().dy;
    }
    
    public static boolean isDragging() {
        return get().mouseDragged;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseButton[e.getButton()] = true;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseButton[e.getButton()] = false;
        this.mouseDragged = false;
        this.dx = 0.0;
        this.dy = 0.0;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        this.xPos = e.getX();
        this.yPos = e.getY();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseDragged = true;
        this.dx = e.getX() - this.xPos;
        this.dy = e.getY() - this.yPos;
    }
}
