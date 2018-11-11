/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.visuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.fungistudii.enjhin.GameScreen;
import de.fungistudii.enjhin.utils.Align;
import de.fungistudii.enjhin.utils.GraphicsUtils;
import de.fungistudii.enjhin.graphics.VisualComponent;
import de.fungistudii.enjhin.graphics.assets.SpriteData;

/**
 * a spimple {@link Sprite} auto draws itself (using {@link GameScreen}s
 * {@link RenderSystem})
 *
 * @author Samuel
 */
public class SpriteVisual extends Sprite implements VisualComponent.Visual, Alignable {

    private float zIndex;
    private Align alignX = Align.left;
    private Align alignY = Align.bottom;
    private SpriteData data;

    private boolean useOrigin = false;

    public SpriteVisual() {
    }

    public SpriteVisual(Texture texture) {
        super(texture);
        data = new SpriteData(texture.getWidth(), texture.getHeight());
    }

    public SpriteVisual(Texture texture, int srcWidth, int srcHeight) {
        super(texture, srcWidth, srcHeight);
        data = new SpriteData(texture.getWidth(), texture.getHeight());
    }

    public SpriteVisual(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        data = new SpriteData(texture.getWidth(), texture.getHeight());
    }

    public SpriteVisual(TextureRegion region) {
        super(region);
        data = new SpriteData(region.getRegionWidth(), region.getRegionHeight());
    }

    public SpriteVisual(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(region, srcX, srcY, srcWidth, srcHeight);
        data = new SpriteData(region.getRegionWidth(), region.getRegionHeight());
    }

    public SpriteVisual(Sprite sprite) {
        super(sprite);
        data = new SpriteData(sprite.getWidth(), sprite.getHeight());
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

    /**
     * Sprite will rotate around the sprites position (using the Align as the
     * Origin)
     */
    public void rotateAroundPosition() {
        useOrigin = false;
    }

    /**
     * using the origin defined by {@link SpriteVisual#setOrigin(float, float)}
     * to rotate this Sprite
     * ({@link SpriteVisual#rotateAroundPosition() is safer})
     */
    public void rotateAroundOrigin() {
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

    public SpriteData getData() {
        return data;
    }

    public void setData(SpriteData data) {
        this.data = data;
    }

    @Override
    public void draw(SpriteBatch batch, Entity entity, float u, float delta) {
        float unitscale = 1 / u;

        float offsetX = 0;
        float offsetY = 0;

        // ---ALIGN---
        offsetX += GraphicsUtils.boxInPointAlignX(alignX, data.actualWidth) * unitscale * getScaleX();
        offsetY += GraphicsUtils.boxInPointAlignY(alignY, data.actualHeight) * unitscale * getScaleY();

        // ---CROP---
        offsetX += data.leftOffsetX * unitscale * getScaleX();
        offsetY += data.bottomOffsetY * unitscale * getScaleY();

        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);

        //Scale uses origin, size not: like this origin will only affect rotation not the scaling (otherwise scaling would lead to weired offsets)
        batch.draw(this,
                //Position
                getX() + offsetX,
                getY() + offsetY,
                //Origin
                useOrigin ? getOriginX() : -offsetX,
                useOrigin ? getOriginY() : -offsetY,
                //Size
                super.getWidth() * getScaleX() * unitscale,
                super.getHeight() * getScaleY() * unitscale,
                //Scale
                1,
                1,
                //Rotation
                getRotation()
        );
        batch.setColor(Color.WHITE);
    }

    /**
     *@returns width in pixels (influenced by scaling)
     *to get the width in meters just multiply by UNITSCALE
     */
    @Override
    public float getWidth() {
        return data.actualWidth * getScaleX();
    }

    /**
     * @returns height in pixels (influenced by scaling) to get the height in
     * meters just multiply by UNITSCALE
     */
    @Override
    public float getHeight() {
        return data.actualHeight * getScaleY();
    }
}
