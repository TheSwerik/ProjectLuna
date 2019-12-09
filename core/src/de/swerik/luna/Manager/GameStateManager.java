package de.swerik.luna.Manager;

import de.swerik.luna.GameState.GameState;
import de.swerik.luna.GameState.LoadingScreen;
import de.swerik.luna.GameState.MainMenu;
import de.swerik.luna.GameState.PlayState;
import de.swerik.luna.Luna;

public class GameStateManager {

    public static final byte PLAY = 1;
    public static final byte MAIN_MENU = 2;
    public static final byte LOADING = 3;

    private GameState currentState;
    private Luna app;

    public LoadingScreen loadingScreen;
    public PlayState playState;
    public MainMenu mainMenu;

    public GameStateManager(Luna app) {
        this.app = app;
        this.currentState = (GameState) this.app.getScreen();

        playState = new PlayState(this.app, this);
        loadingScreen = new LoadingScreen(this.app, this);
        mainMenu = new MainMenu(this.app, this);

        this.setState(LOADING);
    }

    public void setState(byte state) {
        switch (state) {
            case PLAY:
                setState(playState);
                break;
            case MAIN_MENU:
                setState(mainMenu);
                break;
            case LOADING:
                setState(loadingScreen);
                break;
        }
    }

    public void setState(GameState state) {
        app.setScreen(currentState = state);
    }

    public void dispose() {
        // Dispose every State:
        playState.dispose();
        loadingScreen.dispose();
        mainMenu.dispose();
    }
}
