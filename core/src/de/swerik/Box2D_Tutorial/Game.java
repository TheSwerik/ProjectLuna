package de.swerik.Box2D_Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import de.swerik.Box2D_Tutorial.handlers.GameStateManager;
import de.swerik.Box2D_Tutorial.states.GameState;

public class Game extends com.badlogic.gdx.Game {

    public static final String TITLE = "Project Luna";
    public static final int V_WIDTH = 1920;
    public static final int V_HEIGHT = 1080;

    public static final float STEP = 1 / 60f;
    private float accum;

    private GameState screen;

    private GameStateManager gsm;

    @Override
    public void create() {
        gsm = new GameStateManager(this, screen);
    }

    @Override
    public void dispose() {
        gsm.dispose();
        Gdx.app.exit();
        System.exit(0);
    }

    @Override
    public void render() {
        accum += Gdx.graphics.getDeltaTime();
        while (accum >= STEP) {
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            accum -= STEP;
            gsm.update(STEP);
            gsm.render();
        }
    }

    public GameStateManager getGsm() {
        return gsm;
    }
}
