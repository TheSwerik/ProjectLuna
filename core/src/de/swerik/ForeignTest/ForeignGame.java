package de.swerik.ForeignTest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class ForeignGame extends Game {
    public static final int WIDTH = 1280;
    public static final int HEIGHT= 720;

    @Override
    public void create() {

    }

    @Override
    public void dispose() {
        Gdx.app.exit();
        System.exit(0);
    }
}
