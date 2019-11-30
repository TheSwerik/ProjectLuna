package de.swerik.ForeignTest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import de.swerik.ForeignTest.Managers.GameInputProcessor;
import de.swerik.ForeignTest.Managers.GameKeys;
import de.swerik.ForeignTest.Managers.GameStateManager;
import de.swerik.ForeignTest.Managers.Jukebox;

public class ForeignGame implements ApplicationListener {
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;

    public static OrthographicCamera cam;

    private GameStateManager gsm;

    @Override
    public void create() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(WIDTH, HEIGHT);
        cam.translate(WIDTH / 2f, HEIGHT / 2f);
        cam.update();

        Gdx.input.setInputProcessor(new GameInputProcessor());

        gsm = new GameStateManager();

        Jukebox.load("asteroidSounds/explode.ogg", "explode");
        Jukebox.load("asteroidSounds/extralife.ogg", "extralife");
        Jukebox.load("asteroidSounds/largesaucer.ogg", "largesaucer");
        Jukebox.load("asteroidSounds/pulsehigh.ogg", "pulsehigh");
        Jukebox.load("asteroidSounds/pulselow.ogg", "pulselow");
        Jukebox.load("asteroidSounds/saucershoot.ogg", "saucershoot");
        Jukebox.load("asteroidSounds/shoot.ogg", "shoot");
        Jukebox.load("asteroidSounds/smallsaucer.ogg", "smallsaucer");
        Jukebox.load("asteroidSounds/thruster.ogg", "thruster");
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

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.draw();

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
