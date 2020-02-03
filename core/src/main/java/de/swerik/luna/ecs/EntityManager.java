package de.swerik.luna.ecs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
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
import de.swerik.luna.ecs.systems.MovementSystem;
import de.swerik.luna.ecs.systems.RenderSystem;
import de.swerik.luna.ecs.systems.CollisionSystem;
import de.swerik.luna.utils.BodyGenerator;
import de.swerik.luna.utils.Variables;

public class EntityManager {
    private final Engine engine;
    private World world;
    private Entity player;
    private static Array<Entity> entities = new Array<>();
    private static Array<Entity> destroyEntities = new Array<>();

    public EntityManager( Engine e, SpriteBatch batch, World world) {
        engine = e;
        this.world = world;

        MovementSystem ms = new MovementSystem();
        engine.addSystem(ms);
        RenderSystem rs = new RenderSystem(batch);
        engine.addSystem(rs);
        CollisionSystem scs = new CollisionSystem( world);
        engine.addSystem(scs);

        player = new Entity();
        player.add(new PositionComponent(100, 100))
                .add(new VelocityComponent(3))
                .add(new MomentumComponent(new Vector2(0, 0)))
                .add(new EntityStateComponent())
                .add(new SensorCollisionComponent())
                .add(new RenderableComponent())
                .add(new SpriteComponent(new Texture("placeholder/sprites/ninjaboy/Idle__000.png")))
                .add(new BodyComponent(BodyGenerator.generate("bodies/Player.json",
                        Variables.COLLISION_PLAYER,
                        Variables.COLLISION_PLAYER,
                        world,
                        player.getComponent(SpriteComponent.class).sprite)));
        engine.addEntity(player);

        Entity wallEntity = new Entity();
        wallEntity.add(new PositionComponent(100, 100))
                .add(new SensorCollisionComponent())
                .add(new RenderableComponent())
                .add(new SpriteComponent(new Texture("placeholder/sprites/ninjaboy/Idle__000.png")))
                .add(new BodyComponent(BodyGenerator.generate("bodies/Wall.json",
                        Variables.COLLISION_LEVEL,
                        Variables.COLLISION_LEVEL,
                        world,
                        wallEntity.getComponent(SpriteComponent.class).sprite)));
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
