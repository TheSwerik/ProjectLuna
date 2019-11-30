package de.swerik.ForeignTest.Managers;

import de.swerik.ForeignTest.GameStates.*;

public class GameStateManager {

    private GameState currentGameState;

    public static final int MENU = 0;
    public static final int PLAY = 12321;
    public static final int HIGHSCORE = 3541321;
    public static final int GAMEOVER = 65464;

    public GameStateManager() {
        setState(MENU);

        Save.load();
    }

    public void setState(int state) {
        if (currentGameState != null) {
            currentGameState.dispose();
        }
        if (state == MENU) {
            currentGameState = new MenuState(this);
        }
        if (state == PLAY) {
            currentGameState = new PlayState(this);
        }
        if (state == HIGHSCORE) {
            currentGameState = new HighscoreState(this);
        }
        if (state == GAMEOVER) {
            currentGameState = new GameOverState(this);
        }
    }

    public void update(float delta) {
        currentGameState.update(delta);
    }

    public void draw() {
        currentGameState.draw();
    }
}
