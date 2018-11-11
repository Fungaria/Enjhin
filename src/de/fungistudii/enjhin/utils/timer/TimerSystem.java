/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.utils.timer;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.enjhin.utils.timer.TimerComponent.CallbackListener;

/**
 *
 * @author Samuel
 */
public class TimerSystem extends IteratingSystem{

    private static final ComponentMapper<TimerComponent> mapper = ComponentMapper.getFor(TimerComponent.class);
    
    public TimerSystem() {
        super(Family.all(TimerComponent.class).get());
        System.out.println("jjijjiij");
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TimerComponent timerComponent = mapper.get(entity);
        
        if(!timerComponent.timing)
            return;
        timerComponent.time += deltaTime;
        if(timerComponent.time >= timerComponent.duration){
            fireAllListeners(timerComponent);
            timerComponent.timing = false;
        }
    }
    
    private void fireAllListeners(Array<CallbackListener> listeners){
        for (CallbackListener listener : listeners) {
            listener.fire();
        }
    }
    
}
