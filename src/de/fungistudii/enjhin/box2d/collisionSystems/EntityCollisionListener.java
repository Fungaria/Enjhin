/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.box2d.collisionSystems;

import de.fungistudii.enjhin.box2d.contactInterfaces.ContactAdapter;

/**
 * Interface for all the Collision Systems
 * @see ContactAdapter
 * but adapded to Ashley workflow
 * @author sreis
 */
public interface EntityCollisionListener {

    public void beginContact(CollisionSystemsNotifier.PhysicsComponentCollision collision);

    public void endContact(CollisionSystemsNotifier.PhysicsComponentCollision collision);
    
    public boolean shouldProcessCollision(CollisionSystemsNotifier.PhysicsComponentCollision collision);
}
