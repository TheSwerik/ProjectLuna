package de.swerik.MorpheusTest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {
    public static final int WIDTH = 1280;
    public static final int HEIGHT= 720;

    @Override
    public void create() {
//        setScreen(new MenuScreen(this));
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        Gdx.app.exit();
        System.exit(0);
    }
}
