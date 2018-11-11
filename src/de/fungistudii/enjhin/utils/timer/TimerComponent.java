/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.utils.timer;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Samuel
 */
public class TimerComponent extends Array<TimerComponent.CallbackListener> implements Component{
    
    public float duration;
    
    public boolean timing;
    public float time;
    
    public void time(float duration){
        this.duration = duration;
        this.time();
    }
    
    public void time(){
        timing = true;
        time = 0;
    }
    
    public static interface CallbackListener{
        public void fire();
    }
}
