package de.swerik.Box2D_Tutorial.states;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.swerik.Box2D_Tutorial.Game;
import de.swerik.Box2D_Tutorial.handlers.GameStateManager;

public abstract class GameState implements Screen {

    protected Game game;
    protected GameStateManager gsm;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected OrthographicCamera hudCam;

    public GameState(GameStateManager gsm) {
        this.game = gsm.game();
        this.gsm = gsm;

        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        hudCam = new OrthographicCamera();

        cam.position.set(Game.V_WIDTH / 2f, Game.V_HEIGHT / 2f, 0);
        cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
        cam.update();

        hudCam.position.set(Game.V_WIDTH / 2f, Game.V_HEIGHT / 2f, 0);
        hudCam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
        hudCam.update();

        create();
    }

    public abstract void create();

    public abstract void update(float delta);

    public abstract void render();

    public abstract void handleInput();

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

    public SpriteBatch getSb() {
        return sb;
    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public OrthographicCamera getHudCam() {
        return hudCam;
    }
}