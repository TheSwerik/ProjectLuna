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
import de.swerik.luna.manager.Input;

public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<MomentumComponent> mm = ComponentMapper.getFor(MomentumComponent.class);
    private final ComponentMapper<EntityStateComponent> esm = ComponentMapper.getFor(EntityStateComponent.class);

    public MovementSystem() {
        super(Family.all(PositionComponent.class, VelocityComponent.class, MomentumComponent.class, EntityStateComponent.class, BodyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        BodyComponent bodyCom = bm.get(entity);
        VelocityComponent velocityCom = vm.get(entity);
        MomentumComponent momentumCom = mm.get(entity);
        EntityStateComponent stateCom = esm.get(entity);

        // handle movement:
        float acceleration = stateCom.state == EntityStateComponent.State.GROUNDED ? 0.3f : stateCom.state == EntityStateComponent.State.AIRBORN ? 0.1f : 0;
        Body body = bodyCom.body;
        Vector2 momentum = momentumCom.momentum;

        float desiredVelocity = Math.max(body.getLinearVelocity().x - acceleration,
                Math.min(Input.xMovement * velocityCom.velocity,
                        body.getLinearVelocity().x + acceleration));

        momentum.x = body.getLinearVelocity().x;

        float velocityChange = desiredVelocity - momentum.x;
        float impulse = body.getMass() * velocityChange;
        body.applyLinearImpulse(impulse, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);

        // handle jump:
        if (stateCom.state == EntityStateComponent.State.GROUNDED && Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            body.applyForceToCenter(0, 70f, true);
        }
    }
}
