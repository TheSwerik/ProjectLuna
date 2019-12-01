package de.swerik.Box2D_Tutorial.states;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.swerik.Box2D_Tutorial.handlers.GameStateManager;

public class PlayState extends GameState {

    private World world;
    private Box2DDebugRenderer debugRenderer;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void create() {
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        //create Platform
        BodyDef bdef = new BodyDef();
        bdef.position.set(960, 540);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(300, 20);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);

        //create falling Box
        bdef.position.set(960, 1000);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        shape.setAsBox(20, 20);
        fdef.shape = shape;
        body.createFixture(fdef);


    }

    @Override
    public void update(float delta) {
        world.step(delta, 8, 3);
    }

    @Override
    public void render() {
        debugRenderer.render(world, cam.combined);

        sb.setProjectionMatrix(cam.combined);
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
