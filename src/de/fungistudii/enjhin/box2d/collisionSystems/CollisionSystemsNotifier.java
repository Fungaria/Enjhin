package de.fungistudii.enjhin.box2d.collisionSystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.enjhin.box2d.PhysicsBodyComponent;
import de.fungistudii.enjhin.input.InputSystemsNotifier;

/**
 * the big Brother of the whole collision System: detects collisions and forwards them
 * to every {@link EntityCollisionListener} (aka all the Collision Systems) 
 * buffers Collision-Method-Calls to update, bcz multi Threding breaks everything all the time (weird random Runtime Crash with no err log)
 * @see InputSystemsNotifier
 * TODO: endContact, preSolve, postSolve
 * @author Samuel
 */
public final class CollisionSystemsNotifier extends EntitySystem implements ContactListener {

    private ImmutableArray<Entity> entitiesWithPhysicsComponent;
    private static final ComponentMapper<PhysicsBodyComponent> physicsMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);

    private final Array<EntityCollisionListener> listeners = new Array<EntityCollisionListener>();

    private final Array<PhysicsComponentCollision> contactBegins = new Array<PhysicsComponentCollision>();
    private final Array<PhysicsComponentCollision> contactEnds = new Array<PhysicsComponentCollision>();

    public CollisionSystemsNotifier() {
        super();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for (EntityCollisionListener listener : listeners) {
            for (PhysicsComponentCollision contactBegin : contactBegins) {
                if (listener.shouldProcessCollision(contactBegin)) {
                    listener.beginContact(contactBegin);
                }
            }
            for (PhysicsComponentCollision contactEnd : contactEnds) {
                if (listener.shouldProcessCollision(contactEnd)) {
                    listener.endContact(contactEnd);
                }
            }
        }

        contactBegins.clear();
        contactEnds.clear();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entitiesWithPhysicsComponent = engine.getEntitiesFor(Family.all(PhysicsBodyComponent.class).get());
        super.addedToEngine(engine);
    }

    @Override
    public void beginContact(Contact contact) {
        PhysicsComponentCollision collisionData = createCollisionData(contact, "begin");
        contactBegins.add(collisionData);
    }

    @Override
    public void endContact(Contact contact) {
        PhysicsComponentCollision collisionData = createCollisionData(contact, "end");
        contactEnds.add(collisionData);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    public void addListener(EntityCollisionListener listener) {
        if(!listeners.contains(listener, false))
            listeners.add(listener);
    }

    public void removeListener(EntityCollisionListener listener) {
        listeners.removeValue(listener, true);
    }
    
    private PhysicsComponentCollision createCollisionData(Contact contact, String type) {
        Entity entity1 = null;
        Entity entity2 = null;
        Fixture fix1 = contact.getFixtureA();
        Fixture fix2 = contact.getFixtureB();
        Vector2 normal = contact.getWorldManifold().getNormal();

        for (Entity entity : entitiesWithPhysicsComponent) {
            if(physicsMapper.get(entity) == null)
                continue;
            Body body = physicsMapper.get(entity).body;

            if (body == contact.getFixtureA().getBody()) {
                entity1 = entity;
            }
            if (body == contact.getFixtureB().getBody()) {
                entity2 = entity;
            }
        }

        return new PhysicsComponentCollision(entity1, entity2, fix1, fix2, normal, type);
    }

    public static class PhysicsComponentCollision {

        public String type;
        public Entity entityA;
        public Entity entityB;
        public Fixture fixtureA;
        public Fixture fixtureB;
        public Vector2 contactNormal;

        public PhysicsComponentCollision(Entity entityA, Entity entityB, Fixture fixtureA, Fixture fixtureB, Vector2 contactNormal, String type) {
            this.type = type;
            this.entityA = entityA;
            this.entityB = entityB;
            this.fixtureA = fixtureA;
            this.fixtureB = fixtureB;
            this.contactNormal = contactNormal;
        }

        public PhysicsComponentCollision() {
        }

        public void swapForEntity(Entity entityA) {
            if (entityA.equals(this.entityA)) {
                return;
            }

            if (entityA.equals(this.entityB)) {
                swap();
                return;
            }

            throw new RuntimeException("entityA isnt involved in this collision (its not entityA or entityB)");
        }

        public void swap() {
            Entity tmpEntityA = this.entityA;
            this.entityA = this.entityB;
            this.entityB = tmpEntityA;

            Fixture tmpFixA = this.fixtureA;
            this.fixtureA = this.fixtureB;
            this.fixtureB = tmpFixA;
        }

        @Override
        public String toString() {
            return type + " :" + "\n  entityA: " + entityA + "\n  entityB: " + entityB + "\n  fixtureA: " + fixtureA + "\n  fixtureB: " + fixtureB + "\n  normal " + contactNormal;
        }
    }
}
