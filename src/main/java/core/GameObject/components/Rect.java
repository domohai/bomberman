package core.GameObject.components;

import core.MouseController;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Rect extends Component {
    private int x, y;
    private int width, height;
    private int debounceX, debounceY;
    private BufferedImage idleImage, hoverImage, currentImage = null;

    public Rect(int x, int y, int width, int height, BufferedImage idleImage, BufferedImage hoverImage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleImage = idleImage;
        this.hoverImage = hoverImage;
    }

    @Override
    public void start() {
        x = gameObject.getPositionX();
        y = gameObject.getPositionY();
        debounceX = x + width;
        debounceY = y + height;
        currentImage = idleImage;
    }

    @Override
    public void update(double dt) {
        if (MouseController.getX() >= this.x && MouseController.getX() <= debounceX
        && MouseController.getY() >= this.y && MouseController.getY() <= debounceY) {
            currentImage = hoverImage;
        } else {
            currentImage = idleImage;
        }
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.drawImage(currentImage, x, y, currentImage.getWidth(), currentImage.getHeight(), null);
    }
}
