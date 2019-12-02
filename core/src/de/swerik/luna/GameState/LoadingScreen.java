package de.swerik.luna.GameState;

import com.badlogic.gdx.graphics.Texture;
import de.swerik.luna.Luna;

public class LoadingScreen extends GameState {
    public LoadingScreen(Luna app) {
        super(app);
    }

    @Override
    public void create() {

    }

    @Override
    public void update(float delta) {
        if (app.assets.update()) {

        }
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }

    private void queueAssets() {
        app.assets.load("placeholer/sprites/ninjaboy/Running.png", Texture.class);
    }
}
