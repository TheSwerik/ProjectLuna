package de.swerik.luna.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.swerik.luna.Luna;
import de.swerik.luna.Manager.GameStateManager;
import de.swerik.luna.Manager.LogManager;

import static de.swerik.luna.utils.Variables.PPM;

public class PlayState extends GameState {

    private OrthographicCamera cam;

    private World world;

    private Box2DDebugRenderer debugRenderer;

    //placeholder player
    private Body playerBody;
    private Body playerBody2;

    public PlayState(Luna app, GameStateManager gsm) {
        super(app, gsm);
        app.logger.log("Cnstrct Playstate", LogManager.DEBUG);
    }

    @Override
    public void show() {
        app.logger.log("Show \tPlaystate", LogManager.DEBUG);

        setBackgroundColor(0f, 0, 0f, 1);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Luna.V_WIDTH / PPM, Luna.V_HEIGHT / PPM);
        cam.position.set(Luna.V_WIDTH / 2 / PPM, Luna.V_HEIGHT / 2 / PPM, 0);
        cam.update();

        // Box2D Stuff
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        //placeholder floor
        createBox();

        //placeholder player
        createPlayer();
    }

    @Override
    public void hide() {
        app.logger.log("Hide \tPlaystate", LogManager.DEBUG);
    }

    @Override
    public void update(float delta) {
        handleInput();
        world.step(delta, 6, 2);
    }

    @Override
    public void render() {
        debugRenderer.render(world, cam.combined);
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


    private void createPlayer() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        CircleShape circle = new CircleShape();

        //create Player
        bdef.position.set(250f / PPM, 400f / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bdef);
        shape.setAsBox(25f / PPM, 90f / PPM); //he is 180cm tall
        fdef.shape = shape;
        fdef.density = 100;
        fdef.friction = 10;
        fdef.restitution = 1;
        playerBody.setFixedRotation(false);
        playerBody.createFixture(fdef).setUserData("player");

        //player 2
        bdef.position.set(550f / PPM, 400f / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        playerBody2 = world.createBody(bdef);
        shape.setAsBox(25f / PPM, 90f / PPM); //he is 180cm tall
        fdef.shape = shape;
        fdef.density = 100;
        fdef.friction = 10;
        fdef.restitution = 1;
        playerBody2.setFixedRotation(false);
        playerBody2.createFixture(fdef).setUserData("player");

        //ball
        bdef.position.set(550f / PPM, 400f / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bdef);
        circle.setRadius(50f / PPM);
        fdef.shape = circle;
        fdef.density = 100;
        fdef.friction = 10;
        fdef.restitution = 1;
        body.createFixture(fdef);
    }

    private void createBox() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //floor
        bdef.position.set(Luna.V_WIDTH / 2 / PPM, 35f / PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);
        shape.setAsBox(900f / PPM, 25f / PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("floor");

        //ceiling
        bdef.position.set(Luna.V_WIDTH / 2 / PPM, (Luna.V_HEIGHT - 35f) / PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        shape.setAsBox(900f / PPM, 25f / PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("floor");

        //left
        bdef.position.set(35f / PPM, (Luna.V_HEIGHT / 2) / PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        shape.setAsBox(25f / PPM, 500f / PPM); // 50 thicc and 1000 tall
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("floor");

        //right
        bdef.position.set((Luna.V_WIDTH - 35f) / PPM, (Luna.V_HEIGHT / 2) / PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        shape.setAsBox(25f / PPM, 500f / PPM); // 50 thicc and 1000 tall
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("floor");

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerBody.setLinearVelocity(10, playerBody.getLinearVelocity().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerBody.setLinearVelocity(-10, playerBody.getLinearVelocity().y);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerBody2.setLinearVelocity(10, playerBody2.getLinearVelocity().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerBody2.setLinearVelocity(-10, playerBody2.getLinearVelocity().y);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            playerBody2.setLinearVelocity(playerBody2.getLinearVelocity().x, 10);
        }
    }
}
