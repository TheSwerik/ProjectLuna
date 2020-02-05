package de.swerik.luna.game_state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.swerik.luna.Luna;
import de.swerik.luna.manager.GameStateManager;

public abstract class GameState implements Screen {

    protected final Luna app;
    protected final GameStateManager gsm;

    protected final SpriteBatch batch;
    protected final ShapeRenderer shapeRenderer;

    private final float[] backgroundColor;

    public GameState(final Luna app, final GameStateManager gsm) {
        this.app = app;
        this.gsm = gsm;

        batch = app.batch;
        shapeRenderer = app.shapeBatch;
        backgroundColor = new float[]{0, 0, 0, 1};
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
        //Update the state
        update(delta);

        // render the State
        Gdx.gl30.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], backgroundColor[3]);
        Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT);
        render();
    }

    // Setters

    protected void setBackgroundColor(float red, @SuppressWarnings("SameParameterValue") float green, float blue, @SuppressWarnings("SameParameterValue") float alpha) {
        backgroundColor[0] = red;
        backgroundColor[1] = green;
        backgroundColor[2] = blue;
        backgroundColor[3] = alpha;
    }
}
