package de.swerik.Box2D_Tutorial.handlers;

import de.swerik.Box2D_Tutorial.Game;
import de.swerik.Box2D_Tutorial.states.GameState;
import de.swerik.Box2D_Tutorial.states.MainMenu;
import de.swerik.Box2D_Tutorial.states.PlayState;

import java.util.Stack;

public class GameStateManager {

    private Game game;
    private Stack<GameState> gameStates;

    private GameState currentState;

    public static final int PLAY = 1;
    public static final int MENU = 2;

    public GameStateManager(Game game, GameState currentState) {
        this.game = game;
        this.currentState = currentState;
        gameStates = new Stack<>();
        pushState(PLAY);
    }

    public void update(float delta) {
        gameStates.peek().update(delta);
    }

    public void render() {
        gameStates.peek().render();
    }

    public void dispose() {
        gameStates.peek().dispose();
    }

    private GameState getState(int state) {
        switch (state) {
            case PLAY:
                return new PlayState(this);
            case MENU:
            default:
                return new MainMenu(this);
        }
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }

    public Game game() {
        return game;
    }
}
