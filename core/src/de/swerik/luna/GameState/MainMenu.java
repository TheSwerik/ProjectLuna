package de.swerik.luna.GameState;

import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;
import de.swerik.luna.Manager.LogManager;

public class MainMenu extends GameState {

    public MainMenu(final Luna app, final GameStateManager gsm) {
        super(app, gsm);
    }

    @Override
    public void show() {
        app.logger.log("Show \tMainMenu", LogManager.INFO);
        setBackgroundColor(0.3f, 0, 0.5f, 1);
    }

    @Override
    public void hide() {
        app.logger.log("Hide \tMainMenu", LogManager.INFO);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
        app.logger.log("Pause \tMainMenu", LogManager.INFO);
    }

    @Override
    public void resume() {
        app.logger.log("Resume \tMainMenu", LogManager.INFO);
    }

    @Override
    public void resize(int width, int height) {
        app.logger.log("Resize \tMainMenu", LogManager.INFO);
    }

    @Override
    public void dispose() {
        app.logger.log("Dispose \tMainMenu", LogManager.INFO);
    }
}
