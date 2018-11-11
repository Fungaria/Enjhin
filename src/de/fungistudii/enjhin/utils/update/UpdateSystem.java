/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.utils.update;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * essential
 * @author Samuel
 */
public class UpdateSystem extends IteratingSystem{
    
    private ComponentMapper<UpdateComponent> updateMapper = ComponentMapper.getFor(UpdateComponent.class);
    
    public UpdateSystem(){
        super(Family.all(UpdateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float f) {
        UpdateComponent component = updateMapper.get(entity);
        component.updatable.update();
    }
}
