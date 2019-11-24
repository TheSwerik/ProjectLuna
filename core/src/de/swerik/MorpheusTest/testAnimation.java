package de.swerik.MorpheusTest;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

import javax.swing.plaf.synth.Region;

public class testAnimation {
    Timer animation;
    int frame = 0;
    int line = 0;

    public testAnimation(final Sprite spriteSheet, final TextureRegion[][] regions) {
        animation = new Timer();
        animation.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (++frame > 4) {
                    frame = 0;
                    if (++line == 2) {
                        line = 0;
                    }
                }
                spriteSheet.setRegion(regions[line][frame]);
            }
        }, 0, 1 / 20f);
        animation.stop();
    }
    public void stop(){
        animation.stop();
    }
    public void start(){
        animation.start();
    }
}
