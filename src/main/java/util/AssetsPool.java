package util;

import core.GameObject.components.Audio;
import core.GameObject.components.SpriteSheet;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetsPool {
    private static Map<String, SpriteSheet> pool = new HashMap<>();
    private static Map<String, Audio> audioPool = new HashMap<>();
    
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
    
    public static void addAudio(String path, boolean loop) {
        File file = new File(path);
        if (audioPool.containsKey(file.getAbsolutePath())) {
            System.out.println("Audio already exist!");
            return;
        }
        Audio audio = new Audio(file.getAbsolutePath(), loop);
        audioPool.put(file.getAbsolutePath(), audio);
    }
    
    public static Audio getAudio(String path) {
        return audioPool.get(new File(path).getAbsolutePath());
    }
}
