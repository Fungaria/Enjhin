/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.visuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import static de.fungistudii.enjhin.box2d.Box2DUtils.height;
import static de.fungistudii.enjhin.box2d.Box2DUtils.minX;
import static de.fungistudii.enjhin.box2d.Box2DUtils.minY;
import static de.fungistudii.enjhin.box2d.Box2DUtils.width;
import de.fungistudii.enjhin.box2d.PhysicsBodyComponent;
import de.fungistudii.enjhin.utils.Align;
import de.fungistudii.enjhin.utils.GraphicsUtils;

/**
 *
 * @author sreis
 */
public class B2dText extends TextVisual{ 
    
    //tmp Stuff
    private final GraphicsUtils.BodyTransform tmpTransform = new GraphicsUtils.BodyTransform();
    private final Vector2 tmpVec2 = new Vector2();
    private static final ComponentMapper<PhysicsBodyComponent> physicsMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
    
    private boolean rotateWithBody = false;

    public B2dText(String text, String fontFile, Color fntColor) {
        super(text, fontFile, fntColor);
        setAlign(Align.right, Align.top);
    }

    @Override
    public void draw(SpriteBatch batch, Entity entity, float unitscale, float delta){
        GraphicsUtils.BodyTransform bodyTransform = retrieveBodyTransform(entity);
        
        // ---ROTATION---
        float rotation = getRotation();
        if(rotateWithBody)
            rotation += bodyTransform.rotation*MathUtils.radDeg;
        
        // ---BODY---
        float bodyX = bodyTransform.position.x;
        float bodyY = bodyTransform.position.y;
        
        // ---ALIGN---
        float alignOffX = GraphicsUtils.BoxAlignX   (   
                                            getAlignX(), 
                                            bodyTransform.width,
                                            layout.width
                                                    );
        
        float alignOffY =  GraphicsUtils.BoxAlignY  (
                                            getAlignY(), 
                                            bodyTransform.height,
                                            layout.height
                                                    );
        
        getData().setScale(getScale()/unitscale);
        layout.setText(this, getText());
        
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        super.draw(batch, layout, getX()+alignOffX+bodyX, getY()+alignOffY+layout.height+bodyY);
        batch.setColor(Color.WHITE);
    }
    
    private GraphicsUtils.BodyTransform retrieveBodyTransform(Entity entity){
        //retrieve body
        PhysicsBodyComponent physics = physicsMapper.get(entity);
        
        if(physics == null){
            throw new RuntimeException("drawing b2dSprite in entity whithout physicsComponent");
        }
        
        Body body = physics.body;
        
        //Set Body Transform
        tmpTransform.width = width(body);
        tmpTransform.height = height(body);
        
        tmpTransform.position.set(minX(body) + tmpTransform.width / 2, minY(body) + tmpTransform.height / 2);
        tmpTransform.position.set(body.getWorldPoint(tmpTransform.position));
        
        tmpTransform.rotation=body.getAngle();
        
        return tmpTransform;
    }
    
    /** offset roation by bodys orientation*/
    public void rotateWithBody(){
        this.rotateWithBody = true;
    }
    /** only use this {@link #getRotation()}*/
    public void fixRotation(){
        this.rotateWithBody = false;
    }
}
