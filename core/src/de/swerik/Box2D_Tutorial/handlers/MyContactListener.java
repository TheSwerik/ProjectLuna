package de.swerik.Box2D_Tutorial.handlers;

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    private boolean playerOnGround;

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
            playerOnGround = true;
        }
        if (fb.getUserData().equals("foot")) {
            playerOnGround = true;
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
            playerOnGround = false;
        }
        if (fb.getUserData().equals("foot")) {
            playerOnGround = false;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerOnGround() {
        return playerOnGround;
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
