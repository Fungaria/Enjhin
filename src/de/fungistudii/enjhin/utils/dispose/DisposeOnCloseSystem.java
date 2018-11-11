/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.utils.dispose;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author sreis
 */
public class DisposeOnCloseSystem extends EntitySystem{

    public void dispose() {
        for (Entity entity : super.getEngine().getEntities()) {
            if(entity instanceof Disposable)
                ((Disposable) entity).dispose();
            
            for (Component component : entity.getComponents()) {
                if(component instanceof Disposable)
                    ((Disposable) component).dispose();
            }
        }
    }
}
