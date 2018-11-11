/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * a {@link OrthographicCamera} but with aditional stuff 
 * @author Samuel
 */
public class EffectCam extends OrthographicCamera{

    private Body bodyToFollow;
    private Vector3 positionToFollow = new Vector3();
    private float smooth = 0;
    
    public EffectCam() {
    }

    public EffectCam(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }
    
    public void attachToBody(Body body){
        attachToBody(body, smooth);
    }
    
    public void attachToBody(Body body, float smooth){
        bodyToFollow = body;
        this.smooth = smooth;
    }
    
    public void setSmooth(float smooth){
        this.smooth = smooth;
    }
    public float getSmooth(){
        return smooth;
    }
    
    @Override
    public void update(boolean updateFrustum) {
        super.update(updateFrustum);
        
        if(bodyToFollow != null){
            positionToFollow.set(bodyToFollow.getPosition(), position.z);
            this.position.lerp(positionToFollow, smooth);
        }
    }
}
