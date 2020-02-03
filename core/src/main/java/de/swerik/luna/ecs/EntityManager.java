package de.swerik.luna.ecs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.swerik.luna.Luna;
import de.swerik.luna.ecs.components.*;
import de.swerik.luna.ecs.systems.MovementSystem;
import de.swerik.luna.ecs.systems.RenderSystem;
import de.swerik.luna.ecs.systems.SensorCollisionSystem;
import de.swerik.luna.utils.BodyGenerator;
import de.swerik.luna.utils.Variables;

public class EntityManager {
    private final Engine engine;
    private final Luna app;

    public EntityManager(Luna app, Engine e, SpriteBatch batch, World world) {
        engine = e;
        this.app = app;

        MovementSystem cms = new MovementSystem(app);
        engine.addSystem(cms);
        RenderSystem rs = new RenderSystem(batch, app);
        engine.addSystem(rs);
        MovementSystem ms = new MovementSystem(app);
        engine.addSystem(ms);
        SensorCollisionSystem scs = new SensorCollisionSystem(app, world);
        engine.addSystem(scs);

        Entity playerEntity = new Entity();
        playerEntity.add(new PositionComponent(100, 100))
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
                        playerEntity.getComponent(SpriteComponent.class).sprite)));
        engine.addEntity(playerEntity);

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
    }
}
