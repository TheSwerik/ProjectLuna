package de.swerik.MorpheusTest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import de.swerik.luna.Animation;
import de.swerik.luna.InputHandler;

import static de.swerik.MorpheusTest.Main.HEIGHT;
import static de.swerik.MorpheusTest.Main.WIDTH;

public class GameScreen extends AbstractScreen {
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

    public GameScreen(Game game) {
        super(game);
        batch = new SpriteBatch();
        img = new Texture("sprites/ninjaboy/Running.png");
        regions = TextureRegion.split(img, 363, 458);
        sprite = new Sprite(regions[0][0]);
        sprite.setScale(0.25f);

        runningAnimation = new Animation(sprite, regions);

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
        fd.density = 1000f;
        Fixture fixture = body.createFixture(fd);
        shape.dispose();

        inputHandler = new InputHandler(sprite, runningAnimation, body);

        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        world.step(delta, 5, 2);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
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
//        camera.position.x = WIDTH/2f;
//        camera.position.y = HEIGHT/2f;
        camera.zoom = 1000f;
        camera.update();
        batch.setProjectionMatrix(camera.combined);


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
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        tm.dispose();
        effect.dispose();
        Gdx.app.exit();
        System.exit(0);
    }
}
