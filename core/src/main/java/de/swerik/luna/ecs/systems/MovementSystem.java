package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.swerik.luna.ecs.components.*;
import de.swerik.luna.ecs.components.physics.BodyComponent;
import de.swerik.luna.ecs.components.physics.PositionComponent;
import de.swerik.luna.ecs.components.physics.VelocityComponent;
import de.swerik.luna.ecs.components.states.EntityStateComponent;
import de.swerik.luna.ecs.components.states.SensorCollisionComponent;
import de.swerik.luna.manager.Input;

public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<SensorCollisionComponent> scm = ComponentMapper.getFor(SensorCollisionComponent.class);
    private final ComponentMapper<EntityStateComponent> esm = ComponentMapper.getFor(EntityStateComponent.class);

    public MovementSystem() {
        super(Family.all(PositionComponent.class, VelocityComponent.class, EntityStateComponent.class, BodyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        delta = 60f / (1000f * delta);
        BodyComponent bodyCom = bm.get(entity);
        VelocityComponent velocityCom = vm.get(entity);
        SensorCollisionComponent sensorCom = scm.get(entity);
        EntityStateComponent stateCom = esm.get(entity);

        // handle movement:
        Body body = bodyCom.body;
        float acceleration = stateCom.state == EntityStateComponent.State.GROUNDED ? 0.3f : stateCom.state == EntityStateComponent.State.AIRBORN ? 0.05f : 0;
        float momentumX = body.getLinearVelocity().x;

        float desiredVelocity = Math.max(body.getLinearVelocity().x - acceleration,
                Math.min(Input.keyForce.x * velocityCom.x * delta, body.getLinearVelocity().x + acceleration));

        float velocityChange = desiredVelocity - momentumX;
        float impulse = body.getMass() * velocityChange;
        body.applyLinearImpulse(impulse, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);

        // handle jump:
        if (stateCom.state == EntityStateComponent.State.GROUNDED && Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            body.applyForceToCenter(0, 200f * delta, true);
        }
    }
}
