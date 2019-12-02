package de.swerik.luna.GameState;

import com.badlogic.gdx.Screen;
import de.swerik.luna.Luna;

public abstract class GameState implements Screen {

    protected Luna app;

    public GameState(Luna app) {
        this.app = app;
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