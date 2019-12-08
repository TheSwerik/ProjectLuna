package de.swerik.luna;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import de.swerik.luna.Manager.GameStateManager;
import de.swerik.luna.Manager.LogManager;

public class Luna extends Game {

    public static final String TITLE = "Project Luna";
    public static final int V_WIDTH = 1920;
    public static final int V_HEIGHT = 1080;

    private static final int LOG_LEVEL = LogManager.INFO;

    public LogManager logger;
    public AssetManager assets;
    private GameStateManager gameStateManager;

    @Override
    public void create() {
        logger = new LogManager(LOG_LEVEL, true, true);
        assets = new AssetManager();
        gameStateManager = new GameStateManager(this);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.gameStateManager.dispose();
        assets.dispose();
        logger.log("Disposed Successfully", LogManager.ERROR);
        Gdx.app.exit();
    }
}
