package de.swerik.Box2D_Tutorial.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {

    private int numFootContacts;

    private Array<Body> removeList;

    public MyContactListener() {
        super();
        removeList = new Array<Body>();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        nullCheck(fa, fb);

        if (fa.getUserData().equals("foot")) {
            numFootContacts++;
        }
        if (fb.getUserData().equals("foot")) {
            numFootContacts++;
        }

        if (fa.getUserData().equals("crystal")) {
            removeList.add(fa.getBody());
        }
        if (fb.getUserData().equals("crystal")) {
            removeList.add(fb.getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        nullCheck(fa, fb);

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

    private void nullCheck(Fixture fa, Fixture fb) {
        if (fa.getUserData() == null && fb.getUserData() != null) {
            throw new NullPointerException(fb.getUserData() + " collided with something with null-ID.");
        } else if (fa.getUserData() != null && fb.getUserData() == null) {
            throw new NullPointerException(fa.getUserData() + " collided with something with null-ID.");
        } else if (fa.getUserData() == null && fb.getUserData() == null) {
            throw new NullPointerException("Two objects with null-ID collided.");
        }
    }

    public Array<Body> getRemoveList() {
        return removeList;
    }
}
