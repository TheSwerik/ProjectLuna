package de.swerik.luna;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Luna extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture img;
    private TextureRegion[][] regions;
    private Sprite sprite;
    private InputHandler inputHandler;
    private Animation runningAnimation;

    private TiledMap tm;
    private TiledMapRenderer tmr;

    private OrthographicCamera camera;

    private BitmapFont font;

    private ParticleEffect effect = new ParticleEffect();

    private static World world;
    private static Body body;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("sprites/ninjaboy/Running.png");
        regions = TextureRegion.split(img, 363, 458);
        sprite = new Sprite(regions[0][0]);
        sprite.setScale(0.25f);

        runningAnimation = new Animation(sprite, regions);
        inputHandler = new InputHandler(sprite, runningAnimation);

        tm = new TmxMapLoader().load("maps/tmx/Test.tmx");
        tmr = new OrthoCachedTiledMapRenderer(tm);

        camera = new OrthographicCamera(1, (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth());

        font = new BitmapFont();
        font.setColor(Color.BLUE);

        effect.load(Gdx.files.internal("particles/TestFlame.p"), Gdx.files.internal("particles/"));
        effect.start();
        effect.setPosition(200f, 200f);

        world = new World(new Vector2(0, -98f), true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 10;
        Fixture fixture = body.createFixture(fd);
        shape.dispose();

        Gdx.input.setInputProcessor(inputHandler);
    }

    //you need to remove render here for screens to work
    @Override
    public void render() {
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprite.translateX(inputHandler.getMovement());
        effect.setPosition(sprite.getX(), sprite.getY());
        if (!sprite.isFlipX()) {
            sprite.flip(inputHandler.isMovingLeft(), false);
        }

        tmr.setView(camera);
        tmr.render();

        camera.position.x = sprite.getX() + sprite.getOriginX();
        camera.position.y = sprite.getY() + sprite.getOriginY();
        camera.zoom = 1000f;
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        world.step(16, 5, 2);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);

        batch.begin();
        sprite.draw(batch);
        font.draw(batch, "Hello, this works.", 200, 200);
        effect.draw(batch, Gdx.graphics.getDeltaTime());
        batch.end();
        if (effect.isComplete()) {
            effect.reset();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        tm.dispose();
        effect.dispose();
        System.exit(0);
    }
}
