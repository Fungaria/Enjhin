/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics;

import box2dLight.Light;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Disposable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author sreis
 */
public class LightComponent extends ArrayList<Light> implements Component, Disposable{

    public LightComponent() {
    }
    
    public LightComponent(Light... light) {
        super(Arrays.asList(light));
    }

    public LightComponent(Collection<? extends Light> c) {
        super(c);
    }
    
    @Override
    public void dispose() {
        for (Light light : this) {
            light.remove(true);
        }
    }
    
    public void setAllActive(boolean active){
        for (Light light : this) {
            light.setActive(active);
        }
    }
}
