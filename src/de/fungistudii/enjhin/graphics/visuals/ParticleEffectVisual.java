/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.visuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import de.fungistudii.enjhin.GameScreen;
import de.fungistudii.enjhin.graphics.VisualComponent;

/**
 * a simple {@link ParticleEffect}
 * auto draws itself (using {@link GameScreen}s {@link RenderSystem})
 * TODO add unitscale
 * @author Samuel
 */
public class ParticleEffectVisual extends ParticleEffect implements VisualComponent.Visual {

    private float zIndex;
    
    //track along with scaling
    private float scale = 1;
    
    private float maxWidth;
    private float maxHeight;
    
    private final Vector2 position = new Vector2();
    
    private float alpha = 1;
    
    private Color tint;
    
    private float rotation;

    @Override
    public void load(FileHandle effectFile, FileHandle imagesDir) {
        super.load(effectFile, imagesDir);
    }
    @Override
    public void load(FileHandle effectFile, TextureAtlas atlas) {
        super.load(effectFile, atlas);
    }
    @Override
    public void load(FileHandle effectFile, TextureAtlas atlas, String atlasPrefix) {
        super.load(effectFile, atlas, atlasPrefix);
    }

    @Override
    public void draw(SpriteBatch batch, Entity entity, float unitscale, float delta) {
        batch.setColor(tint);
        super.draw(batch, delta);
        batch.setColor(Color.WHITE);
    }

    public void translate(float x, float y){
        position.add(x, y);
        setPosition(position.x, position.y);
    }
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        position.set(x, y);
    }

    public float getX(){
        return position.x;
    }
    
    public float getY(){
        return position.y;
    }
    
    public void setX(float x){
        setPosition(x, position.y);
    }
    
    public void setY(float y){
        setPosition(position.x, y);
    }
    
    @Override
    public void setZIndex(float zIndex) {
        this.zIndex = zIndex;
    }

    @Override
    public float getZIndex() {
        return zIndex;
    }

    public void setAlpha(float alpha){
        this.alpha = alpha;
        for (ParticleEmitter emitter : getEmitters()) {
            emitter.getTransparency().setHigh(alpha);
        }
    }
    
    public float getAlpha(){
        return alpha;
    }
    
    public void setColor(Color color){
        this.tint = color;
    }
    public void setColor(float r, float g, float b, float a){
        this.tint.set(r, g, b, a);
    }
    
    public Color getColor(){
        return tint;
    }
    
    /**only rotates the emission angle: better not use it / rotate stuff in the editor instead
     */
    public void setRotation(float amountInDegrees) {
        for (ParticleEmitter emitter : this.getEmitters()) {
            float diff = amountInDegrees-rotation;
            
            float highMin = emitter.getAngle().getHighMin();
            float highMax = emitter.getAngle().getHighMax();
            float lowMin = emitter.getAngle().getLowMin();
            float lowMax = emitter.getAngle().getLowMax();
            
            highMin += diff;
            highMax += diff;
            lowMin += diff;
            lowMax += diff;
            
            emitter.getAngle().setHigh(highMin, highMax);
            emitter.getAngle().setLow(lowMin, lowMax);
        }
        this.rotation = amountInDegrees;
    }
    public float getRotation() {
        return rotation;
    }
    public void rotate(float amount){
        setRotation(rotation+amount);
    }
    
    @Override
    public void scaleEffect(float scaleFactor) {
        super.scaleEffect(scaleFactor);

        this.scale *= scaleFactor;
    }

    /**(scale/GameScreen.view.UNIT_SCALE) / (this.scale);*/
    public void setScale(float scale) {
        float scaling = (scale) / (this.scale);
        scaleEffect(scaling);
        this.scale = scale;
    }
    
    public float getScale(){
        return scale;
    }
}
