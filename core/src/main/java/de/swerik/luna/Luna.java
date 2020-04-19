package de.swerik.luna;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.swerik.luna.manager.GameStateManager;
import de.swerik.luna.manager.Input;
import de.swerik.luna.manager.Logger;

public class Luna extends Game {

    public static final String TITLE = "Project Luna";
    public static final double VERSION = 0.1;
    public static final float V_WIDTH = 1920;
    public static final float V_HEIGHT = 1080;

    private static final int LOG_LEVEL = Logger.INFO;
    public static final boolean DEBUG = true;

    private GameStateManager gameStateManager;
    public AssetManager assets;

    public SpriteBatch batch;
    public ShapeRenderer shapeBatch;

    @Override
    public void create() {
        Logger.initLogManager(LOG_LEVEL, true, DEBUG);
        assets = new AssetManager();
        assets.getLogger().setLevel(LOG_LEVEL);
        batch = new SpriteBatch();
        shapeBatch = new ShapeRenderer();
        gameStateManager = new GameStateManager(this);
        Gdx.input.setInputProcessor(new Input());
    }

    @Override
    public void dispose() {
        super.dispose();
        this.gameStateManager.dispose();
        this.assets.dispose();
        this.batch.dispose();
        this.shapeBatch.dispose();

        Logger.newLine();
        Logger.log("Disposed Successfully", Logger.DEBUG);

        Gdx.app.exit();
    }
}
