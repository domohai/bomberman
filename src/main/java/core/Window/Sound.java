package core.Window;

import core.GameObject.components.Audio;
import util.AssetsPool;
import javax.sound.sampled.FloatControl;

public class Sound {
    /**
     * play a specific audio
     * @param path the path to the audio file
     * @param volume this value should be between 0.0f and 1.0f
     */
    public static void play(String path, float volume) {
        Audio audio = AssetsPool.getAudio(path);
        // get volume in floating point
        FloatControl gainControl = audio.getGainControl();
        // check if the volume is valid
        if (volume < 0f) volume = 0f;
        else if (volume > 1f) volume = 1f;
        gainControl.setValue(20f * (float) Math.log10(volume));
        audio.play();
    }
    
    
}
