package de.swerik.ForeignTest.GameStates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.swerik.ForeignTest.Entities.Player;
import de.swerik.ForeignTest.Managers.GameKeys;
import de.swerik.ForeignTest.Managers.GameStateManager;

public class PlayState extends GameState {

    private ShapeRenderer sr;

    private Player player;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        sr = new ShapeRenderer();
        player = new Player();
    }

    @Override
    public void update(float delta) {
        handleInput();

        player.update(delta);
    }

    @Override
    public void draw() {
        player.draw(sr);

    }

    @Override
    public void handleInput() {
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
    }

    @Override
    public void dispose() {

    }
}
