package de.swerik.luna.game_state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.swerik.luna.Luna;
import de.swerik.luna.ecs.EntityManager;
import de.swerik.luna.manager.GameStateManager;
import de.swerik.luna.manager.LogManager;
import de.swerik.luna.utils.BodyGenerator;
import de.swerik.luna.utils.Variables;

import static de.swerik.luna.utils.Variables.METERS_TO_PIXELS;
import static de.swerik.luna.utils.Variables.PIXELS_TO_METERS;

public class PlayState extends GameState {

    private OrthographicCamera cam;

    private World world;

    private Box2DDebugRenderer debugRenderer;

    private long lastNanoTime = 0;
    private long lastFPS = 0;

    // Entity Manager
    private EntityManager entityManager;

    public PlayState(Luna app, GameStateManager gsm) {
        super(app, gsm);
        app.logger.log("Cnstrct Playstate", LogManager.DEBUG);
    }

    @Override
    public void show() {
        app.logger.log("Show \tPlaystate", LogManager.DEBUG);

        setBackgroundColor(0f, 0, 0f, 1);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Luna.V_WIDTH * PIXELS_TO_METERS, Luna.V_HEIGHT * PIXELS_TO_METERS);
        cam.position.set(Luna.V_WIDTH / 2 * PIXELS_TO_METERS, Luna.V_HEIGHT / 2 * PIXELS_TO_METERS, 0);
        cam.update();

        // Box2D Stuff
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        //placeholder floor
//        createBox();

        // Entity Manager
        entityManager = new EntityManager( new Engine(), batch, world);
    }

    @Override
    public void hide() {
        app.logger.log("Hide \tPlaystate", LogManager.DEBUG);
    }

    @Override
    public void update(float delta) {
        world.step(delta, 6, 2);
        entityManager.update(delta);
    }

    @Override
    public void render() {
        debugRenderer.render(world, cam.combined);


        //Display FPS:
//        if (lastFPS != Gdx.graphics.getFramesPerSecond()) {
//            lastFPS = Gdx.graphics.getFramesPerSecond();
//            app.logger.log("MehGDX:\t" + lastFPS);
//        }
//        if (TimeUtils.timeSinceNanos(lastNanoTime) >= 333333333) {
//            lastNanoTime = TimeUtils.nanoTime();
//            app.logger.log("PRICISE:\t" + Math.round(1. / Gdx.graphics.getRawDeltaTime()));
//        }
    }

    @Override
    public void pause() {
        app.logger.log("Pause \tPlaystate", LogManager.DEBUG);
    }

    @Override
    public void resume() {
        app.logger.log("Resume \tPlaystate", LogManager.DEBUG);
    }

    @Override
    public void resize(int width, int height) {
        app.logger.log("Resize \tPlaystate", LogManager.DEBUG);
    }

    @Override
    public void dispose() {
        app.logger.log("Dispose Playstate", LogManager.DEBUG);

        batch.dispose();
        shapeRenderer.dispose();
    }

    private void createBox() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //floor
        bdef.position.set(Luna.V_WIDTH / 2 * PIXELS_TO_METERS, 35f * PIXELS_TO_METERS);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);
        shape.setAsBox(900f * PIXELS_TO_METERS, 25f * PIXELS_TO_METERS);
        fdef.shape = shape;
        fdef.filter.categoryBits = Variables.LEVEL_BITS;
//        fdef.filter.maskBits = Variables.FRIENDLY_LEFT_WALL_SENSOR;
        body.createFixture(fdef).setUserData("wall");

        //ceiling
        bdef.position.set(Luna.V_WIDTH / 2 * PIXELS_TO_METERS, (Luna.V_HEIGHT - 35f) * PIXELS_TO_METERS);
        body = world.createBody(bdef);
        body.createFixture(fdef).setUserData("wall");

        //left
        bdef.position.set(35f * PIXELS_TO_METERS, (Luna.V_HEIGHT / 2) * PIXELS_TO_METERS);
        body = world.createBody(bdef);
        shape.setAsBox(25f * PIXELS_TO_METERS, 500f * PIXELS_TO_METERS); // 50 thicc and 1000 tall
        body.createFixture(fdef).setUserData("wall");

        //right
        bdef.position.set((Luna.V_WIDTH - 35f) * PIXELS_TO_METERS, (Luna.V_HEIGHT / 2) * PIXELS_TO_METERS);
        body = world.createBody(bdef);
        body.createFixture(fdef).setUserData("wall");

    }
}
