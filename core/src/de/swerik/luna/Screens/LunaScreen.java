package de.swerik.luna.Screens;

import com.badlogic.gdx.Screen;

public abstract class LunaScreen implements Screen {

    public LunaScreen() {
        create();
    }

    public abstract void create();

    @Override
    public abstract void render(float delta);

    @Override
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
}