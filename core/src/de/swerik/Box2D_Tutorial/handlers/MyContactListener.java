package de.swerik.Box2D_Tutorial.handlers;

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    private int numFootContacts;

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() == null && fb.getUserData() != null) {
            throw new NullPointerException(fb.getUserData() + " collided with something with null-ID.");
        } else if (fa.getUserData() != null && fb.getUserData() == null) {
            throw new NullPointerException(fa.getUserData() + " collided with something with null-ID.");
        } else if (fa.getUserData() == null && fb.getUserData() == null) {
            throw new NullPointerException("Two objects with null-ID collided.");
        }

        if (fa.getUserData().equals("foot")) {
            numFootContacts++;
        }
        if (fb.getUserData().equals("foot")) {
            numFootContacts++;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() == null && fb.getUserData() != null) {
            throw new NullPointerException(fb.getUserData() + " collided with something with null-ID.");
        } else if (fa.getUserData() != null && fb.getUserData() == null) {
            throw new NullPointerException(fa.getUserData() + " collided with something with null-ID.");
        } else if (fa.getUserData() == null && fb.getUserData() == null) {
            throw new NullPointerException("Two objects with null-ID collided.");
        }

        if (fa.getUserData().equals("foot")) {
            numFootContacts--;
        }
        if (fb.getUserData().equals("foot")) {
            numFootContacts--;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerOnGround() {
        return numFootContacts > 0;
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
