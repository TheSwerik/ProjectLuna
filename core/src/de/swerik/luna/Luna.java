package de.swerik.luna;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import de.swerik.luna.Manager.GameStateManager;

public class Luna extends Game {

    public static final String TITLE = "Project Luna";
    public static final int V_WIDTH = 1920;
    public static final int V_HEIGHT = 1080;

    private GameStateManager gameStateManager;
    public AssetManager assets;

    @Override
    public void create() {
        assets = new AssetManager();
        gameStateManager = new GameStateManager(this);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.gameStateManager.dispose();
        assets.dispose();
        Gdx.app.exit();
    }

    @Override
    public void render() {
        this.gameStateManager.update(Gdx.graphics.getDeltaTime());
        super.render();
    }
}
