package de.fungistudii.enjhin.graphics.visuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fungistudii.enjhin.graphics.VisualComponent;
import de.fungistudii.enjhin.utils.Align;
import de.fungistudii.enjhin.utils.GraphicsUtils;

/**
 *
 * @author sreis
 */
public class TextVisual extends BitmapFont implements VisualComponent.Visual, Alignable {

    protected float zIndex;
    private Align alignX = Align.left;
    private Align alignY = Align.bottom;

    private float x = 0;
    private float y = 0;
    
    private float originX = 0;
    private float originY = 0;
    
    private float scale = 1;
    
    private float rotation = 0;
    
    protected boolean useOrigin = false;
    
    protected GlyphLayout layout;
    
    private String text;
    
    public TextVisual(String text, String fontFile, Color fntColor) {
        super(Gdx.files.internal(fontFile));
        layout = new GlyphLayout(this, text);
        this.text = text;
        this.setColor(fntColor);
        super.setUseIntegerPositions(false);
    }
    
    @Override
    public void setAlign(Align alignX, Align alignY){
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
    
    /** Sprite will rotate around the sprites position (using the Align as the Origin)*/
    public void rotateAroundPosition(){
        useOrigin = false;
    }
    
    /** using the origin defined by {@link SpriteVisual#setOrigin(float, float) to rotate this Sprite} ({@link SpriteVisual#rotateAroundPosition() is safer})*/
    public void rotateAroundOrigin(){
        useOrigin = true;
    }

    @Override
    public void setZIndex(float zIndex) {
        this.zIndex = zIndex;
    }
    
    @Override
    public float getZIndex() {
        return zIndex;
    }
    
    @Override
    public void draw(SpriteBatch batch, Entity entity, float unitscale, float delta) {
        
        // ---ALIGN---
        float offsetX = GraphicsUtils.boxInPointAlignX(alignX, 0)/unitscale*getScaleX();
        float offsetY = GraphicsUtils.boxInPointAlignY(alignY, 0)/unitscale*getScaleY();
        
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        //Scale uses origin, size not: like this origin will only affect rotation not the scaling (otherwise scaling would lead to weired offsets)
        super.draw(
                batch, 
                text, 
                x+offsetX, 
                y+offsetY
                );
        batch.setColor(Color.WHITE);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scaleXY){
        this.scale = scaleXY;
    }
    
    public boolean isUseOrigin() {
        return useOrigin;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setPosition(float newValue, float y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
