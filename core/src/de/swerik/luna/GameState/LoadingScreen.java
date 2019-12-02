package de.swerik.luna.GameState;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;

public class LoadingScreen extends GameState {

    private float progress;
    private float[] progressBar;

    public LoadingScreen(Luna app, GameStateManager gsm) {
        super(app, gsm);
        this.queueAssets();
    }

    @Override
    public void show() {
        progress = 0f;
        progressBar = new float[]{
                0,
                0,
                Luna.V_WIDTH,
                30
        };

    }

    @Override
    public void hide() {
    }

    @Override
    public void update(float delta) {
//        progress = app.assets.getProgress();
        progress = MathUtils.lerp(progress, app.assets.getProgress(), 0.1f);

        if (app.assets.update() && MathUtils.isEqual(progress, app.assets.getProgress(), 0.001f)) {
            gsm.setState(GameStateManager.MAIN_MENU);
        }

    }

    @Override
    public void render() {
        System.out.println("loading...");
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(.25f, .25f, .25f, 1);
        shapeRenderer.rect(progressBar[0], progressBar[1], progressBar[2], progressBar[3]);
        shapeRenderer.setColor(.25f, .25f, 1f, 1);
        shapeRenderer.rect(progressBar[0], progressBar[1], progressBar[2] * progress, progressBar[3]);
        shapeRenderer.end();
        batch.begin();
        batch.end();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
    }

    private void queueAssets() {
        app.assets.load("placeholder/sprites/ninjaboy/Running.png", Texture.class);
    }
}
