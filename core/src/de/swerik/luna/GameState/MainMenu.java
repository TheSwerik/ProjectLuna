package de.swerik.luna.GameState;

import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;

public class MainMenu extends GameState {

    public MainMenu(Luna app, GameStateManager gsm) {
        super(app, gsm);
    }

    @Override
    public void create() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {
        System.out.println("MENU");

    }

    @Override
    public void dispose() {

    }
}
