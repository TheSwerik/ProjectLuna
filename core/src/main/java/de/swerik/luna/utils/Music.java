package de.swerik.luna.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import jdk.vm.ci.aarch64.AArch64;

public class Music {
    private com.badlogic.gdx.audio.Music track1;
    private com.badlogic.gdx.audio.Music track2;
    private com.badlogic.gdx.audio.Music track3;

    public Music(AssetManager assetManager) {
        AssetDescriptor<com.badlogic.gdx.audio.Music> baseline = new AssetDescriptor<>("placeholder\\music\\Marco Bros Bassline vorbis.ogg", com.badlogic.gdx.audio.Music.class);
        AssetDescriptor<com.badlogic.gdx.audio.Music> synths = new AssetDescriptor<>("placeholder\\music\\Marco Bros Synths vorbis.ogg", com.badlogic.gdx.audio.Music.class);
        AssetDescriptor<com.badlogic.gdx.audio.Music> complete = new AssetDescriptor<>("placeholder\\music\\Marco Bros Complete vorbis.ogg", com.badlogic.gdx.audio.Music.class);

        assetManager.load(baseline);
        assetManager.load(synths);
        assetManager.load(complete);
        assetManager.finishLoading();

        track1 = assetManager.get(baseline);
        track2 = assetManager.get(synths);
        track3 = assetManager.get(complete);
    }

    public void play() {
        track1.setVolume(1);
        track2.setVolume(1);
        track3.setVolume(1);
        track1.play();
//        track2.play();
//        track3.play();

        track2.setPosition(track1.getPosition());
        track3.setPosition(track1.getPosition());
    }

    public void both() {
        track1.setVolume(1f);
        track2.setVolume(1f);
        track2.play();
        track2.setPosition(track1.getPosition());
        track3.setVolume(0f);
    }

    public void bass() {
        float barsPerSecond = 0.75f;
        while (!(track1.getPosition() % barsPerSecond <= 0.1f && track1.getPosition() % barsPerSecond >= -0.1f)) {
            System.out.println(track1.getPosition());
        }
        track1.setVolume(1f);
        track2.setVolume(0f);
        track3.setVolume(0f);
    }

    public void synth() {
        track1.setVolume(0);
        track2.play();
        track2.setPosition(track1.getPosition());
//        track2.setVolume(1);
//        track3.setVolume(0f);
    }

    public void complete() {
        track1.setVolume(0);
        track2.setVolume(0);
        track3.setVolume(1);
    }
}
