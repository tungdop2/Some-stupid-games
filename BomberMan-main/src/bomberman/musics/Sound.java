package bomberman.musics;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip clip;
    private URL url;
    private int time;

    public Sound(String url1, int time1){
        url = this.getClass().getClassLoader().getResource(url1);
        time = time1;
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
        }
        catch (Exception e) {

        }
    }
    public void play() {
        clip.start();
        if (time == -1) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.loop(time);
        }
    }
    public void stop() {
        clip.stop();
    }
}
