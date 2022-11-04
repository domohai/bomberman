package core.GameObject.components;

import core.MouseController;
import core.Window.Scenes.PlayScene;
import core.Window.Scenes.SceneType;
import core.Window.Window;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Rect extends Component {
    private int x, y;
    private int width, height;
    private int debounceX, debounceY;
    private BufferedImage idleImage, hoverImage, currentImage = null;
    private ButtonType type;

    public Rect(BufferedImage idleImage, BufferedImage hoverImage, ButtonType type) {
        this.x = 0;
        this.y = 0;
        this.width = idleImage.getWidth();
        this.height = idleImage.getHeight();
        this.idleImage = idleImage;
        this.hoverImage = hoverImage;
        this.type = type;
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
                handleAction(type);
            }
        } else {
            currentImage = idleImage;
        }
    }

    // Function Button
    private static void handleAction(ButtonType type){
        switch (type) {
            case SETTING -> ((PlayScene)Window.getCurrentScene()).setPause(true);
            case PLAY -> Window.changeScene(SceneType.PLAY_SCENE);
            case QUIT -> Window.get().exit();
            case RESUME -> ((PlayScene)Window.getCurrentScene()).setPause(false);
            case MENU -> Window.changeScene(SceneType.MENU_SCENE);

        }
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.drawImage(currentImage, x, y, width, height, null);
    }
}
