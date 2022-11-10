package core.GameObject.components;

import java.awt.image.BufferedImage;

public class Frame {
    private transient BufferedImage image;
    private double displayTime;
    
    public Frame() {
    }
    
    public Frame(BufferedImage image, double displayTime) {
        this.image = image;
        this.displayTime = displayTime;
    }
    
    public double getDisplayTime() {
        return this.displayTime;
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
}
