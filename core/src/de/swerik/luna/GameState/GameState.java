package de.swerik.luna.GameState;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;

public abstract class GameState implements Screen {

    protected Luna app;
    protected GameStateManager gsm;

    protected SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;

    public GameState(Luna app, GameStateManager gsm) {
        this.app = app;
        this.gsm = gsm;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        create();
    }

    public abstract void create();

    public abstract void update(float delta);

    public abstract void render();

    public abstract void dispose();

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {
        render();
    }
}