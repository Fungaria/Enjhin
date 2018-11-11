package de.fungistudii.enjhin.utils;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

/**
 * provides an {@link ImmutableArray} with all Entities in the givien Family (passed in Construktor)
 * @author Samuel
 */
public abstract class OnDemandEntitySystem extends EntitySystem{
    
    protected ImmutableArray<Entity> entitiesInFamily;
    private final Family family;

    public OnDemandEntitySystem(Family family) {
        this.family = family;
    }

    public OnDemandEntitySystem(Family family, int priority) {
        super(priority);
        this.family = family;
    }
    
    @Override
    public void addedToEngine(Engine engine) {
        entitiesInFamily = engine.getEntitiesFor(family);
        super.addedToEngine(engine);
    }
}
