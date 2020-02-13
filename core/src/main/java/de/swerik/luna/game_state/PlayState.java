package de.swerik.luna.game_state;

import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.swerik.luna.Luna;
import de.swerik.luna.ecs.components.PlayerDataComponent;
import de.swerik.luna.ecs.components.TypeComponent;
import de.swerik.luna.ecs.components.graphics.RenderableComponent;
import de.swerik.luna.ecs.components.graphics.SpriteComponent;
import de.swerik.luna.ecs.components.physics.BodyComponent;
import de.swerik.luna.ecs.components.physics.PositionComponent;
import de.swerik.luna.ecs.components.physics.VelocityComponent;
import de.swerik.luna.ecs.components.states.EntityStateComponent;
import de.swerik.luna.ecs.components.states.SensorCollisionComponent;
import de.swerik.luna.ecs.systems.*;
import de.swerik.luna.manager.GameStateManager;
import de.swerik.luna.manager.Logger;
import de.swerik.luna.manager.Strategy;
import de.swerik.luna.utils.BodyGenerator;
import de.swerik.luna.utils.Music;
import de.swerik.luna.utils.Variables;

import static de.swerik.luna.utils.Variables.PPM;

public class PlayState extends GameState {

    private OrthographicCamera cam;

    private World world;

    private Box2DDebugRenderer debugRenderer;

    private BitmapFont font;

    // Entity Manager
    private com.artemis.World artemisWorld;

    private Music music;

    public PlayState(Luna app, GameStateManager gsm) {
        super(app, gsm);
        Logger.log("Cnstrct Playstate", Logger.DEBUG);
    }

    @Override
    public void show() {
        Logger.log("Show \tPlaystate", Logger.DEBUG);

        setBackgroundColor(0f, 0, 0f, 1);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Luna.V_WIDTH, Luna.V_HEIGHT);
        cam.position.set(Luna.V_WIDTH / 2, Luna.V_HEIGHT / 2, 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        // Box2D Stuff
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Entity Manager
        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(
                        new CollisionSystem(world),
                        new GravitySystem(),
                        new MovementSystem(),
                        new PositionSystem(),
                        new RenderSystem(batch),
                        new TurnSystem(batch)
                )
                .register(new Strategy()).build();
        artemisWorld = new com.artemis.World(config);
        initEntities();

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        music = new Music(app.assets);
    }

    @Override
    public void hide() {
        Logger.log("Hide \tPlaystate", Logger.DEBUG);
    }

    @Override
    public void update(float delta) {
        world.step(1f / 60f, 6, 2);
//        entityManager.update(1f/delta);
        artemisWorld.setDelta(delta);
        artemisWorld.process();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            music.play();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            music.bass();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            music.synth();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            music.complete();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            music.both();
        }
    }

    @Override
    public void render() {
        debugRenderer.render(world, cam.combined.cpy().scl(PPM));

        batch.begin();
        ((Strategy) artemisWorld.getInvocationStrategy()).render();
        font.draw(batch, Gdx.graphics.getFramesPerSecond() + "", 10, Luna.V_HEIGHT - 10);
        batch.end();
    }

    @Override
    public void pause() {
        Logger.log("Pause \tPlaystate", Logger.DEBUG);
    }

    @Override
    public void resume() {
        Logger.log("Resume \tPlaystate", Logger.DEBUG);
    }

    @Override
    public void resize(int width, int height) {
        Logger.log("Resize \tPlaystate", Logger.DEBUG);
    }

    @Override
    public void dispose() {
        Logger.log("Dispose Playstate", Logger.DEBUG);
    }

    private void initEntities() {
        //Entities:
        int player = artemisWorld.create();
        SpriteComponent spriteComponent = new SpriteComponent(new Texture("placeholder/sprites/ninjaboy/Idle__000.png"));
        PositionComponent positionComponent = new PositionComponent(200, 200);
        artemisWorld.edit(player).add(positionComponent)
                .add(new TypeComponent(Variables.COLLISION_PLAYER))
                .add(new PlayerDataComponent())
                .add(spriteComponent)
                .add(new VelocityComponent(300, 0))
                .add(new EntityStateComponent())
                .add(new SensorCollisionComponent())
                .add(new RenderableComponent())
                .add(new BodyComponent(positionComponent, BodyGenerator.generate(player,
                        spriteComponent.sprites.first(),
                        "bodies/Player.json",
                        Variables.FRIENDLY_BITS,
                        world)));

        int wallEntity = artemisWorld.create();
        PositionComponent wallPositionComponent = new PositionComponent(0, 0);
        SpriteComponent wallSpriteComponent = new SpriteComponent(new Texture("placeholder/sprites/ninjaboy/Idle__000.png"));
        artemisWorld.edit(wallEntity).add(wallPositionComponent)
                .add(new SensorCollisionComponent())
                .add(new RenderableComponent())
                .add(wallSpriteComponent)
                .add(new BodyComponent(wallPositionComponent, BodyGenerator.generate(wallEntity,
                        wallSpriteComponent.sprites.first(),
                        "bodies/Wall.json",
                        Variables.LEVEL_BITS,
                        world)));
    }
}
