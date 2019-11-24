package de.swerik.ForeignTest.Managers;

import de.swerik.ForeignTest.GameStates.GameState;
import de.swerik.ForeignTest.GameStates.PlayState;

public class GameStateManager {

    private GameState currentGameState;

    public static final int MENU = 0;
    public static final int PLAY = 12321;

    public GameStateManager() {
        setState(PLAY);
    }

    public void setState(int state) {
        if (currentGameState != null) {
            currentGameState.dispose();
        }
        if (state == MENU) {

        }
        if (state == PLAY) {
            currentGameState = new PlayState(this);
        }
    }

    public void update(float delta) {
        currentGameState.update(delta);
    }

    public void draw() {
        currentGameState.draw();
    }
}
