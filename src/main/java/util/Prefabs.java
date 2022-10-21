package util;

import core.GameObject.GameObject;
import core.GameObject.ObjectType;
import core.GameObject.Transform;
import core.GameObject.components.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import static javax.imageio.ImageIO.*;

public class Prefabs {
    public static BufferedImage readImage(String path) {
        BufferedImage parent = null;
        try {
            parent = read(new File(path));
        } catch (IOException e) {
            System.out.println("Cannot load image!");
            e.printStackTrace();
        }
        return parent;
    }

    public static char[][] loadMap(String path) {
        char[][] a = null;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            StringTokenizer st = new StringTokenizer(line);
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            a = new char[row][col];
            for (int i = 0; i < row; i++) {
                line = reader.readLine();
                for (int j = 0; j < line.length(); j++)
                    a[i][j] = line.charAt(j);
            }
            reader.close();
        } catch(IOException e) {
            System.out.println("Can't load map");
            e.printStackTrace();
        }
        return a;
    }
    
    public static GameObject generateBomb() {
        SpriteSheet sheet = AssetsPool.getSpriteSheet("src/main/resources/bomb_scaled.png");
        if (sheet == null) {
            System.out.println("Bomb sheet was not loaded");
            return null;
        }
        // create bomb
        GameObject bomb = new GameObject(ObjectType.UNSTABLE);
        // state
        State countDown = new State("count");
        countDown.setLoop(false);
        for (int i = 0; i < sheet.size(); i++) {
            countDown.addFrame(new Frame(sheet.getSprite(i), 0.6));
        }
        // machine
        StateMachine machine = new StateMachine();
        machine.addState(countDown);
        machine.setDefaultState("count");
        bomb.addComponent(machine);
        bomb.addComponent(new Bomb());
        return bomb;
    }
    
    public static GameObject generateFlame() {
        SpriteSheet sheet = AssetsPool.getSpriteSheet("src/main/resources/Flame.png");
        if (sheet == null) {
            System.out.println("Flame sheet was not loaded");
            return null;
        }
        // create explosion
        GameObject flame = new GameObject(ObjectType.FLAME);
        // State
        State flameState = new State("idle");
        flameState.setLoop(false);
        for (int i = 0; i < sheet.size(); i++) {
            flameState.addFrame(new Frame(sheet.getSprite(i), Const.DEFAULT_FRAME_TIME));
        }
        // machine
        StateMachine machine = new StateMachine();
        machine.addState(flameState);
        machine.setDefaultState("idle");
        flame.addComponent(machine);
        flame.addComponent(new Flame());
        return flame;
    }

    public static GameObject generateBlock() {
        SpriteSheet sheet = AssetsPool.getSpriteSheet("src/main/resources/Wall.png");
        if (sheet == null) {
            System.out.println("Block sheet was not loaded");
            return null;
        }
        GameObject block = new GameObject();
        StateMachine machine = new StateMachine();
        State idle = new State("idle");
        idle.setLoop(false);
        idle.addFrame(new Frame(sheet.getSprite(0), -1));
        machine.addState(idle);
        machine.setDefaultState("idle");
        block.addComponent(machine);
        return block;
    }

    public static GameObject generatePlayer() {
        SpriteSheet runLeftSheet = AssetsPool.getSpriteSheet("src/main/resources/DoctorBombLeft.png");
        SpriteSheet runRightSheet = AssetsPool.getSpriteSheet("src/main/resources/DoctorBombRight.png");
        SpriteSheet runUpSheet = AssetsPool.getSpriteSheet("src/main/resources/DoctorBombUp.png");
        SpriteSheet runDownSheet = AssetsPool.getSpriteSheet("src/main/resources/DoctorBombDown.png");
        // check
        if (runLeftSheet == null || runDownSheet == null
                || runRightSheet == null || runUpSheet == null) {
            System.out.println("Forgot to load doctorBomb spriteSheet!");
            return null;
        }
        // create new game object
        GameObject player = new GameObject(ObjectType.PLAYER);
        // idle left state
        State idleLeft = new State("idleLeft");
        idleLeft.setLoop(false);
        idleLeft.addFrame(new Frame(runLeftSheet.getSprite(0), -1));
        // run left state
        State runLeft = new State("runLeft");
        runLeft.setLoop(true);
        for (int i = 1; i < runLeftSheet.size(); i++) {
            runLeft.addFrame(new Frame(runLeftSheet.getSprite(i), Const.DEFAULT_FRAME_TIME));
        }
        // idle right state
        State idleRight = new State("idleRight");
        idleRight.setLoop(false);
        idleRight.addFrame(new Frame(runRightSheet.getSprite(0), -1));
        // run right state
        State runRight = new State("runRight");
        runRight.setLoop(true);
        for (int i = 1; i < runRightSheet.size(); i++) {
            runRight.addFrame(new Frame(runRightSheet.getSprite(i), Const.DEFAULT_FRAME_TIME));
        }
        // idle up state
        State idleUp = new State("idleUp");
        idleUp.setLoop(false);
        idleUp.addFrame(new Frame(runUpSheet.getSprite(0), -1));
        // run up state
        State runUp = new State("runUp");
        runUp.setLoop(true);
        for (int i = 1; i < runUpSheet.size(); i++) {
            runUp.addFrame(new Frame(runUpSheet.getSprite(i), Const.DEFAULT_FRAME_TIME));
        }
        // idle down state
        State idleDown = new State("idleDown");
        idleDown.setLoop(false);
        idleDown.addFrame(new Frame(runDownSheet.getSprite(0), -1));
        // run down state
        State runDown = new State("runDown");
        runDown.setLoop(true);
        for (int i = 1; i < runDownSheet.size(); i++) {
            runDown.addFrame(new Frame(runDownSheet.getSprite(i), Const.DEFAULT_FRAME_TIME));
        }
        // create state machine
        StateMachine machine = new StateMachine();
        // add idle states
        machine.addState(idleUp);
        machine.addState(idleRight);
        machine.addState(idleLeft);
        machine.addState(idleDown);
        // add run states
        machine.addState(runDown);
        machine.addState(runUp);
        machine.addState(runRight);
        machine.addState(runLeft);
        // add triggers and set default
        machine.setDefaultState(idleDown.getState());
        player.addComponent(machine);
        // player movement
        PlayerMovement movement = new PlayerMovement();
        player.addComponent(movement);
        return player;
    }

    public static GameObject generateButton(BufferedImage idleImage, BufferedImage hoverImage) {
        GameObject button = new GameObject();
        // set position
        Rect menuRect = new Rect(0,0,idleImage.getHeight(), idleImage.getWidth(), idleImage, hoverImage);
        button.addComponent(menuRect);
        return button;
    }
    
    public static GameObject generateBot() {
        SpriteSheet runLeftSheet = AssetsPool.getSpriteSheet("src/main/resources/RedOverlordLeft.png");
        SpriteSheet runRightSheet = AssetsPool.getSpriteSheet("src/main/resources/RedOverlordRight.png");
        SpriteSheet runUpSheet = AssetsPool.getSpriteSheet("src/main/resources/RedOverlordUp.png");
        SpriteSheet runDownSheet = AssetsPool.getSpriteSheet("src/main/resources/RedOverlordDown.png");
        // check
        if (runLeftSheet == null || runDownSheet == null
                || runRightSheet == null || runUpSheet == null) {
            System.out.println("Forgot to load RedOverlord spriteSheet!");
            return null;
        }
        // create new game object
        GameObject bot = new GameObject(ObjectType.MOVING);
        // idle left state
        State idleLeft = new State("idleLeft");
        idleLeft.setLoop(false);
        idleLeft.addFrame(new Frame(runLeftSheet.getSprite(0), -1));
        // run left state
        State runLeft = new State("runLeft");
        runLeft.setLoop(true);
        for (int i = 1; i < runLeftSheet.size(); i++) {
            runLeft.addFrame(new Frame(runLeftSheet.getSprite(i), Const.DEFAULT_FRAME_TIME));
        }
        // idle right state
        State idleRight = new State("idleRight");
        idleRight.setLoop(false);
        idleRight.addFrame(new Frame(runRightSheet.getSprite(0), -1));
        // run right state
        State runRight = new State("runRight");
        runRight.setLoop(true);
        for (int i = 1; i < runRightSheet.size(); i++) {
            runRight.addFrame(new Frame(runRightSheet.getSprite(i), Const.DEFAULT_FRAME_TIME));
        }
        // idle up state
        State idleUp = new State("idleUp");
        idleUp.setLoop(false);
        idleUp.addFrame(new Frame(runUpSheet.getSprite(0), -1));
        // run up state
        State runUp = new State("runUp");
        runUp.setLoop(true);
        for (int i = 1; i < runUpSheet.size(); i++) {
            runUp.addFrame(new Frame(runUpSheet.getSprite(i), Const.DEFAULT_FRAME_TIME));
        }
        // idle down state
        State idleDown = new State("idleDown");
        idleDown.setLoop(false);
        idleDown.addFrame(new Frame(runDownSheet.getSprite(0), -1));
        // run down state
        State runDown = new State("runDown");
        runDown.setLoop(true);
        for (int i = 1; i < runDownSheet.size(); i++) {
            runDown.addFrame(new Frame(runDownSheet.getSprite(i), Const.DEFAULT_FRAME_TIME));
        }
        // create state machine
        StateMachine machine = new StateMachine();
        // add idle states
        machine.addState(idleUp);
        machine.addState(idleRight);
        machine.addState(idleLeft);
        machine.addState(idleDown);
        // add run states
        machine.addState(runDown);
        machine.addState(runUp);
        machine.addState(runRight);
        machine.addState(runLeft);
        // add triggers and set default
        machine.setDefaultState(idleDown.getState());
        bot.addComponent(machine);
        // bot movement
        BotMovement movement = new BotMovement();
        bot.addComponent(movement);
        return bot;
    }
}
