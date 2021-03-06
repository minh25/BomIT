package engine;

import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundManager {
    ExecutorService soundPool = Executors.newFixedThreadPool(2);
    Map<String, AudioClip> soundEffectsMap = new HashMap<>();

    public SoundManager(int numberOfThreads) {
        soundPool = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void loadSoundEffects(String id, URL url) {
        if (url == null) {
            System.out.println("Sound url is null!");
            return;
        }
        AudioClip sound = new AudioClip(url.toExternalForm());
        soundEffectsMap.put(id, sound);
    }

    public void playSound(final String id) {
        Runnable soundPlay = () -> {
            AudioClip audioClip = soundEffectsMap.get(id);
            audioClip.setVolume(0.5);
            audioClip.play();

        };
        soundPool.execute(soundPlay);
    }

    public void shutdown() {
        soundPool.shutdown();
    }
}
