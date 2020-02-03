package de.swerik.luna;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import de.swerik.luna.manager.GameStateManager;
import de.swerik.luna.manager.Input;
import de.swerik.luna.manager.Logger;

public class Luna extends Game {

    private static final int LOG_LEVEL = Logger.INFO;
    public static final boolean DEBUG = true;

    public static final String TITLE = "Project Luna";
    public static final float V_WIDTH = 1920;
    public static final float V_HEIGHT = 1080;

    public Logger logger;
    public AssetManager assets;
    private GameStateManager gameStateManager;

    @Override
    public void create() {
        Logger.initLogManager(LOG_LEVEL, true, DEBUG);
        assets = new AssetManager();
        gameStateManager = new GameStateManager(this);
        Gdx.input.setInputProcessor(new Input());
    }

    @Override
    public void dispose() {
        super.dispose();
        this.gameStateManager.dispose();
        assets.dispose();

        Logger.newLine();
        Logger.log("Disposed Successfully", Logger.DEBUG);

        Gdx.app.exit();
    }
}
