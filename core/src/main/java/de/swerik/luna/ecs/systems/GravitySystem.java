package de.swerik.luna.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import de.swerik.luna.ecs.components.physics.BodyComponent;
import de.swerik.luna.ecs.components.physics.GravityComponent;
import de.swerik.luna.ecs.components.physics.PositionComponent;
import de.swerik.luna.ecs.components.physics.VelocityComponent;
import de.swerik.luna.ecs.components.states.EntityStateComponent;
import de.swerik.luna.ecs.components.states.SensorCollisionComponent;

public class GravitySystem extends IteratingSystem {
    private ComponentMapper<GravityComponent> gm;
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<VelocityComponent> vm;

    public GravitySystem() {
        super(Aspect.all(PositionComponent.class, GravityComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(int entity) {
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
