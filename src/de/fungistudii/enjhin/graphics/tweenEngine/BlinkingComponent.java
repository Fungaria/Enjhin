/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.tweenEngine;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

/**
 *
 * @author Samuel
 */
public class BlinkingComponent implements Component{
    public Color color;
    public float duration = 10;
    public float colorDuration;
    public float neutralDuration;

    public Tween to;
    public Tween back;
    
    public boolean shouldStart;
    
    public BlinkingComponent(Color color, float duration, float speed) {
        this(color, duration, speed, speed);
    }
    
    public BlinkingComponent(Color color, float duration, float colorDuration, float neutralDuration) {
        this.color = color;
        this.duration = duration;
        this.neutralDuration = neutralDuration;
        this.colorDuration = colorDuration;
    }
    
    public void start(){
        shouldStart = true;
    }
}
