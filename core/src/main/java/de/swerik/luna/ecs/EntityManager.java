package de.swerik.luna.ecs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import de.swerik.luna.Luna;
import de.swerik.luna.ecs.components.*;
import de.swerik.luna.ecs.systems.ControlledMovementSystem;
import de.swerik.luna.ecs.systems.RenderSystem;

public class EntityManager {
    private final Engine engine;
    private final Luna app;

    public EntityManager(Luna app, Engine e, SpriteBatch batch) {
        engine = e;
        this.app = app;

        ControlledMovementSystem cms = new ControlledMovementSystem(app);
        engine.addSystem(cms);
        RenderSystem rs = new RenderSystem(batch, app);
        engine.addSystem(rs);

        Entity playerEntity = new Entity();
        playerEntity.add(new PositionComponent(500, 500))
                .add(new VelocityComponent(3))
                .add(new SpriteComponent(new Texture("placeholder/sprites/ninjaboy/Idle__000.png")))
                .add(new RenderableComponent());
        engine.addEntity(playerEntity);
    }

    public void update(float delta) {
        engine.update(delta);
    }
}
