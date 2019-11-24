package de.swerik.ForeignTest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import de.swerik.ForeignTest.Managers.GameInputProcessor;
import de.swerik.ForeignTest.Managers.GameKeys;

public class ForeignGame implements ApplicationListener {
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;

    public static OrthographicCamera cam;

    @Override
    public void create() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(WIDTH, HEIGHT);
        cam.translate(WIDTH / 2f, HEIGHT / 2f);
        cam.update();

        Gdx.input.setInputProcessor(new GameInputProcessor());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Better / New Way:
        /*
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            System.out.println("SPACE");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            System.out.println("W");
        }
        */

        //Worse / Old Way:
        /*
        if (GameKeys.isDown(GameKeys.SPACE)) {
            System.out.println("SPACE");
        }
        if (GameKeys.isPressed(GameKeys.ENTER)) {
            System.out.println("ENTER");
        }
        */

        GameKeys.update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        Gdx.app.exit();
        System.exit(0);
    }
}
