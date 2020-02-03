package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.swerik.luna.Luna;
import de.swerik.luna.ecs.components.PositionComponent;
import de.swerik.luna.ecs.components.VelocityComponent;
import de.swerik.luna.manager.Input;
import de.swerik.luna.manager.LogManager;

public class ControlledMovementSystem extends EntitySystem {
    private final Luna app;
    private ImmutableArray<Entity> entities;

    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public ControlledMovementSystem(Luna app) {
        this.app = app;
    }

    public void addedToEngine(Engine engine) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
        } else {
            app.logger.log("ControlledMovementSystem: Ashley Engine is null.", LogManager.ERROR);
        }
    }

    public void update(float delta) {
        for (Entity entity : entities) {
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);

            position.x += velocity.velocity * delta * Input.xMovement;
        }
    }
}
