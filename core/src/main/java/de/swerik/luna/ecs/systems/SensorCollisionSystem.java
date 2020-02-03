package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.*;
import de.swerik.luna.Luna;
import de.swerik.luna.ecs.components.*;
import de.swerik.luna.manager.LogManager;
import de.swerik.luna.utils.Variables;

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import static de.swerik.luna.utils.Variables.LEVEL_BITS;

public class SensorCollisionSystem extends EntitySystem implements ContactListener {
    private final Luna app;
    private ImmutableArray<Entity> entities;

    private final ComponentMapper<SensorCollisionComponent> scm = ComponentMapper.getFor(SensorCollisionComponent.class);
    private final ComponentMapper<EntityStateComponent> esm = ComponentMapper.getFor(EntityStateComponent.class);

    public SensorCollisionSystem(Luna app, World world) {
        super(-1);
        this.app = app;
        world.setContactListener(this);
    }

    @Override
    public void addedToEngine(Engine engine) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SensorCollisionComponent.class, BodyComponent.class, EntityStateComponent.class).get());
        } else {
            app.logger.log("ControlledMovementSystem: Ashley Engine is null.", LogManager.ERROR);
        }
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            SensorCollisionComponent scc = scm.get(entity);
            EntityStateComponent esc = esm.get(entity);

            if (scc.numFoot > 0) {
                esc.state = EntityStateComponent.State.GROUNDED;
            } else {
                esc.state = EntityStateComponent.State.AIRBORN;
            }
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
            case Variables.FRIENDLY_FOOT_SENSOR:
                data.numFoot++;
                break;
            case Variables.FRIENDLY_RIGHT_WALL_SENSOR:
                data.numRightWall++;
                break;
            case Variables.FRIENDLY_LEFT_WALL_SENSOR:
                data.numLeftWall++;
                break;
            case Variables.FRIENDLY_RIGHT_SENSOR:
                data.numRight++;
                break;
            case Variables.FRIENDLY_LEFT_SENSOR:
                data.numLeft++;
                break;
            case Variables.FRIENDLY_RIGHT_UPPER_SENSOR:
                data.numRightUpper++;
                break;
            case Variables.FRIENDLY_LEFT_UPPER_SENSOR:
                data.numLeftUpper++;
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
