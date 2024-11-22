package com.badlogic.hassubhoi;

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Object userDataA = fixA.getBody().getUserData();
        Object userDataB = fixB.getBody().getUserData();

        if (userDataA instanceof Bird && userDataB instanceof Pig) {
            ((Pig) userDataB).takeDamage(10);
        } else if (userDataB instanceof Bird && userDataA instanceof Pig) {
            ((Pig) userDataA).takeDamage(10);
        }

        if (userDataA instanceof Bird && userDataB instanceof Structure) {
            ((Structure) userDataB).takeDamage(5);
        } else if (userDataB instanceof Bird && userDataA instanceof Structure) {
            ((Structure) userDataA).takeDamage(5);
        }

        // Handle other collision cases if needed
    }

    @Override
    public void endContact(Contact contact) {
        // Not needed for now
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Not needed for now
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Not needed for now
    }
}
