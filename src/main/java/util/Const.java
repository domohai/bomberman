package util;

import java.awt.image.BufferedImage;

public class Const {
    // General
    public static final int TILE_W = 64;
    public static final int TILE_H = 64;
    public static final int HALF_TILE_W = 32;
    public static final int HALF_TILE_H = 32;
    public static final int SCREEN_WIDTH = TILE_W * 20 - 50;
    public static final int SCREEN_HEIGHT = TILE_H * 12 + 37;
    public static final String SCREEN_TITLE = "Bomberman";

    public static final double DEFAULT_FRAME_TIME = 0.08;
    public static final double FLAME_TIME = 0.04;

    // Player
    public static final int PLAYER_SPEED = 150;
    public static final int INITIAL_MAP = 1;
    public static final int INITIAL_FLAME_SIZE = 1;
    public static final int INITIAL_BOMB_NUMBER = 1;
    public static final int INITIAL_HP = 3;

    // Bomb
    public static final int BOMB_WIDTH = 40;
    public static final int BOMB_HEIGHT = 52;
    public static final int HALF_BOMB_W = 20;
    public static final int HALF_BOMB_H = 26;
    public static final int FLAME_SIZE = 48;
    public static final int HALF_FLAME_SIZE = 24;
    
    // audio paths
    public static final String EXPLOSION_SOUND = "src/main/resources/audio/Explosion4.wav";
    public static final String BACKGROUND_MUSIC = "src/main/resources/audio/Dungeon1.wav";
    public static final String ITEM_SOUND = "src/main/resources/audio/Item1.wav";
    
    // map paths
    public static final int MAX_LEVEL = 3;
    public static final String LEVEL_1 = "src/main/resources/Levels/Level1.txt";
    public static final String LEVEL_2 = "src/main/resources/Levels/Level2.txt";
    public static final String LEVEL_3 = "src/main/resources/Levels/Level3.txt";
    
    // background
    public static final BufferedImage background = Prefabs.readImage("src/main/resources/background.png");
    public static final BufferedImage blur_background = Prefabs.readImage("src/main/resources/blur_background.png");
    // heart
    public static final BufferedImage heart = Prefabs.readImage("src/main/resources/heart1.png");
    // buttons
    public static final int SQUARE_BUTTON = 60;
    public static final int BUTTON_WIDTH = 300;
    public static final int BUTTON_HEIGHT = 100;
    public static final int BUTTON_OFFSET = 105;

    // volume
    public static final float DEFAULT_VOLUME = 0.08f;
    public static final float MIN_VOLUME = 0.0f;
    public static final float MAX_VOLUME = 0.5f;

    // z index
    public static final int MIN_Z_INDEX = -10;
    public static final int STILL_OBJECT_ZINDEX = -5;
    public static final int ITEM_ZINDEX = -1;
    public static final int PLAYER_ZINDEX = 0;
    public static final int BOT_ZINDEX = 3;
    public static final int BREAKABLE_ROCK_ZINDEX = 4;
    public static final int FLAME_ZINDEX = 5;
    public static final int MAX_Z_INDEX = 10;
    
}
