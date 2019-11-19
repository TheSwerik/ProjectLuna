package de.swerik.luna;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;

public class Luna extends Game {
    SpriteBatch batch;
    Texture img;
    TextureRegion[][] regions;
    Sprite sprite;
    InputHandler inputHandler;
    Animation runningAnimation;

    TiledMap tm;
    TiledMapRenderer tmr;

    OrthographicCamera camera;

    BitmapFont font;

    ParticleEffect effect = new ParticleEffect();

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("Sprites/ninjaboy/Running.png");
        regions = TextureRegion.split(img, 363, 458);
        sprite = new Sprite(regions[0][0]);
        sprite.setScale(0.25f);

        runningAnimation = new Animation(sprite, regions);
        inputHandler = new InputHandler(sprite, runningAnimation);

        tm = new TmxMapLoader().load("Maps/TMX/Test.tmx");
        tmr = new OrthoCachedTiledMapRenderer(tm);

        camera = new OrthographicCamera(1, (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth());

        font = new BitmapFont();
        font.setColor(Color.BLUE);

//        setScreen(new MenuScreen(this));

        effect.load(Gdx.files.internal("particles/TestFlame.p"), Gdx.files.internal("particles/"));
        effect.start();
        effect.setPosition(200f,200f);

        Gdx.input.setInputProcessor(inputHandler);
    }

    //you need to remove render here for screens to work
    @Override
    public void render() {
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprite.translateX(inputHandler.getMovement());
        effect.setPosition(sprite.getX(),sprite.getY());
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

        batch.begin();
        sprite.draw(batch);
        font.draw(batch, "Hello, this works.", 200, 200);
        effect.draw(batch,Gdx.graphics.getDeltaTime());
        batch.end();
        if(effect.isComplete()){
            effect.reset();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        tm.dispose();
        System.exit(0);
    }
}
