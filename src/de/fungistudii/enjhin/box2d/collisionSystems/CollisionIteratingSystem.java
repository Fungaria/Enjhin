/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.box2d.collisionSystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.fungistudii.enjhin.GameScreen;
import de.fungistudii.enjhin.box2d.contactInterfaces.ContactAdapter;

/**
 * a standart {@link IteratingSystem} but in addition also provides collision
 * methods (so it just combines {@link IteratingSystem} and {@link ContactAdapter})
 * you can chose whether the collsion Method should only be called if at least one of the colliding Entities satisfy the Family constraints 
 * (via {@link CollisionOnDemandSystem#setProcessing(boolean)})
 * {@link CollisionOnDemandSystem} will be better in most cases
 * @author sreis
 */
public abstract class CollisionIteratingSystem extends IteratingSystem implements EntityCollisionListener{

    private boolean processAllCollisions = false;
    
    public CollisionIteratingSystem(Family family) {
        super(family);
    }
    
    public CollisionIteratingSystem(Family family, boolean processAllCollisions) {
        this(family);
        this.processAllCollisions = processAllCollisions;
    }
    
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.getSystem(CollisionSystemsNotifier.class).addListener(this);
    }
    
    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.getSystem(CollisionSystemsNotifier.class).removeListener(this);
    }
    
    /** whether to procees all Collisions or only the ones invoking the PhysicsComponent of Entities in the Specified Family*/
    public void getProcessAllCollisions(boolean all){
        this.processAllCollisions = all;
    }
    
    /** whether to procees all Collisions or only the ones invoking the PhysicsComponent of Entities in the Specified Family*/
    public boolean getProcessAllCollisions(){
        return this.processAllCollisions;
    }

    @Override
    public void beginContact(CollisionSystemsNotifier.PhysicsComponentCollision collision){
        if(shouldSwapEntities(collision)){
            collision.swap();
        }
    }
    
    @Override
    public void endContact(CollisionSystemsNotifier.PhysicsComponentCollision collision){
        if(shouldSwapEntities(collision)){
            collision.swap();
        }
    }

    @Override
    public boolean shouldProcessCollision(CollisionSystemsNotifier.PhysicsComponentCollision collision){
        if(processAllCollisions){
            return true;
        }else{
            return super.getEntities().contains(collision.entityA, true) || super.getEntities().contains(collision.entityB, true);
        }
    }
    
    /**so entityA will always be a member of the Family*/
    private boolean shouldSwapEntities(CollisionSystemsNotifier.PhysicsComponentCollision collision){
        return super.getEntities().contains(collision.entityB, true);
    }
}
