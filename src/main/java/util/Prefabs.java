package util;

import core.GameObject.GameObject;
import core.GameObject.Transform;
import core.GameObject.components.Frame;
import core.GameObject.components.PlayerMovement;
import core.GameObject.components.SpriteSheet;
import core.GameObject.components.State;
import core.GameObject.components.StateMachine;
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
        GameObject player = new GameObject();
        // set position
        player.setTransform(new Transform(new Vector2D(50, 50), 0));
        // idle left state
        State idleLeft = new State("idleLeft");
        idleLeft.setLoop(false);
        idleLeft.addFrame(new Frame(runLeftSheet.getSheet().get(0), -1));
        // run left state
        State runLeft = new State("runLeft");
        runLeft.setLoop(true);
        for (int i = 1; i < runLeftSheet.getSheet().size(); i++) {
            runLeft.addFrame(new Frame(runLeftSheet.getSheet().get(i), Const.DEFAULT_FRAME_TIME));
        }
        // idle right state
        State idleRight = new State("idleRight");
        idleRight.setLoop(false);
        idleRight.addFrame(new Frame(runRightSheet.getSheet().get(0), -1));
        // run right state
        State runRight = new State("runRight");
        runRight.setLoop(true);
        for (int i = 1; i < runRightSheet.getSheet().size(); i++) {
            runRight.addFrame(new Frame(runRightSheet.getSheet().get(i), Const.DEFAULT_FRAME_TIME));
        }
        // idle up state
        State idleUp = new State("idleUp");
        idleUp.setLoop(false);
        idleUp.addFrame(new Frame(runUpSheet.getSheet().get(0), -1));
        // run up state
        State runUp = new State("runUp");
        runUp.setLoop(true);
        for (int i = 1; i < runUpSheet.getSheet().size(); i++) {
            runUp.addFrame(new Frame(runUpSheet.getSheet().get(i), Const.DEFAULT_FRAME_TIME));
        }
        // idle down state
        State idleDown = new State("idleDown");
        idleDown.setLoop(false);
        idleDown.addFrame(new Frame(runDownSheet.getSheet().get(0), -1));
        // run down state
        State runDown = new State("runDown");
        runDown.setLoop(true);
        for (int i = 1; i < runDownSheet.getSheet().size(); i++) {
            runDown.addFrame(new Frame(runDownSheet.getSheet().get(i), Const.DEFAULT_FRAME_TIME));
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
}
