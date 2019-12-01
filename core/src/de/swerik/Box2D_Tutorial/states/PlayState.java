package de.swerik.Box2D_Tutorial.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.swerik.Box2D_Tutorial.Game;
import de.swerik.Box2D_Tutorial.handlers.GameStateManager;
import de.swerik.Box2D_Tutorial.handlers.MyContactListener;
import de.swerik.Box2D_Tutorial.handlers.MyInput;
import de.swerik.Box2D_Tutorial.handlers.Variables;

import static de.swerik.Box2D_Tutorial.handlers.Variables.PPM;


public class PlayState extends GameState {

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private OrthographicCamera b2dCam;

    private Body playerBody;

    private MyContactListener cl;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void create() {
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(cl = new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawContacts(true);

        //create Platform
        BodyDef bdef = new BodyDef();
        bdef.position.set(960 / PPM, 540 / PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(300 / PPM, 20 / PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = Variables.BIT_GROUND;
        fdef.filter.maskBits = Variables.BIT_PLAYER;
        body.createFixture(fdef).setUserData("ground");

        //create Player
        bdef.position.set(960 / PPM, 1000 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bdef);
        shape.setAsBox(20 / PPM, 20 / PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = Variables.BIT_PLAYER;   // category
        fdef.filter.maskBits = Variables.BIT_GROUND;    // can collide with
        playerBody.createFixture(fdef).setUserData("player");

        //create foot sensor
        shape.setAsBox(8 / PPM, 8 / PPM, new Vector2(0, -20 / PPM), 0);
//        fdef.shape = shape;
//        fdef.filter.categoryBits = Variables.BIT_PLAYER;   // category
//        fdef.filter.maskBits = Variables.BIT_GROUND;    // can collide with
        fdef.isSensor = true;
        playerBody.createFixture(fdef).setUserData("foot");

        //converted cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
    }

    @Override
    public void update(float delta) {
        handleInput();
        world.step(delta, 8, 3);
    }

    @Override
    public void render() {
        debugRenderer.render(world, b2dCam.combined);

        sb.setProjectionMatrix(b2dCam.combined);
        sb.begin();


        sb.end();
    }

    @Override
    public void handleInput() {
        if (MyInput.isButtonJustPressed()) {
            if (cl.isPlayerOnGround()) {
                playerBody.applyForceToCenter(0, 400, true);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
