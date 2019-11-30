package de.swerik.luna;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import de.swerik.luna.Screens.MainMenu;

public class Luna extends Game {

    public static final String TITLE = "Project Luna";
    public static final int V_WIDTH = 1920;
    public static final int V_HEIGHT = 1080;
    public static final int SCALE = 1;

    @Override
    public void create() {
        this.setScreen(new MainMenu());
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
}
