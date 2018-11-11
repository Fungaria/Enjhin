/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import de.fungistudii.enjhin.GameScreen;
import de.fungistudii.enjhin.graphics.VisualComponent.Visual;

/**
 * Container for {@link Visual}s
 * note: has to take care of adding and removal of visuals from renderer (bz of zIndexes)
 * @author Samuel
 */
public class VisualComponent extends Array<Visual> implements Component, Disposable{

    private boolean visible = true;
    
    public VisualComponent(Visual visual){
        super(); 
        add(visual);
    }
    
    public VisualComponent(Visual... visuals){
        super();
        addAll(visuals);
    }

    @Override
    public void dispose() {
        clear();
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            removeIndex(i);
        }
        super.clear();
    }
    
    public static interface Visual{
        public void draw(SpriteBatch batch, Entity entity, float unitscale, float delta);
        public float getZIndex();
        public void setZIndex(float zIndex);
    }

    @Override
    public void add(Visual value) {
        super.add(value);
    }

    @Override
    public Visual removeIndex(int index) {
        Visual visual = super.removeIndex(index);
        return visual;
    }

    //TODO 
    @Override
    public void removeRange(int start, int end) {
        super.removeRange(start, end);
    }
    
    @Override
    public void addAll(Visual... array) {
        for (Visual vis : array) {
            add(vis);
        }
    }

    @Override
    public void addAll(Array<? extends Visual> array) {
        for (Visual visual : array) {
            add(visual);
        }
    }

    @Override
    public void addAll(Array<? extends Visual> array, int start, int count) {
        super.addAll(array, start, count);
    }

    @Override
    public void addAll(Visual[] array, int start, int count) {
        super.addAll(array, start, count);
    }
    
    public void hide(){
        this.visible = false;
    }
    
    public void show(){
        this.visible = true;
    }
    
    public boolean isVisible(){
        return visible;
    }
}
