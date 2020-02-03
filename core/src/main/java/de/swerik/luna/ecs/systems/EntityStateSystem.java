package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.swerik.luna.Luna;
import de.swerik.luna.ecs.components.*;
import de.swerik.luna.manager.Input;
import de.swerik.luna.manager.LogManager;

public class EntityStateSystem extends EntitySystem {
    private final Luna app;
    private ImmutableArray<Entity> entities;

    private final ComponentMapper<SensorCollisionComponent> scm = ComponentMapper.getFor(SensorCollisionComponent.class);
    private final ComponentMapper<EntityStateComponent> esm = ComponentMapper.getFor(EntityStateComponent.class);

    public EntityStateSystem(Luna app) {
        this.app = app;
    }

    public void addedToEngine(Engine engine) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SensorCollisionComponent.class).get());
        } else {
            app.logger.log("ControlledMovementSystem: Ashley Engine is null.", LogManager.ERROR);
        }
    }

    public void update(float delta) {
        for (Entity entity : entities) {
            SensorCollisionComponent scc = scm.get(entity);
        }
    }
}
