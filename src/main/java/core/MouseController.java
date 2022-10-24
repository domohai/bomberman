package core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseController extends MouseAdapter {
    private static MouseController instance = new MouseController(); // the only mouseController
    private double xPos, yPos, dx, dy;
    private boolean[] mouseButton = new boolean[9];
    private boolean mouseDragged;
    
    private MouseController() {
        this.mouseDragged = false;
    }
    
    public static MouseController get() {
        return MouseController.instance;
    }
    
    public static boolean isMousePressed(int key) {
        return instance.mouseButton[key];
    }
    
    public static double getX() {
        return instance.xPos;
    }
    
    public static double getY() {
        return instance.yPos;
    }
    
    public static double getDx() {
        return instance.dx;
    }
    
    public static double getDy() {
        return instance.dy;
    }
    
    public static boolean isDragging() {
        return instance.mouseDragged;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseButton[e.getButton()] = true;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseButton[e.getButton()] = false;
        this.mouseDragged = false;
        dx = 0;
        dy = 0;
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
