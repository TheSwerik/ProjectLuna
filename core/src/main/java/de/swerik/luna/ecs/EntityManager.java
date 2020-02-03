package de.swerik.luna.ecs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.swerik.luna.ecs.components.*;
import de.swerik.luna.ecs.components.graphics.RenderableComponent;
import de.swerik.luna.ecs.components.graphics.SpriteComponent;
import de.swerik.luna.ecs.components.physics.BodyComponent;
import de.swerik.luna.ecs.components.physics.PositionComponent;
import de.swerik.luna.ecs.components.physics.VelocityComponent;
import de.swerik.luna.ecs.components.states.EntityStateComponent;
import de.swerik.luna.ecs.components.states.SensorCollisionComponent;
import de.swerik.luna.ecs.systems.*;
import de.swerik.luna.utils.BodyGenerator;
import de.swerik.luna.utils.Variables;

public class EntityManager {
    private final Engine engine;
    private World world;
    private Entity player;
    private static Array<Entity> entities = new Array<>();
    private static Array<Entity> destroyEntities = new Array<>();

    public EntityManager(Engine e, SpriteBatch batch, World world) {
        engine = e;
        this.world = world;
        BodyGenerator.setWorld(world);

        // Systems:
        CollisionSystem scs = new CollisionSystem(world);
        GravitySystem gs = new GravitySystem();
        MovementSystem ms = new MovementSystem();
        PositionSystem ps = new PositionSystem();
        RenderSystem rs = new RenderSystem(batch);
        TurnSystem ts = new TurnSystem(batch);
        engine.addSystem(scs);
        engine.addSystem(gs);
        engine.addSystem(ms);
        engine.addSystem(ps);
        engine.addSystem(rs);
        engine.addSystem(ts);

        //Entities:
        player = new Entity();
        SpriteComponent spriteComponent = new SpriteComponent(new Texture("placeholder/sprites/ninjaboy/Idle__000.png"));
        PositionComponent positionComponent = new PositionComponent(100, 100);
        player.add(positionComponent)
                .add(new TypeComponent(Variables.COLLISION_PLAYER))
                .add(new PlayerDataComponent())
                .add(spriteComponent)
                .add(new VelocityComponent(3, 0))
                .add(new EntityStateComponent())
                .add(new SensorCollisionComponent())
                .add(new RenderableComponent())
                .add(new BodyComponent(positionComponent, BodyGenerator.generate(player,
                        spriteComponent.sprites.first(),
                        "bodies/Player.json",
                        Variables.FRIENDLY_BITS)));
        engine.addEntity(player);

        Entity wallEntity = new Entity();
        PositionComponent wallPositionComponent = new PositionComponent(100, 100);
        SpriteComponent wallSpriteComponent = new SpriteComponent(new Texture("placeholder/sprites/ninjaboy/Idle__000.png"));
        wallEntity.add(wallPositionComponent)
                .add(new SensorCollisionComponent())
                .add(new RenderableComponent())
                .add(wallSpriteComponent)
                .add(new BodyComponent(wallPositionComponent,BodyGenerator.generate(wallEntity,
                        wallSpriteComponent.sprites.first(),
                        "bodies/Wall.json",
                        Variables.LEVEL_BITS)));
        engine.addEntity(wallEntity);
    }

    public void update(float delta) {
        engine.update(delta);
        for (Entity entity : EntityManager.destroyEntities) {
            try {
                world.destroyBody(entity.getComponent(BodyComponent.class).body);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        EntityManager.destroyEntities.clear();
    }

    public static void add(Entity entity) {
        EntityManager.entities.add(entity);
    }

    public static void destroy(Entity entity) {
        EntityManager.entities.removeValue(entity, true);
        EntityManager.destroyEntities.add(entity);
    }

    public Entity getPlayer() {
        return player;
    }
}
