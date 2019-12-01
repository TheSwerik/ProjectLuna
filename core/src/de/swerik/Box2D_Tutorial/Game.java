package de.swerik.Box2D_Tutorial;

import com.badlogic.gdx.Gdx;
import de.swerik.Box2D_Tutorial.handlers.GameStateManager;
import de.swerik.Box2D_Tutorial.states.GameState;

public class Game extends com.badlogic.gdx.Game {

    public static final String TITLE = "Project Luna";
    public static final int V_WIDTH = 1920;
    public static final int V_HEIGHT = 1080;

    private GameState screen;

    private GameStateManager gsm;

    @Override
    public void create() {
        gsm = new GameStateManager(this, screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        // super.dispose() calls screen.hide()
        // NOT screen.dispose()

        // that would call screen.dispose():
        this.screen.dispose();

        Gdx.app.exit();
        System.exit(0);
    }

    @Override
    public void render() {
        gsm.update();
        gsm.render();
    }

    public GameStateManager getGsm() {
        return gsm;
    }
}
