package util;

import core.GameObject.components.SpriteSheet;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetsPool {
    private static Map<String, SpriteSheet> pool = new HashMap<>();

    public static void addSpriteSheet(String path, SpriteSheet sheet) {
        path = (new File(path)).getAbsolutePath();
        if (AssetsPool.pool.containsKey(path)) {
            System.out.println("Already added asset at: " + path);
            return;
        }
        AssetsPool.pool.put(path, sheet);
    }

    public static SpriteSheet getSpriteSheet(String path) {
        path = (new File(path)).getAbsolutePath();
        SpriteSheet spriteSheet = AssetsPool.pool.get(path);
        if (spriteSheet == null) {
            System.out.println("Spritesheet have not been added yet!");
            return null;
        }
        return spriteSheet;
    }
}
