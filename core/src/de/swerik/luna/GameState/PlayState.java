package de.swerik.luna.GameState;

import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;
import de.swerik.luna.Manager.LogManager;

public class PlayState extends GameState {

    public PlayState(Luna app, GameStateManager gsm) {
        super(app, gsm);
        app.logger.log("Cnstrct Playstate", LogManager.DEBUG);
    }

    @Override
    public void show() {
        app.logger.log("Show \tPlaystate", LogManager.DEBUG);

        setBackgroundColor(0f, 0, 0f, 1);
    }

    @Override
    public void hide() {
        app.logger.log("Hide \tPlaystate", LogManager.DEBUG);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
        app.logger.log("Pause \tPlaystate", LogManager.DEBUG);
    }

    @Override
    public void resume() {
        app.logger.log("Resume \tPlaystate", LogManager.DEBUG);
    }

    @Override
    public void resize(int width, int height) {
        app.logger.log("Resize \tPlaystate", LogManager.DEBUG);
    }

    @Override
    public void dispose() {
        app.logger.log("Dispose Playstate", LogManager.DEBUG);
    }
}
