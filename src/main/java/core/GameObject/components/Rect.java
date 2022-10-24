package core.GameObject.components;

import core.MouseController;
import core.Window.Scenes.PlayScene;
import core.Window.Window;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Rect extends Component {
    private int x, y;
    private int width, height;
    private int debounceX, debounceY;
    private BufferedImage idleImage, hoverImage, currentImage = null;

    public Rect(BufferedImage idleImage, BufferedImage hoverImage) {
        this.x = 0;
        this.y = 0;
        this.width = idleImage.getWidth();
        this.height = idleImage.getHeight();
        this.idleImage = idleImage;
        this.hoverImage = hoverImage;
    }

    @Override
    public void start() {
        x = gameObject.getPositionX();
        y = gameObject.getPositionY();
        debounceX = x + width;
        debounceY = y + 30 + height;
        currentImage = idleImage;
    }

    @Override
    public void update(double dt) {
        if (MouseController.getX() >= x && MouseController.getX() <= debounceX
        && MouseController.getY() >= y + 30 && MouseController.getY() <= debounceY) {
            currentImage = hoverImage;
            if (MouseController.isMousePressed(MouseEvent.BUTTON1)) {
                // todo: handle action
                
            }
        } else {
            currentImage = idleImage;
        }
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.drawImage(currentImage, x, y, width, height, null);
    }
}
