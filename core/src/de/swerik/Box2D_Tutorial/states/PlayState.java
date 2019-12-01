package de.swerik.Box2D_Tutorial.states;

import de.swerik.Box2D_Tutorial.handlers.GameStateManager;

public class PlayState extends GameState {


    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void create() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();


        sb.end();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
