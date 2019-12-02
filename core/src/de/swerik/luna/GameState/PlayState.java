package de.swerik.luna.GameState;

import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;

public class PlayState extends GameState {

    public PlayState(Luna app, GameStateManager gsm) {
        super(app, gsm);
    }

    @Override
    public void create() {
        System.out.println("test");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
