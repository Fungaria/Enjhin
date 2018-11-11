/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.tweenEngine.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import static de.fungistudii.enjhin.graphics.tweenEngine.accessors.SpriteAccessor.ALPHA;

/**
 *
 * @author sreis
 */
public class CamAccessor implements TweenAccessor<OrthographicCamera>{

    public static final int ZOOM=0;
    
    @Override
    public int getValues(OrthographicCamera target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case ZOOM:
                returnValues[0] = target.zoom;
                return 1;
            default: assert false;
        }
        return -1;
    }

    @Override
    public void setValues(OrthographicCamera target, int tweenType, float[] newValues) {
        switch(tweenType){
            case ZOOM:
                target.zoom = newValues[0];
                break;
        }
    }
    
}
