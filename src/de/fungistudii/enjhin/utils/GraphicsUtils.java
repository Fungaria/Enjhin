/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import static de.fungistudii.enjhin.utils.Align.*;
import de.fungistudii.enjhin.graphics.visuals.B2dSprite;
import de.fungistudii.enjhin.graphics.visuals.SpriteVisual;

/**
 * REQUIRED
 * @author Samuel
 */
public class GraphicsUtils {
    
    public static class BodyTransform{

        public BodyTransform() {
        }
        public Vector2 position = new Vector2();
        public float width;
        public float height;
        /**in RADIANS (use {@link com.badlogic.gdx.math.MathUtils#radDeg} to convert to DEGREE)*/
        public float rotation;
    }
    
    public static void setAtlasFilter(TextureAtlas atlas, Texture.TextureFilter minFilter, Texture.TextureFilter magFilter){
        for (Texture texture : atlas.getTextures()) {
            texture.setFilter(minFilter, magFilter);
        }
    }
    
    /**@return the offset to align a Box (Sprite) in the left, center, or right side of another Box (Body)*/
    public static float BoxAlignX(Align alignX, float bodyWidth, float spriteWidth){
        float offsetX=0;
        switch(alignX){
            case center:
                //moving spritesWidth/2 to the left
                offsetX -= spriteWidth/2f;
                break;
            case right:
                //moving it first to the right so its left side is aligned with the right side of the body
                //and then by spritesWidth/2 back to the left
                offsetX += (bodyWidth/2f)-spriteWidth;
                break;
            case left:
                //moving it by bodysWidth to the left
                offsetX -= bodyWidth/2f;
                
                break;
            default:
                //using top or bottom in alignX leads to Illuminati
                System.err.println(B2dSprite.class+".alignX cannot be "+alignX);
        }
        return offsetX;
    }
    /**@return the offset to align a Box (Sprite) in the bottom, center, or top of another Box (Body)*/
    public static float BoxAlignY(Align alignY, float bodyHeight, float spriteHeight){
        float offsetY=0;
        switch(alignY){
            case center:
                //moving by spritesWidth/2 to the left
                offsetY -= spriteHeight/2;
                break;
            case top:
                //moving it first to the right so its left side is aligned with the right side of the body
                //and then by spritesWidth/2 back to the left
                offsetY += (bodyHeight/2f) - spriteHeight;
                break;
            case bottom:
                //moving it by bodysWidth/2 to the left
                offsetY -= bodyHeight/2f;
                break;
            default:
                //using top or bottom in alignX leads to Illuminati
                System.err.println(B2dSprite.class+".alignX cannot be "+alignY);
        }
        return offsetY;
    }
    
    
    /**@return the offset to align a Point (ParticleEmitter) in the left, center, or right side of another Box (Body)*/
    public static float pointInBoxAlignX(Align alignX, float box2Width){
        float offsetX=0;
        switch(alignX){
            case center:
                //already centerd by default
                break;
            case right:
                //moving it by bodysWith/2 to the right
                offsetX += (box2Width/2f);
                break;
            case left:
                //moving it by bodysWith/2 to the left
                offsetX -= box2Width/2f;
                break;
            default:
                //using top or bottom in alignX leads to Illuminaten
                System.err.println(B2dSprite.class+".alignX cannot be "+alignX);
        }
        return offsetX;
    }
    /**@return the offset to align a Point (ParticleEmitter) in the bottom, center, or top of another Box (Body)*/
    public static float pointInBoxAlignY(Align alignY, float box2Height){
        float offsetY = 0;
        switch(alignY){
            case center:
                break;
            case top:
                //moving it up by BodysHeight/2
                offsetY += box2Height/2f;
                break;
            case bottom:
                //moving it down by BodysHeight/2
                offsetY -= box2Height/2f;
                break;
            default:
                //using left or right in alignY leads to Illuminaten too
                System.err.println(B2dSprite.class+".alignY cannot be "+alignY);
        }
        return offsetY;
    }
    
    /**@return the offset to align a Box (Sprite) in the left, center, or right side of a point(Position)*/
    public static float boxInPointAlignX(Align align, float boxWidth) {
        switch (align) {
            case left:
                //already right by default
                return 0;
            case right:
                return -boxWidth;
            case center:
                return -boxWidth / 2;
            default:
                throw new RuntimeException(".alignX cannot be " + align);
        }
    }

    /**@return the offset to align a Box (Sprite) in the bottom, center, or top of a point(Position)*/
    public static float boxInPointAlignY(Align align, float spriteHeight) {
        switch (align) {
            case bottom:
                //already top by default
                return 0;
            case top:
                return -spriteHeight;
            case center:
                return -spriteHeight / 2;
            default:
                throw new RuntimeException(".alignY cannot be " + align);
        }
    }
    private static final Vector3 tmpVec3=new Vector3();
    private static final Vector2 tmpVec2=new Vector2();
    
    public static Vector2 getScreenAlign(Align alignX, Align alignY, Camera camera){
        tmpVec3.set(0, 0, 0);
        switch(alignX){
            case left:
                break;
            case right:
                tmpVec3.add(Gdx.graphics.getWidth(), 0, 0);
                break;
            case center:
                tmpVec3.add(Gdx.graphics.getWidth()/2f, 0, 0);
                break;
        }
        switch(alignY){
            case top:
                break;
            case bottom:
                tmpVec3.add(0, Gdx.graphics.getHeight(), 0);
                break;
            case center:
                tmpVec3.add(0, Gdx.graphics.getHeight()/2f, 0);
                break;
        }
        
        tmpVec3.set(camera.unproject(tmpVec3));
        return tmpVec2.set(tmpVec3.x, tmpVec3.y);
    }
}
