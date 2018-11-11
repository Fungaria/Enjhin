/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.box2d.collisionSystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import de.fungistudii.enjhin.GameScreen;
import de.fungistudii.enjhin.utils.OnDemandEntitySystem;

/**
 * In adition to {@link CollisionEntitySystem} this class also provides an entitiesInFamilyArray
 * (so iterating can be done on demand p.e. in the input-methods
 * you can chose whether the collsion Method should only be called if at least one of the colliding Entities satisfy the Family constraints 
 * (via {@link CollisionOnDemandSystem#setProcessing(boolean)})
   question: why did I call beginContact and endContact in update and not directly (buffer it)? if it has to be like that do it in CollisionSystemsNotifier and caomment there
 * @author Samuel
 */
public abstract class CollisionOnDemandSystem extends OnDemandEntitySystem implements EntityCollisionListener{

    private boolean processAllCollisions = false;
    
    public CollisionOnDemandSystem(Family family) {
        super(family);
    }
    
    public CollisionOnDemandSystem(boolean processAllCollisions) {
        super(Family.all().get());
        this.processAllCollisions = processAllCollisions;
    }
    
    public CollisionOnDemandSystem(Family family, boolean processAllCollisions) {
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
    
    /** whether to procees all Collisions or only the ones invoking the PhysicsComponent of Entities in the Specified Family*/
    public void setProcessAllCollisions(boolean all){
        this.processAllCollisions = all;
    }
    
    /** whether to procees all Collisions or only the ones invoking the PhysicsComponent of Entities in the Specified Family*/
    public boolean getProcessAllCollisions(){
        return this.processAllCollisions;
    }
    
    @Override
    public boolean shouldProcessCollision(CollisionSystemsNotifier.PhysicsComponentCollision collision){
        if(processAllCollisions){
            return true;
        }else{
            return entitiesInFamily.contains(collision.entityA, true) || entitiesInFamily.contains(collision.entityB, true);
        }
    }
    
    /**so entityA will always be a member of the Family*/
    private boolean shouldSwapEntities(CollisionSystemsNotifier.PhysicsComponentCollision collision){
        return entitiesInFamily.contains(collision.entityB, true);
    }
}
