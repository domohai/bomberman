package util;

import core.GameObject.components.Audio;
import core.GameObject.components.SpriteSheet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetsPool {
    private static final Map<String, SpriteSheet> spritePool = new HashMap<>();
    private static final Map<String, Audio> audioPool = new HashMap<>();
    private static final Map<String, BufferedImage> buttonPool = new HashMap<>();
    
    public static void addSpriteSheet(String path, SpriteSheet sheet) {
        File file = new File(path);
        if (AssetsPool.spritePool.containsKey(file.getAbsolutePath())) {
            System.out.println("Already added asset: " + file.getAbsolutePath());
            return;
        }
        AssetsPool.spritePool.put(file.getAbsolutePath(), sheet);
    }

    public static SpriteSheet getSpriteSheet(String path) {
        File file = new File(path);
        SpriteSheet spriteSheet = AssetsPool.spritePool.get(file.getAbsolutePath());
        if (spriteSheet == null) {
            System.out.println("Spritesheet has not been added yet: " + file.getAbsolutePath());
            return null;
        }
        return spriteSheet;
    }
    
    public static void addAudio(String path, boolean loop, float volume) {
        File file = new File(path);
        if (AssetsPool.audioPool.containsKey(file.getAbsolutePath())) {
            System.out.println("Audio already exist: " + file.getAbsolutePath());
            return;
        }
        Audio audio = new Audio(file.getAbsolutePath(), loop);
        audio.setVolume(volume);
        AssetsPool.audioPool.put(file.getAbsolutePath(), audio);
    }
    
    public static Audio getAudio(String path) {
        File file = new File(path);
        Audio audio = AssetsPool.audioPool.get(file.getAbsolutePath());
        if (audio == null) {
            System.out.println("Audio has not been added yet: " + file.getAbsolutePath());
            return null;
        }
        return audio;
    }
    
    public static void addButton(String path) {
        File file = new File(path);
        if (buttonPool.containsKey(file.getAbsolutePath())) {
            System.out.println("Button image already exist: " + file.getAbsolutePath());
            return;
        }
        BufferedImage image = Prefabs.readImage(file.getAbsolutePath());
        buttonPool.put(file.getAbsolutePath(), image);
    }
    
    public static BufferedImage getButton(String path) {
        File file = new File(path);
        BufferedImage image = buttonPool.get(file.getAbsolutePath());
        if (image == null) {
            System.out.println("Button image has not been added: " + file.getAbsolutePath());
            return null;
        }
        return image;
    }
}
