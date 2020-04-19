package de.swerik.luna.game_state;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import de.swerik.luna.Luna;
import de.swerik.luna.manager.GameStateManager;
import de.swerik.luna.manager.Logger;

import java.util.ArrayList;
import java.util.List;

public class LoadingScreen extends GameState {

    public LoadingScreen(final Luna app, final GameStateManager gsm) {
        super(app, gsm);
        Logger.log("Cnstrct LoadingScreen", Logger.DEBUG);
    }

    @Override
    public void show() {
        Logger.log("Show \tLoadingScreen", Logger.DEBUG);

        setBackgroundColor(0.3f, 0, 0.5f, 1);

        @SuppressWarnings("rawtypes") ArrayList<AssetDescriptor> assets = new ArrayList<>();
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Running.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__000.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__001.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__002.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__003.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__004.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__005.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__006.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__007.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__008.png", Texture.class));
        assets.add(new AssetDescriptor<>("placeholder/sprites/ninjaboy/Run__009.png", Texture.class));
        load(assets);
    }

    @Override
    public void hide() {
        Logger.log("Hide \tLoadingScreen ", Logger.DEBUG);
    }

    @Override
    public void update(float delta) {
        app.assets.finishLoading();
        gsm.setState(State.MAIN_MENU);
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
        Logger.log("Pause \tLoadingScreen", Logger.DEBUG);
    }

    @Override
    public void resume() {
        Logger.log("Resume \tLoadingScreen", Logger.DEBUG);
    }

    @Override
    public void resize(int width, int height) {
        Logger.log("Resize \tLoadingScreen", Logger.DEBUG);
    }

    @Override
    public void dispose() {
        Logger.log("Dispose LoadingScreen", Logger.DEBUG);
    }

    public void load(@SuppressWarnings("rawtypes") List<AssetDescriptor> assets) {
        for (@SuppressWarnings("rawtypes") AssetDescriptor ad : assets) {
//            Logger.log("added File to Loading queue: " + ad.fileName, Logger.DEBUG);
            app.assets.load(ad);
        }
    }

    public void load(@SuppressWarnings("rawtypes") AssetDescriptor asset) {
        Logger.log("added File to Loading queue: " + asset.fileName, Logger.DEBUG);
        app.assets.load(asset);
    }
}
