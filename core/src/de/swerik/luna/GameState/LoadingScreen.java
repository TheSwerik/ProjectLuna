package de.swerik.luna.GameState;

import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;
import de.swerik.luna.Manager.LogManager;

public class LoadingScreen extends GameState {

    public LoadingScreen(final Luna app, final GameStateManager gsm) {
        super(app, gsm);
    }

    @Override
    public void show() {
        app.logger.log("Show \tLoadingScreen", LogManager.DEBUG);

        setBackgroundColor(0.3f, 0, 0.5f, 1);
    }

    @Override
    public void hide() {
        app.logger.log("Hide \tLoadingScreen", LogManager.DEBUG);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
        app.logger.log("Pause \tLoadingScreen", LogManager.DEBUG);
    }

    @Override
    public void resume() {
        app.logger.log("Resume \tLoadingScreen", LogManager.DEBUG);
    }

    @Override
    public void resize(int width, int height) {
        app.logger.log("Resize \tLoadingScreen", LogManager.DEBUG);
    }

    @Override
    public void dispose() {
        app.logger.log("Dispose LoadingScreen", LogManager.DEBUG);
    }
}
