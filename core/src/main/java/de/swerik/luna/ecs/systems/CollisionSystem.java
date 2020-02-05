package de.swerik.luna.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.*;
import de.swerik.luna.ecs.components.TypeComponent;
import de.swerik.luna.ecs.components.physics.BodyComponent;
import de.swerik.luna.ecs.components.states.EntityStateComponent;
import de.swerik.luna.ecs.components.states.SensorCollisionComponent;
import de.swerik.luna.utils.Variables;

import static de.swerik.luna.utils.Variables.LEVEL_BITS;

public class CollisionSystem extends IteratingSystem implements ContactListener {
    private ComponentMapper<SensorCollisionComponent> scm;
    private ComponentMapper<EntityStateComponent> esm;
    private ComponentMapper<BodyComponent> bm;
    private ComponentMapper<TypeComponent> tm;

    public CollisionSystem(World world) {
        super(Aspect.all(SensorCollisionComponent.class, BodyComponent.class, EntityStateComponent.class));
        world.setContactListener(this);
    }

    @Override
    protected void process(int entity) {
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

        int entityA = (int) fixtureA.getUserData();
        int entityB = (int) fixtureB.getUserData();

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

        int entityA = (int) fixtureA.getUserData();
        int entityB = (int) fixtureB.getUserData();

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
