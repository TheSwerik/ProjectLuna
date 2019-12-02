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

    public GameState loadingScreen;
    public GameState playState;
    public GameState mainMenu;

    public GameStateManager(Luna game) {
        app = game;
        this.currentState = (GameState) game.getScreen();

        playState = new PlayState(app, this);
        loadingScreen = new LoadingScreen(app, this);
        mainMenu = new MainMenu(app, this);

        this.setState(LOADING);
    }

    public void setState(byte state) {
        if (currentState != null) {
            currentState.dispose();
        }
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
        currentState = state;
    }

    public void dispose() {
        currentState.getBatch().dispose();
        currentState.getShapeRenderer().dispose();
        playState.dispose();
        loadingScreen.dispose();
        mainMenu.dispose();
    }

    public void update(float delta) {
        currentState.update(delta);
    }

    public void render() {
        currentState.render();
    }
}