package de.swerik.luna.game_state;

import de.swerik.luna.Luna;
import de.swerik.luna.manager.GameStateManager;
import de.swerik.luna.manager.Logger;

public class MainMenu extends GameState {

    public MainMenu(final Luna app, final GameStateManager gsm) {
        super(app, gsm);
        Logger.log("Cnstrct MainMenu", Logger.DEBUG);
    }

    @Override
    public void show() {
        Logger.log("Show \tMainMenu", Logger.DEBUG);
        setBackgroundColor(0.3f, 0, 0.5f, 1);
    }

    @Override
    public void hide() {
        Logger.log("Hide \tMainMenu", Logger.DEBUG);
    }

    @Override
    public void update(float delta) {
        gsm.setState(GameStateManager.State.PLAY);
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
        Logger.log("Pause \tMainMenu", Logger.DEBUG);
    }

    @Override
    public void resume() {
        Logger.log("Resume \tMainMenu", Logger.DEBUG);
    }

    @Override
    public void resize(int width, int height) {
        Logger.log("Resize \tMainMenu", Logger.DEBUG);
    }

    @Override
    public void dispose() {
        Logger.log("Dispose MainMenu", Logger.DEBUG);

        batch.dispose();
        shapeRenderer.dispose();
    }
}
