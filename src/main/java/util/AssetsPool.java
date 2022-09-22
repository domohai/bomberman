package util;

import components.Sprite;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetsPool {
    private static Map<String, Sprite> sprites = new HashMap<>();

    public static boolean hasSprite(String path) {
        File file = new File(path);
        return AssetsPool.sprites.containsKey(file.getAbsolutePath());
    }

    public static Sprite getSprite(String path) {
        File file = new File(path);
        if (AssetsPool.hasSprite(path)) {
            return AssetsPool.sprites.get(file.getAbsolutePath());
        } else {
            Sprite sprite = new Sprite(path);
            AssetsPool.addSprite(path,sprite);
            return AssetsPool.sprites.get(file.getAbsolutePath());
        }
    }

    public static void addSprite(String path, Sprite sprite) {
        File file = new File(path);
        if (AssetsPool.hasSprite(path)) {
            System.out.println("Already added sprite at: " + file.getAbsolutePath());
//            System.exit(-1);
        } else {
            AssetsPool.sprites.put(file.getAbsolutePath(), sprite);
        }
    }


}
