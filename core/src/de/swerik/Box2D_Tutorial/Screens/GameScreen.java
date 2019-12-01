package de.swerik.Box2D_Tutorial.Screens;

import com.badlogic.gdx.Screen;

public abstract class GameScreen implements Screen {

    public GameScreen() {
        create();
    }

    public abstract void create();

    public abstract void update(float delta);

    public abstract void render();

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
        System.out.println("aSs");
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {
        render();
    }
}