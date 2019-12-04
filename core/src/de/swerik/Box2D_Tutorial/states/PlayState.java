package de.swerik.Box2D_Tutorial.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import de.swerik.Box2D_Tutorial.Game;
import de.swerik.Box2D_Tutorial.entities.Crystal;
import de.swerik.Box2D_Tutorial.entities.HUD;
import de.swerik.Box2D_Tutorial.entities.Player;
import de.swerik.Box2D_Tutorial.handlers.GameStateManager;
import de.swerik.Box2D_Tutorial.handlers.MyContactListener;
import de.swerik.Box2D_Tutorial.handlers.MyInput;
import de.swerik.Box2D_Tutorial.handlers.Variables;

import static de.swerik.Box2D_Tutorial.handlers.Variables.PPM;


public class PlayState extends GameState {

    public static final boolean DEBUG = true;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private OrthographicCamera b2dCam;

    private MyContactListener cl;

    private TiledMap map;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    private Player player;

    private Array<Crystal> crystals;

    private HUD hud;

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

        //create res
        res.loadTexture("ninjaboy/Running.png", "runningSheet");
        res.loadTexture("../tilesets/winter/Object/Crystal.png", "crystal");
        res.loadTexture("../../tutorial/res/images/hud.png", "hud");

        //create Player
        createPlayer();

        //converted cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);

        //Map stuff
        createTiles();
        createLayer("red");
        createLayer("green");
        createLayer("blue");

        //create Crystals
        createCrystals();

        hud = new HUD(player);
    }

    @Override
    public void update(float delta) {
        handleInput();
        world.step(Game.STEP, 6, 2);

        //remove crystals
        Array<Body> bodies = cl.getRemoveList();
        for (Body b : bodies) {
            crystals.removeValue((Crystal) b.getUserData(), true);
            world.destroyBody(b);
            player.collectCrystal();
        }
        bodies.clear();

        player.getBody().setLinearVelocity(new Vector2(5f, player.getBody().getLinearVelocity().y));
        if (player.getBody().getPosition().y < -500 / PPM || player.getBody().getPosition().x > 5000 / PPM) {
            player.getBody().setTransform(100 / PPM, 500 / PPM, 0);
        }
        player.update(delta);

        for (Crystal c : crystals) {
            c.update(delta);
        }
    }

    @Override
    public void render() {
        //cam should follow player
        cam.position.set(new Vector2(
                player.getPosition().x * PPM + Game.V_WIDTH / 7f,
                player.getPosition().y * PPM + Game.V_HEIGHT / 7f
        ), 0);
        cam.update();
        b2dCam.position.set(new Vector2(
                player.getPosition().x + Game.V_WIDTH / 7f / PPM,
                player.getPosition().y + Game.V_HEIGHT / 7f / PPM
        ), 0);
        b2dCam.update();

        if (DEBUG) {
            debugRenderer.render(world, b2dCam.combined);
        }

        //draw map
        tmr.setView(cam);
        tmr.render();

        //draw player
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);
        for (Crystal c : crystals) {
            c.render(sb);
        }

        sb.setProjectionMatrix(hudCam.combined);
        hud.render(sb);
    }

    @Override
    public void handleInput() {
        if (MyInput.isButton1JustPressed()) {
            if (cl.isPlayerOnGround()) {
                player.getBody().applyForceToCenter(0, 15000, true);
                player.getBody().applyAngularImpulse(-15f, true);
            }
        }
        if (MyInput.isButton2JustPressed()) {
            switchBlocks();
        }
    }

    @Override
    public void dispose() {
        res.dispose();
    }

    private void createPlayer() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //create Player
        bdef.position.set(100 / PPM, 500 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearVelocity.set(3, 0);
        bdef.fixedRotation = false;
        Body body = world.createBody(bdef);
        shape.setAsBox(363 * 0.15f / PPM, 458 * 0.15f / PPM);
        fdef.shape = shape;
        fdef.density = 10f;
        fdef.filter.categoryBits = Variables.BIT_PLAYER;   // category
        fdef.filter.maskBits = Variables.BIT_RED | Variables.BIT_Crystal;    // can collide with
        body.createFixture(fdef).setUserData("player");

        //create foot sensor
        shape.setAsBox(363 * 0.16f / PPM, 458 * 0.16f / PPM, new Vector2(0, 0), 0);
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

    private void createLayer(String layerName) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

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

    private void createCrystals() {
        crystals = new Array<>();

        MapLayer layer = map.getLayers().get("crystals");

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(97 / 2f / PPM, 78 / 2f / PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Variables.BIT_Crystal;
        fdef.filter.maskBits = Variables.BIT_PLAYER;

        for (MapObject mo : layer.getObjects()) {
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(
                    (float) mo.getProperties().get("x") / PPM,
                    (float) mo.getProperties().get("y") / PPM
            );
            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("crystal");
            Crystal c = new Crystal(body);
            crystals.add(c);
            body.setUserData(c);
        }
    }

    private void switchBlocks() {
        Filter filter = player.getBody().getFixtureList().first().getFilterData();
        short bits = filter.maskBits;

        //switch to next color
        // red -> green -> blue -> red
        if ((bits & Variables.BIT_RED) != 0) {
            bits &= ~Variables.BIT_RED;
            bits |= Variables.BIT_GREEN;
        } else if ((bits & Variables.BIT_GREEN) != 0) {
            bits &= ~Variables.BIT_GREEN;
            bits |= Variables.BIT_BLUE;
        } else if ((bits & Variables.BIT_BLUE) != 0) {
            bits &= ~Variables.BIT_BLUE;
            bits |= Variables.BIT_RED;
        }

        //set new MaskBits
        filter.maskBits = bits;
        player.getBody().getFixtureList().first().setFilterData(filter);

        //set new MaskBits for foot
        filter = player.getBody().getFixtureList().get(1).getFilterData();
        bits &= ~Variables.BIT_Crystal;
        filter.maskBits = bits;
        player.getBody().getFixtureList().get(1).setFilterData(filter);
    }
}
