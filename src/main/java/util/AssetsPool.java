package util;

import components.Sprite;
import components.SpriteSheet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetsPool {
    //    private static Map<String, Sprite> sprites = new HashMap<>();
//
//    public static Sprite getSprite(String path) {
//        path = (new File(path)).getAbsolutePath();
//        if (!AssetsPool.sprites.containsKey(path)) {
//            Sprite sprite = new Sprite(path);
//            AssetsPool.addSprite(path,sprite);
//        }
//        return AssetsPool.sprites.get(path);
//    }
//
//    public static void addSprite(String path, Sprite sprite) {
//        path = (new File(path)).getAbsolutePath();
//        if (AssetsPool.sprites.containsKey(path)) {
//            System.out.println("Already added sprite at: " + path);
//        } else {
//            AssetsPool.sprites.put(path, sprite);
//        }
//    }
    private static Map<String, SpriteSheet> pool = new HashMap<>();

    public static void addSpriteSheet(String path, SpriteSheet sheet) {
        path = (new File(path)).getAbsolutePath();
        if (AssetsPool.pool.containsKey(path)) {
            System.out.println("Already added asset at: " + path);
        } else {
            AssetsPool.pool.put(path, sheet);
        }
    }

    public static SpriteSheet getSpriteSheet(String path) {
        path = (new File(path)).getAbsolutePath();
        return AssetsPool.pool.get(path);
    }
}
