package de.fungistudii.enjhin.graphics.tweenEngine.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import de.fungistudii.enjhin.graphics.visuals.ParticleEffectVisual;

/**
 *
 * @author sreis
 */
public class ParticleEffectAccessor implements TweenAccessor<ParticleEffectVisual>{
    public static final int ALPHA = 0;
    public static final int TINT = 1;
    public static final int SCALE = 3;
    public static final int POSITION_X = 6;
    public static final int POSITION_Y = 7;
    
    
    @Override
    public int getValues(ParticleEffectVisual target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case ALPHA:
                returnValues[0] = target.getAlpha();
                return 1;
            case SCALE:
                returnValues[0] = target.getScale();
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
    public void setValues(ParticleEffectVisual target, int tweenType, float[] newValues) {
        switch(tweenType){
            case ALPHA:
                target.setAlpha(newValues[0]);
                break;
            case POSITION_X:
                target.setPosition(newValues[0], target.getY());
                break;
            case POSITION_Y:
                target.setPosition(target.getX(), newValues[0]);
                break;
            case SCALE:
                target.setScale(newValues[0]);
                break;
            case TINT:
                target.setColor(newValues[0], newValues[1], newValues[2], newValues[3]);
                break;
            default:
                assert false;
        }
    }
}
