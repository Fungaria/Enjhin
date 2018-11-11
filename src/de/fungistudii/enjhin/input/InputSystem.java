/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import de.fungistudii.enjhin.GameScreen;

/**
 * holds a {@link InputSystemsNotifier} and an {@link InputMultiplexer}
 * -> super class of the whole Input Module
 * only one instance of this class should exist
 * should be created by {@link GameScreen}
 * @author Samuel
 */
public class InputSystem extends EntitySystem{
    public InputMultiplexer inputMultiplexer;
    public InputSystemsNotifier inputNotifier;
    
    public InputSystem(){
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        
        inputNotifier = new InputSystemsNotifier(inputMultiplexer);
        engine.addSystem(inputNotifier);
    }
}
