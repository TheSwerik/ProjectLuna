package de.swerik.Box2D_Tutorial.handlers;

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        System.out.println(fa.getUserData());
        System.out.println(fb.getUserData() + "\n");
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

    /*
     *  STEPS:
     *       Collision detection
     *       presolve
     *       Collision handling
     *       postsolve
     *
     * */
}
