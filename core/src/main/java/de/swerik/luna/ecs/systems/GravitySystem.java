package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.swerik.luna.ecs.components.physics.BodyComponent;
import de.swerik.luna.ecs.components.physics.GravityComponent;
import de.swerik.luna.ecs.components.physics.PositionComponent;
import de.swerik.luna.ecs.components.physics.VelocityComponent;
import de.swerik.luna.ecs.components.states.EntityStateComponent;
import de.swerik.luna.ecs.components.states.SensorCollisionComponent;

public class GravitySystem extends IteratingSystem {
    private final ComponentMapper<GravityComponent> gm = ComponentMapper.getFor(GravityComponent.class);
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public GravitySystem() {
        super(Family.all(PositionComponent.class, GravityComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
//        PositionComponent pCom = pm.get(entity);
//        VelocityComponent vCom = vm.get(entity);
//        float gravity = gm.get(entity).gravity;
//
//        vCom.y -= gravity * delta;
//
//        pCom.x += vCom.x * delta;
//        pCom.y += vCom.y * delta;
    }
}
