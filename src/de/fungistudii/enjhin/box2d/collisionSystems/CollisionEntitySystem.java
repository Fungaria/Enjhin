package de.fungistudii.enjhin.box2d.collisionSystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import de.fungistudii.enjhin.GameScreen;

/**
 * an {@link EntitySystem} (yay you got an {@link EntitySystem#update(float)} method ) that also provides Coliision Methods (these get called automaticly, just override)
 * @author sreis
 */
public abstract class CollisionEntitySystem extends EntitySystem implements EntityCollisionListener{

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
    public void beginContact(CollisionSystemsNotifier.PhysicsComponentCollision collision) {
    }

    @Override
    public void endContact(CollisionSystemsNotifier.PhysicsComponentCollision collision) {
    }

    @Override
    public boolean shouldProcessCollision(CollisionSystemsNotifier.PhysicsComponentCollision collision) {
        return true;
    }
    
}
