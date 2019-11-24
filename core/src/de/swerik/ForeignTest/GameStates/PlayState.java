package de.swerik.ForeignTest.GameStates;

import de.swerik.ForeignTest.Managers.GameStateManager;

public class PlayState extends GameState {

    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {
        System.out.println("Play Update");

    }

    @Override
    public void draw() {
        System.out.println("Play Draw");//
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
