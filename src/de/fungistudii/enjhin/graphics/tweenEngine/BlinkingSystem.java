/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.tweenEngine;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.enjhin.graphics.VisualComponent;
import de.fungistudii.enjhin.graphics.VisualComponent.Visual;
import de.fungistudii.enjhin.graphics.tweenEngine.accessors.SpriteAccessor;

/**
 *
 * @author Samuel
 */
public class BlinkingSystem extends IteratingSystem{
    public static final ComponentMapper<VisualComponent> visualMapper = ComponentMapper.getFor(VisualComponent.class);
    public static final ComponentMapper<BlinkingComponent> blinkingMapper = ComponentMapper.getFor(BlinkingComponent.class);

    private final TweenManager tweenManager;
    
    public BlinkingSystem(TweenManager tweenManager) {
        super(Family.all(VisualComponent.class, BlinkingComponent.class).get());
        this.tweenManager = tweenManager;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BlinkingComponent blinkingComponent = blinkingMapper.get(entity);
        
        if(!blinkingComponent.shouldStart)
            return;
        blinkingComponent.shouldStart = false;
        
        Array<Visual> visuals = visualMapper.get(entity);
        for (Visual visual : visuals) {
            if(visual instanceof Sprite){
                Color visColor = ((Sprite)visual).getColor();
                float speed = blinkingComponent.colorDuration+blinkingComponent.neutralDuration;
                
                Tween to = Tween.set((Sprite)visual, SpriteAccessor.TINT);
                to.target(blinkingComponent.color.r, blinkingComponent.color.g, blinkingComponent.color.b, blinkingComponent.color.a);
                to.repeat(roundOddInteger(blinkingComponent.duration/speed), speed);
                to.start(tweenManager);
                
                Tween back = Tween.set((Sprite)visual, SpriteAccessor.TINT);
                back.target(visColor.r, visColor.g, visColor.b, visColor.a);
                back.delay(blinkingComponent.neutralDuration);
                back.repeat(roundOddInteger(blinkingComponent.duration/speed), speed);
                back.start(tweenManager);
            }
        }
    }
    
    private int roundOddInteger(float value){
        int roundedValue = Math.round(value);
        return ((roundedValue & 1) == 0)? roundedValue-2:roundedValue-1;
    }
}
