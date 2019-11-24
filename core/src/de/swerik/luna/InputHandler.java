package de.swerik.luna;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import de.swerik.luna.Animation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InputHandler implements InputProcessor {
    Sprite sprite;
    float movement = 0f;
    boolean movingLeft = false;
    Animation animation;
    Sound sound;
    long id;

    Music music;

    private Body body;

    public InputHandler(Sprite sprite, Animation animation, Body body) {
        this.sprite = sprite;
        this.animation = animation;
        this.body = body;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Marco Bros. Banana.wav"));    //dont forget dispose
        music = Gdx.audio.newMusic(Gdx.files.internal("music/Marco Bros. Overworld.wav"));  //dont forget dispose
    }

    public float getMovement() {
        return movement;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            body.setLinearVelocity(-100f, -10f);
            if (sprite.getX() > -100) {
                movement = -5f;
                movingLeft = true;
            }
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            body.setLinearVelocity(100f, -10f);
            if (sprite.getX() < 1000) {
                movement = 5f;
                movingLeft = false;
            }
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            body.setLinearVelocity(0f, 100f);
        }
        if (keycode == Input.Keys.F) {
            id = sound.loop();
            sound.setVolume(id, 0.2f);
        }
        if (keycode == Input.Keys.P) {
            music.pause();
        }
        if (keycode == Input.Keys.M) {
            if (!music.isPlaying())
                music.play();
            else
                music.stop();
            music.setVolume(0.2f);
        }
        if (keycode == Input.Keys.R) {
            int samplingRate = 48000;
            final short[] data = new short[samplingRate];
            final AudioRecorder rec = Gdx.audio.newAudioRecorder(samplingRate, true);
            final AudioDevice player = Gdx.audio.newAudioDevice(samplingRate, true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    rec.read(data, 0, data.length);
                    rec.dispose();
                    player.writeSamples(data, 0, data.length);
                    player.dispose();
                }
            }).start();
        }
        if (keycode == Input.Keys.K) {
            byte[] pixel = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
            Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
            BufferUtils.copy(pixel, 0, pixmap.getPixels(), pixel.length);
            PixmapIO.writePNG(Gdx.files.local("screenshots/screenshot " + new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new Date()) + ".png"), pixmap);
            pixmap.dispose();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            if (sprite.getX() > -100) {
                movement = 0f;
            }
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            if (sprite.getX() < 1000) {
                movement = 0f;
            }
        }
        if (keycode == Input.Keys.F) {
            sound.stop(id);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            animation.start();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            animation.stop();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void testInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (sprite.getX() > -100) {
                sprite.translateX(-5f);
            }
            if (!sprite.isFlipX()) {
                sprite.flip(true, false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (sprite.getX() < 1000) {
                sprite.translateX(5f);
            }
        }
    }
}
