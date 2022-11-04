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

    public static final int MIN_Z_INDEX = -10;
    public static final int MAX_Z_INDEX = 10;

    public static final double DEFAULT_FRAME_TIME = 0.08;
    public static final double FLAME_TIME = 0.04;

    // Player
    public static final int PLAYER_SPEED = 150;

    // Bomb
    public static final int BOMB_WIDTH = 40;
    public static final int BOMB_HEIGHT = 52;
    public static final int HALF_BOMB_W = 20;
    public static final int HALF_BOMB_H = 26;
    public static final int FLAME_SIZE = 48;
    public static final int HALF_FLAME_SIZE = 24;
    
    // audio paths
    public static final String BACKGROUND_AUDIO = "C:/Users/Asus/Videos/The_Quarry_Opening_Credits_Song.wav";
    public static final String TEST_AUDIO = "D:/videos/cuoi.wav";
    // map paths
    public static final String LEVEL_1 = "src/main/resources/Level1.txt";
    // background
    public static final BufferedImage background = Prefabs.readImage("src/main/resources/background.png");
    public static final BufferedImage blur_background = Prefabs.readImage("src/main/resources/blur_background.png");

    // buttons
    public static final int SQUARE_BUTTON = 60;
    public static final int BUTTON_WIDTH = 300;
    public static final int BUTTON_HEIGHT = 100;
    public static final int BUTTON_OFFSET = 105;

    // volume
    public static final float DEFAULT_VOLUME = 0.4f;
    public static final float MUTE_VOLUME = 0.0f;
    public static final float MAX_VOLUME = 1.0f;


}
