package core.Window;

import core.GameObject.components.Audio;
import util.AssetsPool;

public class Sound {
    /**
     * play a specific audio
     * @param path the path to the audio file
     */
    public static void play(String path) {
        Audio audio = AssetsPool.getAudio(path);
        audio.reset();
        audio.play();
    }
}
