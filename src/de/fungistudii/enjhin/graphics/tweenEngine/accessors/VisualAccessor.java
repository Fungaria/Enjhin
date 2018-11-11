/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.tweenEngine.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.fungistudii.enjhin.graphics.VisualComponent.Visual;

/**
 *
 * @author sreis
 */
public class VisualAccessor implements TweenAccessor<Visual>{
    private final SpriteAccessor spriteAccessor;
    private final ParticleEffectAccessor particleAccessor;

    public VisualAccessor() {
        spriteAccessor = new SpriteAccessor();
        particleAccessor = new ParticleEffectAccessor();
    }
    
    @Override
    public int getValues(Visual target, int tweenType, float[] returnValues) {
        if(target instanceof Sprite)
            return spriteAccessor.getValues((Sprite)target, tweenType, returnValues);
        
        return 0;
    }

    @Override
    public void setValues(Visual target, int tweenType, float[] newValues) {
        if(target instanceof Sprite)
            spriteAccessor.setValues((Sprite)target, tweenType, newValues);
    }
}
