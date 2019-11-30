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

    @Override
    public void create() {
        this.switchScreen();
    }

    public void setScreen(int screen) {
        currentScreen = Screen.values()[screen];
        this.switchScreen();
    }

    public void setScreen(Screen screen) {
        currentScreen = screen;
        this.switchScreen();
    }

    private void switchScreen() {
//        screen.dispose();
        switch (currentScreen){
            case MENU:
                this.setScreen(new MainMenu());
                break;
            case GAME:
//                this.setScreen(new Game());
                break;
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
        Gdx.gl30.glClearColor(0,0,0,1);
        Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT);
        this.screen.update(Gdx.graphics.getDeltaTime());
        this.screen.render();
    }
}
