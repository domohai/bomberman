package components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite extends Component {
    public String path;
    public BufferedImage image;
    int width, height;
    int row, col;

    public Sprite(String path) {
        this.path = path;
        try {
//            File file = new File(path);
            image = ImageIO.read(new File(path));
            width = image.getWidth();
            height = image.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("Can't load image file at: " + path);
//            System.exit(-1);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int x = row;
        int y = col;
        g2.drawImage(image, x, y, width, height, null);
    }


}