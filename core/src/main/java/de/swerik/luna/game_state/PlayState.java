package de.swerik.luna.game_state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import de.swerik.luna.Luna;
import de.swerik.luna.manager.EntityManager;
import de.swerik.luna.ecs.LunaEngine;
import de.swerik.luna.manager.GameStateManager;
import de.swerik.luna.manager.Logger;

import static de.swerik.luna.utils.Variables.PPM;

public class PlayState extends GameState {

    private OrthographicCamera cam;

    private World world;

    private Box2DDebugRenderer debugRenderer;

    private BitmapFont font;

    // Entity Manager
    private EntityManager entityManager;

    public PlayState(Luna app, GameStateManager gsm) {
        super(app, gsm);
        Logger.log("Cnstrct Playstate", Logger.DEBUG);
    }

    @Override
    public void show() {
        Logger.log("Show \tPlaystate", Logger.DEBUG);

        setBackgroundColor(0f, 0, 0f, 1);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Luna.V_WIDTH, Luna.V_HEIGHT);
        cam.position.set(Luna.V_WIDTH / 2, Luna.V_HEIGHT / 2, 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        // Box2D Stuff
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Entity Manager
        entityManager = new EntityManager(new LunaEngine(), batch, world);

        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    @Override
    public void hide() {
        Logger.log("Hide \tPlaystate", Logger.DEBUG);
    }

    @Override
    public void update(float delta) {
        world.step(1f / 60f, 6, 2);
//        entityManager.update(1f/delta);
        entityManager.update(delta);
    }

    @Override
    public void render() {
        debugRenderer.render(world, cam.combined.cpy().scl(PPM));

        batch.begin();
        entityManager.render();
        font.draw(batch,  Gdx.graphics.getFramesPerSecond() + "", 10, Luna.V_HEIGHT - 10);
        batch.end();
    }

    @Override
    public void pause() {
        Logger.log("Pause \tPlaystate", Logger.DEBUG);
    }

    @Override
    public void resume() {
        Logger.log("Resume \tPlaystate", Logger.DEBUG);
    }

    @Override
    public void resize(int width, int height) {
        Logger.log("Resize \tPlaystate", Logger.DEBUG);
    }

    @Override
    public void dispose() {
        Logger.log("Dispose Playstate", Logger.DEBUG);
    }
}
