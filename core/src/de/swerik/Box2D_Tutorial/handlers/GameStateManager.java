package de.swerik.Box2D_Tutorial.handlers;

import de.swerik.Box2D_Tutorial.Game;
import de.swerik.Box2D_Tutorial.states.GameState;
import de.swerik.Box2D_Tutorial.states.MainMenu;
import de.swerik.Box2D_Tutorial.states.PlayState;
import de.swerik.Box2D_Tutorial.states.State;

import java.util.Stack;

public class GameStateManager {

    private Game game;
    private Stack<GameState> gameStates;

    private GameState currentState;
    private State currState;

    public GameStateManager(Game game, GameState currentState) {
        this.game = game;
        this.currentState = currentState;
    }

    public void setCurrentScreen(int screen) {
        currState = State.values()[screen];
        this.setState();
    }

    public void setCurrentScreen(State screen) {
        currState = screen;
        this.setState();
    }

    private void setState() {
//        screen.dispose();
        switch (currState) {
            case GAME:
                currentState = new PlayState(game, this);
                break;
            case MENU:
            default:
                currentState = new MainMenu(game, this);
        }
    }
}
