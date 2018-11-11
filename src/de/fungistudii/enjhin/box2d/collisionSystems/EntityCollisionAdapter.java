/** this code belongs to Fungi-Studii. You may copy or change it as you like since we currently have no money for a lawyer to sue you. */
package de.fungistudii.enjhin.box2d.collisionSystems;

import com.badlogic.ashley.core.Family;

/**
 *
 * @author sreis
 */
public class EntityCollisionAdapter implements EntityCollisionListener{
    private Family family;
    
    public EntityCollisionAdapter() {
    }

    public EntityCollisionAdapter(Family family) {
        this.family = family;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }
    
    @Override
    public void beginContact(CollisionSystemsNotifier.PhysicsComponentCollision collision) {
        if(shouldSwapEntities(collision)){
            collision.swap();
        }
    }

    @Override
    public void endContact(CollisionSystemsNotifier.PhysicsComponentCollision collision) {
        if(shouldSwapEntities(collision)){
            collision.swap();
        }
    }

    @Override
    public boolean shouldProcessCollision(CollisionSystemsNotifier.PhysicsComponentCollision collision) {
        if(this.family == null)
            return true;
        else
            return family.matches(collision.entityA) || family.matches(collision.entityB);
    }
    
    /**so entityA will always be a member of the Family*/
    private boolean shouldSwapEntities(CollisionSystemsNotifier.PhysicsComponentCollision collision){
        if(family == null)
            return false;
        else
            return family.matches(collision.entityB);
    }
}
