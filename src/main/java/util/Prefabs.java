package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Prefabs {
    //    public static BufferedImage createSubImage(BufferedImage image, int x, int y, int width, int height) {
//        return image.getSubimage(x, y, width, height);
//    }
    public static BufferedImage readImage(String path) {
        BufferedImage parent = null;
        try {
            parent = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Cannot load image!");
            e.printStackTrace();
        }
        return parent;
    }
}
