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
import de.swerik.Box2D_Tutorial.entities.Player;
import de.swerik.Box2D_Tutorial.handlers.GameStateManager;
import de.swerik.Box2D_Tutorial.handlers.MyContactListener;
import de.swerik.Box2D_Tutorial.handlers.MyInput;
import de.swerik.Box2D_Tutorial.handlers.Variables;

import static de.swerik.Box2D_Tutorial.handlers.Variables.PPM;


public class PlayState extends GameState {

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private OrthographicCamera b2dCam;

    private MyContactListener cl;

    private TiledMap map;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    private Player player;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void create() {
        //Setup Box2d-stuff
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(cl = new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawContacts(true);
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        //create res
        res.loadTexture("ninjaboy/Running.png", "runningSheet");

        //create Player
        createPlayer(bdef, shape, fdef);

        //converted cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);

        //Map stuff
        createTiles();
        createLayer(bdef, fdef, "red");
        createLayer(bdef, fdef, "green");
        createLayer(bdef, fdef, "blue");
    }

    @Override
    public void update(float delta) {
        handleInput();
        world.step(delta, 8, 3);
        player.update(delta);
    }

    @Override
    public void render() {
        debugRenderer.render(world, b2dCam.combined);

        //draw map
        tmr.setView(cam);
        tmr.render();

        //draw player
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);
    }

    @Override
    public void handleInput() {
        if (MyInput.isButtonJustPressed()) {
            if (cl.isPlayerOnGround()) {
                player.getBody().applyForceToCenter(0, 400, true);
            }
        }
    }

    @Override
    public void dispose() {
        res.dispose();
    }

    private void createPlayer(BodyDef bdef, PolygonShape shape, FixtureDef fdef) {
        //create Player
        bdef.position.set(960 / PPM, 1000 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearVelocity.set(1, 0);
        Body body = world.createBody(bdef);
        shape.setAsBox(363 * 0.15f / PPM, 458 * 0.15f / PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = Variables.BIT_PLAYER;   // category
        fdef.filter.maskBits = Variables.BIT_RED | Variables.BIT_GREEN | Variables.BIT_BLUE;    // can collide with
        body.createFixture(fdef).setUserData("player");

        //create foot sensor
        shape.setAsBox(363 * 0.15f / PPM, 5 / PPM, new Vector2(0, -458 * 0.15f / PPM), 0);
//        fdef.shape = shape;
//        fdef.filter.categoryBits = Variables.BIT_PLAYER;   // category
//        fdef.filter.maskBits = Variables.BIT_GROUND;    // can collide with
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

        //create player
        player = new Player(body);

        body.setUserData(player);
    }

    private void createTiles() {
        map = new TmxMapLoader().load("tutorial/maps/bunny.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        tileSize = (int) map.getProperties().get("tilewidth");
    }

    private void createLayer(BodyDef bdef, FixtureDef fdef, String layerName) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);

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
                fdef.filter.categoryBits = layerName.equals("red") ? Variables.BIT_RED : layerName.equals("green") ? Variables.BIT_GREEN : Variables.BIT_BLUE;   // category
                fdef.filter.maskBits = Variables.BIT_PLAYER;    // can collide with
                fdef.isSensor = false;
                world.createBody(bdef).createFixture(fdef).setUserData(layerName);
            }
        }
    }
}
