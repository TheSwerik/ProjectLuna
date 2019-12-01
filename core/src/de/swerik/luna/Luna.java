package de.swerik.luna;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import de.swerik.luna.Screens.LunaScreen;
import de.swerik.luna.Screens.MainMenu;
import de.swerik.luna.Screens.Screen;

public class Luna extends Game {

    public static final String TITLE = "Project Luna";
    public static final int V_WIDTH = 1920;
    public static final int V_HEIGHT = 1080;

    private Screen currentScreen = Screen.MENU;
    //    private Screen currentScreen = Screen.GAME;
    private LunaScreen screen;

    //TODO look into Stage Scene2d

    @Override
    public void create() {
        this.switchScreen();
    }

    public void setCurrentScreen(int screen) {
        currentScreen = Screen.values()[screen];
        this.switchScreen();
    }

    public void setCurrentScreen(Screen screen) {
        currentScreen = screen;
        this.switchScreen();
    }

    private void switchScreen() {
//        screen.dispose();
        switch (currentScreen) {
            case GAME:
//                screen=new Game();
                break;
            case MENU:
            default:
                screen = new MainMenu();
        }
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
        this.screen.update(Gdx.graphics.getDeltaTime());
        Gdx.gl30.glClearColor(0, 0, 0, 1);
        Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT);
        this.screen.render();
    }
}
