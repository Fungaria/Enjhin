/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.box2d;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author sreiser
 * TODO: prohibit doubole disposal crash in native?? --
 */
public class PhysicsBodyComponent implements Component, Disposable{
    public Body body;

    public PhysicsBodyComponent(Body body){
        this.body = body;
    }

    public PhysicsBodyComponent() {
    }
    
    @Override
    public void dispose() {
        if(!body.getWorld().isLocked())
            body.getWorld().destroyBody(body);
        else
            System.err.println("Cannot dispose Body: World locked");
    }
}
