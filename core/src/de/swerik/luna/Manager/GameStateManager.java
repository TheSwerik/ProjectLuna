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

    public GameStateManager(Luna game) {
        app = game;
        this.currentState = (GameState) game.getScreen();
        this.setState(PLAY);
    }

    public void setState(byte state) {
        if (currentState != null) {
            currentState.dispose();
        }
        switch (state) {
            case PLAY:
                setState(new PlayState(app));
                break;
            case MAIN_MENU:
                setState(new MainMenu(app));
                break;
            case LOADING:
                setState(new LoadingScreen(app));
                break;
        }
    }

    public void setState(GameState state) {
        currentState = state;
    }

    public void dispose() {
        currentState.dispose();
    }

    public void update(float delta) {
        currentState.update(delta);
    }

    public void render() {
        currentState.render();
    }
}