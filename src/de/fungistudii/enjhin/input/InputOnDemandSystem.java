/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.enjhin.GameScreen;
import de.fungistudii.enjhin.box2d.collisionSystems.CollisionSystemsNotifier;
import de.fungistudii.enjhin.utils.OnDemandEntitySystem;

/**
 * In adition to {@link InputEntitySystem} this class also provides an entitiesInFamilyArray
 * (so iterating can be done on demand p.e. in the input-methods
 * @author Samuel
 */
public abstract class InputOnDemandSystem extends OnDemandEntitySystem implements InputProcessor{
    
    
    private Array<CollisionSystemsNotifier.PhysicsComponentCollision> collisions = new Array<CollisionSystemsNotifier.PhysicsComponentCollision>();
    
    
    public InputOnDemandSystem(Family family) {
        super(family);
    }
    
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.getSystem(InputSystemsNotifier.class).addListener(this);
    }
    
    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.getSystem(InputSystemsNotifier.class).removeListener(this);
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
