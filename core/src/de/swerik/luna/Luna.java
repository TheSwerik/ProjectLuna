package de.swerik.luna;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Luna extends Game {

    @Override
    public void create() {

    }

    @Override
    public void dispose() {
        super.dispose();
        // super.dispose() calls screen.hide()
        // NOT screen.dispose()

        // that would call screen.dispose():
        screen.dispose();

        Gdx.app.exit();
        System.exit(0);
    }
}
