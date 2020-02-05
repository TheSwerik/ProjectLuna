package de.swerik.luna.manager;

import de.swerik.luna.game_state.*;
import de.swerik.luna.Luna;

import java.util.HashMap;
import java.util.Map;

public class GameStateManager {

    private GameState currentState;
    private final Luna app;

    private Map<State, GameState> states;

    public GameStateManager(final Luna app) {
        this.app = app;
        this.currentState = (GameState) this.app.getScreen();

        states = new HashMap<State, GameState>();
        states.put(State.PLAY, new PlayState(this.app, this));
        states.put(State.MAIN_MENU, new MainMenu(this.app, this));
        states.put(State.LOADING, new LoadingScreen(this.app, this));

        this.setState(State.LOADING);
    }

    public void setState(State state) {
        setState(states.get(state));
    }

    private void setState(GameState state) {
        app.setScreen(currentState = state);
    }

    public void dispose() {
        // Dispose every State:
        for (GameState state : states.values()) {
            state.dispose();
        }
    }
}
