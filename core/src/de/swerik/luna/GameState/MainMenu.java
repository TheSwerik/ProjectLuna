package de.swerik.luna.GameState;

import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;

public class MainMenu extends GameState {

    public MainMenu(Luna app, GameStateManager gsm) {
        super(app, gsm);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {
        System.out.println("MENU");

    }

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
    public void dispose() {

    }
}
