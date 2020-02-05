package de.swerik.luna.manager;

import de.swerik.luna.game_state.*;
import de.swerik.luna.Luna;

import java.util.HashMap;
import java.util.Map;

public class GameStateManager {

    private final Luna app;

    private final Map<State, GameState> states;
    private GameState currentState;

    /**
     * Initializes and manages the {@link de.swerik.luna.game_state.GameState GameStates}.
     *
     * @param app the reference to the current {@link de.swerik.luna.Luna Application}
     */
    public GameStateManager(final Luna app) {
        this.app = app;
        this.currentState = (GameState) this.app.getScreen();

        states = new HashMap<State, GameState>();
        states.put(State.PLAY, new PlayState(this.app, this));
        states.put(State.MAIN_MENU, new MainMenu(this.app, this));
        states.put(State.LOADING, new LoadingScreen(this.app, this));

        this.setState(State.LOADING);
    }

    /**
     * Sets the current {@link de.swerik.luna.game_state.GameState} from a {@link de.swerik.luna.game_state.State}.
     *
     * @param state the desired {@link de.swerik.luna.game_state.State}.
     */
    public void setState(State state) {
        setState(states.get(state));
    }

    /**
     * Sets the current {@link de.swerik.luna.game_state.GameState}.
     *
     * @param state the desired {@link de.swerik.luna.game_state.GameState}.
     */
    private void setState(GameState state) {
        app.setScreen(currentState = state);
    }

    /**
     * Calls {@code dispose} for each {@link de.swerik.luna.game_state.GameState}.
     */
    public void dispose() {
        // Dispose every State:
        for (GameState state : states.values()) {
            state.dispose();
        }
    }
}
