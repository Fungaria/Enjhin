/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.visuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.badlogic.gdx.math.MathUtils.radDeg;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import static de.fungistudii.enjhin.box2d.Box2DUtils.height;
import static de.fungistudii.enjhin.box2d.Box2DUtils.minX;
import static de.fungistudii.enjhin.box2d.Box2DUtils.minY;
import static de.fungistudii.enjhin.box2d.Box2DUtils.width;
import de.fungistudii.enjhin.box2d.PhysicsBodyComponent;
import de.fungistudii.enjhin.utils.GraphicsUtils;
import de.fungistudii.enjhin.graphics.VisualComponent;
import de.fungistudii.enjhin.utils.Align;
import de.fungistudii.enjhin.utils.GeometryUtils;

/**
 * {@link ParticleEffect} that draws itself into the Body of the {@link PhysicsBodyComponent} of this {@link Entity} (which means it has to have one ... ;))
 * alignment: Emitter center around bodys AABB (not inside of the Body)
 * auto draws itself (using {@link GameScreen}s {@link RenderSystem})
 * 
 * TODO add unitscale
 * @author Samuel
 */
public class B2dParticleEffect extends ParticleEffectVisual implements VisualComponent.Visual, Alignable{

    private static final ComponentMapper<PhysicsBodyComponent> physicsMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
    
    private final GraphicsUtils.BodyTransform tmpTransform = new GraphicsUtils.BodyTransform();
    private final Vector2 tmpVec2 = new Vector2();
    
    
    private Align alignX = Align.left;
    private Align alignY = Align.bottom;
    
    private boolean rotateWithBody = false;
    
    @Override
    public void draw(SpriteBatch batch, Entity entity, float unitscale, float delta) {
        PhysicsBodyComponent physics = physicsMapper.get(entity);
        if(physics == null){
            throw new RuntimeException("drawing b2dParticleEffect in entity whithout physicsComponent");
        }
        Body body = physics.body;
        
        tmpTransform.width = width(body);
        tmpTransform.height = height(body);
        
        tmpTransform.position.set(minX(body) + tmpTransform.width / 2, minY(body) + tmpTransform.height / 2);
        tmpTransform.position.set(body.getWorldPoint(tmpTransform.position));
        
        tmpTransform.rotation=body.getAngle();
        
        drawParticleOnBody(body, tmpTransform, batch, delta);
    }
    
        @Override
    public void setAlign(Align alignX, Align alignY) {
        setAlignX(alignX);
        setAlignY(alignY);
    }

    @Override
    public void setAlignX(Align alignX) {
        this.alignX = alignX;
    }

    @Override
    public Align getAlignX() {
        return alignX;
    }

    @Override
    public Align getAlignY() {
        return alignY;
    }

    @Override
    public void setAlignY(Align alignY) {
        this.alignY = alignY;
    }
    
    private final Vector2 off = new Vector2();
    private void drawParticleOnBody(Body body, GraphicsUtils.BodyTransform transform, SpriteBatch batch, float delta){
        off.set(getX(), getY());
        //align offset
        tmpVec2.set(GraphicsUtils.pointInBoxAlignX(getAlignX(), transform.width), GraphicsUtils.pointInBoxAlignY(getAlignY(), transform.height));
        tmpVec2.add(getX(), getY());
        
        tmpVec2.set(GeometryUtils.rotate(tmpVec2, Vector2.Zero, transform.rotation));
        
        tmpVec2.add(transform.position);
        
        super.setPosition(tmpVec2.x, tmpVec2.y);
        super.draw(batch, delta);
        super.setPosition(off.x, off.y);
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
