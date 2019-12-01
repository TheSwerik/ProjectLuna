package de.swerik.Box2D_Tutorial.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.swerik.Box2D_Tutorial.Game;
import de.swerik.Box2D_Tutorial.handlers.GameStateManager;

import static de.swerik.Box2D_Tutorial.handlers.Variables.PPM;


public class PlayState extends GameState {

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private OrthographicCamera b2dCam;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void create() {
        world = new World(new Vector2(0, -9.81f), true);
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
        body.createFixture(fdef);

        //create falling Box
        bdef.position.set(960 / PPM, 1000 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        shape.setAsBox(20 / PPM, 20 / PPM);
        fdef.shape = shape;
        body.createFixture(fdef);

        //converted cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);

        //converted cam
    }

    @Override
    public void update(float delta) {
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

    }

    @Override
    public void dispose() {

    }
}
