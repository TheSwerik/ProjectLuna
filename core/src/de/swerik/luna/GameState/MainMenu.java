package de.swerik.luna.GameState;

import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;

public class MainMenu extends GameState {

    public MainMenu(final Luna app, final GameStateManager gsm) {
        super(app, gsm);
    }

    @Override
    public void show() {
        setBackgroundColor(0.3f, 0, 0.5f, 1);
    }

    @Override
    public void hide() {
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
    }
}
