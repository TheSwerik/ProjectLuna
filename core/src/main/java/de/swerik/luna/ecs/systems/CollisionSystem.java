package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.*;
import de.swerik.luna.ecs.components.TypeComponent;
import de.swerik.luna.ecs.components.physics.BodyComponent;
import de.swerik.luna.ecs.components.states.EntityStateComponent;
import de.swerik.luna.ecs.components.states.SensorCollisionComponent;
import de.swerik.luna.utils.Variables;

import static de.swerik.luna.utils.Variables.LEVEL_BITS;

public class CollisionSystem extends IteratingSystem implements ContactListener {
    private final ComponentMapper<SensorCollisionComponent> scm = ComponentMapper.getFor(SensorCollisionComponent.class);
    private final ComponentMapper<EntityStateComponent> esm = ComponentMapper.getFor(EntityStateComponent.class);
    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    private final ComponentMapper<TypeComponent> tm = ComponentMapper.getFor(TypeComponent.class);

    public CollisionSystem(World world) {
        super(Family.all(SensorCollisionComponent.class, BodyComponent.class, EntityStateComponent.class).get());
        world.setContactListener(this);
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        SensorCollisionComponent scc = scm.get(entity);
        EntityStateComponent esc = esm.get(entity);

        if (scc.numFoot > 0) {
            esc.state = EntityStateComponent.State.GROUNDED;
        } else {
            esc.state = EntityStateComponent.State.AIRBORN;
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Entity entityA = (Entity) fixtureA.getUserData();
        Entity entityB = (Entity) fixtureB.getUserData();

        SensorCollisionComponent data;
        short categoryBits;

        if (fixtureA.isSensor() && fixtureB.getFilterData().categoryBits == LEVEL_BITS) {
            data = scm.get(entityA);
            categoryBits = fixtureA.getFilterData().categoryBits;
        } else if (fixtureB.isSensor() && fixtureA.getFilterData().categoryBits == LEVEL_BITS) {
            data = scm.get(entityB);
            categoryBits = fixtureB.getFilterData().categoryBits;
        } else {
            return;
        }

        switch (categoryBits) {
            case Variables.FOOT_SENSOR:
                data.numFoot++;
                break;
            case Variables.RIGHT_WALL_SENSOR:
                data.numRightWall++;
                break;
            case Variables.LEFT_WALL_SENSOR:
                data.numLeftWall++;
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Entity entityA = (Entity) fixtureA.getUserData();
        Entity entityB = (Entity) fixtureB.getUserData();

        SensorCollisionComponent data;
        short categoryBits;

        if (fixtureA.isSensor() && fixtureB.getFilterData().categoryBits == LEVEL_BITS) {
            data = scm.get(entityA);
            categoryBits = fixtureA.getFilterData().categoryBits;
        } else if (fixtureB.isSensor() && fixtureA.getFilterData().categoryBits == LEVEL_BITS) {
            data = scm.get(entityB);
            categoryBits = fixtureB.getFilterData().categoryBits;
        } else {
            return;
        }

        switch (categoryBits) {
            case Variables.FOOT_SENSOR:
                data.numFoot--;
                break;
            case Variables.RIGHT_WALL_SENSOR:
                data.numRightWall--;
                break;
            case Variables.LEFT_WALL_SENSOR:
                data.numLeftWall--;
                break;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
