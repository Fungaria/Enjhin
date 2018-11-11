/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.tweenEngine.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import box2dLight.PositionalLight;

/**
 *
 * @author sreis
 */
public class LightAccessor implements TweenAccessor<PositionalLight>{
    
    public static final int ALPHA = 0;
    public static final int TINT = 1;
    public static final int DISTANCE = 3;
    public static final int SCALE_X = 4;
    public static final int SCALE_Y = 5;
    public static final int POSITION_X = 6;
    public static final int POSITION_Y = 7;
    
    @Override
    public int getValues(PositionalLight target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            case DISTANCE:
                returnValues[0] = target.getDistance();
                return 1;
            case POSITION_X:
                returnValues[0] = target.getX();
                return 1;
            case POSITION_Y:
                returnValues[0] = target.getY();
                return 1;
            case TINT:
                returnValues[0] = target.getColor().r;
                returnValues[1] = target.getColor().g;
                returnValues[2] = target.getColor().b;
                returnValues[3] = target.getColor().a;
                return 4;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(PositionalLight target, int tweenType, float[] newValues) {
        switch(tweenType){
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            case POSITION_X:
                target.setPosition(newValues[0], target.getY());
                break;
            case POSITION_Y:
                target.setPosition(target.getX(), newValues[0]);
                break;
            case DISTANCE:
                target.setDistance(newValues[0]);
                break;
            case TINT:
                target.setColor(newValues[0], newValues[1], newValues[2], newValues[3]);
                break;
            default:
                assert false;
        }
    }
}
