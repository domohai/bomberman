package core.GameObject.components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite extends Component {
    public String path;
    public BufferedImage image;
    private int width, height;

    public Sprite(String path) {
        this.path = path;
        try {
//            File file = new File(path);
            image = ImageIO.read(new File(path));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
//            System.out.println("Can't load image file at: " + path);
//            System.exit(-1);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return image;
    }
}