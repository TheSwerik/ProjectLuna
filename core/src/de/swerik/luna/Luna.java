package de.swerik.luna;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

public class Luna extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    int frame = 0;
    int line = 0;
    TextureRegion[][] regions;
    Sprite sprite;
    InputHandler inputHandler;
    Timer running;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("ninjaboy/Running.png");
        regions = TextureRegion.split(img, 363, 458);
        sprite = new Sprite(regions[0][0]);
        sprite.setScale(0.25f);
        running = new Timer();
        this.testTimer();
        running.stop();
        inputHandler = new InputHandler(sprite,running);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprite.translateX(inputHandler.getMovement());
        if (!sprite.isFlipX()) {
            sprite.flip(inputHandler.isMovingLeft(), false);
        }
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    private void testTimer() {
        running.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (++frame > 4) {
                    frame = 0;
                    if (++line == 2) {
                        line = 0;
                    }
                }
                sprite.setRegion(regions[line][frame]);
            }
        }, 0, 1 / 20f);
    }
}
