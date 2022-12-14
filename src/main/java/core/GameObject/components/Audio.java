package core.GameObject.components;

import util.Const;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Audio {
    // to store current position
    private Long currentFrame = 0L;
    private Clip clip = null;
    // current status of clip
    private String status = "paused";
    private AudioInputStream audioInputStream = null;
    private String filePath;
    private boolean loop;
    private FloatControl gainControl = null;
    private float volume;
    
    public Audio(String path, boolean loop) {
        try {
            // create AudioInputStream object
            audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            // create clip reference
            clip = AudioSystem.getClip();
            // open audioInputStream to the clip
            clip.open(audioInputStream);
            if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("Error when load audio!");
            e.printStackTrace();
        }
        this.loop = loop;
        this.filePath = path;
    }
    
    public void setVolume(float volume) {
        if (volume < 0f) volume = 0f;
        else if (volume > 1f) volume = 1f;
        this.volume = volume;
        if (status.equals("play")) {
            pause();
            gainControl.setValue(20f * (float) Math.log10(volume));
            resume();
        } else {
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
    }
    
    public void play() {
        // start the clip
        clip.start();
        status = "play";
    }
    
    public void pause() {
        if (status.equals("paused")) {
            return;
        }
        currentFrame = clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }
    
    public void resume() {
        if (status.equals("play")) {
            return;
        }
//        clip.close();
//        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        gainControl.setValue(20f * (float) Math.log10(volume));
        if (filePath.equals(Const.BACKGROUND_MUSIC)) {
            play();
        }
    }
    
    public void restart() {
        clip.stop();
//        clip.close();
//        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        play();
    }
    
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }
    
    public void reset() {
        clip.stop();
        currentFrame = 0L;
        clip.setMicrosecondPosition(currentFrame);
    }
    
    public void resetAudioStream() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip.close();
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("Error when reset audio stream!");
            e.printStackTrace();
        }
    }
    
    public boolean isLoop() {
        return loop;
    }
    
    public String getStatus() {
        return status;
    }
    
    public float getVolume() {
        return volume;
    }
}
