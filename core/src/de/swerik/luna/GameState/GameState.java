package de.swerik.luna.GameState;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;

public abstract class GameState implements Screen {

    protected final Luna app;
    protected final GameStateManager gsm;

    protected final SpriteBatch batch;
    protected final ShapeRenderer shapeRenderer;

    public GameState(final Luna app, final GameStateManager gsm) {
        this.app = app;
        this.gsm = gsm;

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public abstract void show();

    @Override
    public abstract void hide();

    public abstract void update(float delta);

    public abstract void render();

    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    @Override
    public abstract void resize(int width, int height);

    @Override
    public abstract void dispose();

    @Override
    public void render(float delta) {
        render();
    }

    // Getters
    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
}