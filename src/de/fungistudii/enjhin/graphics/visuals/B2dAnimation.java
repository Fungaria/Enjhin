/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.visuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.enjhin.box2d.PhysicsBodyComponent;
import de.fungistudii.enjhin.graphics.assets.SpriteData;

/**
 * Animation that draws itself into the Body of the {@link PhysicsBodyComponent} of this {@link Entity} (which means it has to have one ... ;))
 * auto draws itself (using {@link GameScreen}s {@link RenderSystem})
 * @author Samuel
 */
public class B2dAnimation extends B2dSprite{
    private Animation<TextureRegion> animation;
    private final Array<SpriteData> datas = new Array<SpriteData>();
    
    private float animation_time;
    private boolean isPlaying = true;
    
    public B2dAnimation(float fps, Array<? extends TextureRegion> regions, Animation.PlayMode playMode) {
        animation = new Animation(fps, regions, playMode);
        setRegion(regions.first());
    }
    public B2dAnimation(float fps, Array<? extends TextureRegion> regions, Array<SpriteData> datas, Animation.PlayMode playMode) {
        this(fps, regions, playMode);
        this.datas.addAll(datas);
        setData(datas.first());
    }
    
    private void update(){
        if(getDatas().size == 0){
            throw new RuntimeException("datas is empty. your fault? idk"); 
        }
        SpriteData data = datas.get(this.animation.getKeyFrameIndex(animation_time));
        
        super.setData(data);
        super.setRegion(animation.getKeyFrame(animation_time));
        
        super.setSize(getRegionWidth(), getRegionHeight());
    }
    @Override
    public void draw(SpriteBatch batch, Entity entity, float unitscale, float delta) {
        if(super.getTexture()==null){
            throw new RuntimeException("textureRegion is null. your fault? idk"); 
        }
        
        if(isPlaying){
            animation_time += delta;
            update();
        }
        super.draw(batch, entity, unitscale, delta);
    }
    
    public Array<SpriteData> getDatas() {
        return datas;
    }
    public void restart(){
        animation_time = 0;
    }
    public int getKeyFrameIndex(){
        return animation.getKeyFrameIndex(animation_time);
    }
    public boolean isPlaying(){
        return !isPlaying;
    }
    public void setPlaying(boolean playing){
        isPlaying = !playing;
    }
    public void setFps(float fps){
        animation.setFrameDuration(1/fps);
    }
    public float getFps(){
        return 1/animation.getFrameDuration();
    }
    public void setFrameDuration(float frameDuration) {
        animation.setFrameDuration(frameDuration);
    }
    public float getFrameDuration() {
        
        return animation.getFrameDuration();
    }
    public Animation.PlayMode getPlayMode() {
        return animation.getPlayMode();
    }
    public void setPlayMode(Animation.PlayMode playMode) {
        animation.setPlayMode(playMode);
    }
}
