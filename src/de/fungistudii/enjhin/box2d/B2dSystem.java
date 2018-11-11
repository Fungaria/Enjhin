/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.box2d;

import box2dLight.RayHandler;
import de.fungistudii.enjhin.box2d.contactInterfaces.CFMultiplexer;
import de.fungistudii.enjhin.box2d.contactInterfaces.CLMultiplexer;
import com.badlogic.ashley.core.Engine;
import de.fungistudii.enjhin.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import de.fungistudii.enjhin.box2d.collisionSystems.CollisionSystemsNotifier;
import de.fungistudii.enjhin.box2d.collisionSystems.CollisionOnDemandSystem;

/**
 * manages everything box2d related mainly a {@link World} but also collision Stuff
 * only one instance of this class should exist
 * should be created by {@link GameScreen}
 * @author Samuel
 */
public class B2dSystem extends EntitySystem implements Disposable{
    
    public  int POSITIONITERATIONS = 3;
    public int VELOCITYITERATIONS = 8;
    public float TIMESTEP = 1/40f;
    
    public World world;
    public RayHandler rayHandler;
    
    public CLMultiplexer clMultiplexer;
    public CFMultiplexer cfMultiplexer;
    
    /** * only used by {@link CollisionOnDemandSystem}*/
    public CollisionSystemsNotifier collisionSystemNotifier;
    
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        
        rayHandler.update();
    }
    
    public B2dSystem(){
        world = new World(new Vector2(0, -9.81f), true);
        RayHandler.setGammaCorrection(true);
        rayHandler = new RayHandler(world, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rayHandler.setBlurNum(4);

        clMultiplexer = new CLMultiplexer();
        world.setContactListener(clMultiplexer);
        cfMultiplexer = new CFMultiplexer();
        world.setContactFilter(cfMultiplexer);
        
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        
        collisionSystemNotifier = new CollisionSystemsNotifier();
        clMultiplexer.add(collisionSystemNotifier);
        engine.addSystem(collisionSystemNotifier);
    }
    
    @Override
    public void dispose() {
        rayHandler.dispose();
        world.dispose();
    }
}
