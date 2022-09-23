package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Prefabs {
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
