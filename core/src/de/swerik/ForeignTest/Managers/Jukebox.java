package de.swerik.ForeignTest.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class Jukebox {

    private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();

    static {
//        sounds = new HashMap<String, Sound>();
    }

    public static void load(String path, String name) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("tutorial/sounds/" + path));
        sounds.put(name, sound);
    }

    public static void play(String name) {
        sounds.get(name).play();
    }

    public static void play(String name, float volume) {
        sounds.get(name).play(volume);
    }

    public static void loop(String name) {
        sounds.get(name).loop();
    }

    public static void loop(String name, float volume) {
        sounds.get(name).loop(volume);
    }

    public static void stop(String name) {
        sounds.get(name).stop();
    }

    public static void stopAll() {
        for (Sound s : sounds.values()) {
            s.stop();
        }
    }

}
