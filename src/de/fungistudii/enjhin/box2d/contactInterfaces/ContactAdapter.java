/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.box2d.contactInterfaces;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author Asus
 */
public abstract class ContactAdapter implements ContactListener{

    /**@see ContactListener#beginContact(com.badlogic.gdx.physics.box2d.Contact) */
    @Override
    public void beginContact(Contact contact) {
    }

    /**@see ContactListener#endContact(com.badlogic.gdx.physics.box2d.Contact) */
    @Override
    public void endContact(Contact contact) {
    }

    /**@see ContactListener#preSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.Manifold) */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    /**@see ContactListener#postSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.ContactImpulse) */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
