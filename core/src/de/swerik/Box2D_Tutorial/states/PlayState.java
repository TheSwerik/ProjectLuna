package de.swerik.Box2D_Tutorial.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

    private TiledMap map;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void create() {
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(cl = new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawContacts(true);

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        //create Player
        bdef.position.set(960 / PPM, 1000 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bdef);
        shape.setAsBox(20 / PPM, 20 / PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = Variables.BIT_PLAYER;   // category
        fdef.filter.maskBits = Variables.BIT_RED;    // can collide with
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

        map = new TmxMapLoader().load("tutorial/maps/bunny.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("red");
        tileSize = layer.getTileWidth();

        //go through all cells
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                Cell cell = layer.getCell(col, row);

                //check if cell exists
                if (cell == null || cell.getTile() == null) {
                    continue;
                }

                //create body + fixture
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set(
                        (col + 0.5f) * tileSize / PPM,
                        (row + 0.5f) * tileSize / PPM
                );
                ChainShape cShape = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(
                        -tileSize / 2 / PPM,
                        -tileSize / 2 / PPM
                );
                v[1] = new Vector2(
                        -tileSize / 2 / PPM,
                        tileSize / 2 / PPM
                );
                v[2] = new Vector2(
                        tileSize / 2 / PPM,
                        tileSize / 2 / PPM
                );
                cShape.createChain(v);
                fdef.friction = 0;
                fdef.shape = cShape;
                fdef.filter.categoryBits = Variables.BIT_RED;   // category
                fdef.filter.maskBits = Variables.BIT_PLAYER;    // can collide with
                fdef.isSensor = false;
                world.createBody(bdef).createFixture(fdef).setUserData("Tile");
            }
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
        world.step(delta, 8, 3);
    }

    @Override
    public void render() {
        debugRenderer.render(world, b2dCam.combined);

        //draw map
        tmr.setView(cam);
        tmr.render();

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
