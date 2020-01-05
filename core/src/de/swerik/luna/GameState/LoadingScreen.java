package de.swerik.luna.GameState;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;
import de.swerik.luna.Manager.LogManager;

import java.util.ArrayList;
import java.util.List;

public class LoadingScreen extends GameState {

    public LoadingScreen(final Luna app, final GameStateManager gsm) {
        super(app, gsm);
        app.logger.log("Cnstrct LoadingScreen", LogManager.DEBUG);
    }

    @Override
    public void show() {
        app.logger.log("Show \tLoadingScreen", LogManager.DEBUG);

        setBackgroundColor(0.3f, 0, 0.5f, 1);

        ArrayList<AssetDescriptor> assets = new ArrayList<>();
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Running.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__000.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__001.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__002.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__003.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__004.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__005.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__006.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__007.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__008.png", Texture.class));
        assets.add(new AssetDescriptor<Texture>("placeholder/sprites/ninjaboy/Run__009.png", Texture.class));
        load(assets);
    }

    @Override
    public void hide() {
        app.logger.log("Hide \tLoadingScreen ", LogManager.DEBUG);
    }

    @Override
    public void update(float delta) {
        app.assets.finishLoading();
        gsm.setState(GameStateManager.MAIN_MENU);
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
        app.logger.log("Pause \tLoadingScreen", LogManager.DEBUG);
    }

    @Override
    public void resume() {
        app.logger.log("Resume \tLoadingScreen", LogManager.DEBUG);
    }

    @Override
    public void resize(int width, int height) {
        app.logger.log("Resize \tLoadingScreen", LogManager.DEBUG);
    }

    @Override
    public void dispose() {
        app.logger.log("Dispose LoadingScreen", LogManager.DEBUG);

        batch.dispose();
        shapeRenderer.dispose();
    }

    public void load(List<AssetDescriptor> assets) {
        for (AssetDescriptor ad : assets) {
            app.logger.log("added File to Loading queue: " + ad.fileName, LogManager.DEBUG);
            app.assets.load(ad);
        }
    }

    public void load(AssetDescriptor asset) {
        app.logger.log("added File to Loading queue: " + asset.fileName, LogManager.DEBUG);
        app.assets.load(asset);
    }
}
