package de.fungistudii.enjhin.graphics.visuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import static de.fungistudii.enjhin.box2d.Box2DUtils.height;
import static de.fungistudii.enjhin.box2d.Box2DUtils.minX;
import static de.fungistudii.enjhin.box2d.Box2DUtils.minY;
import static de.fungistudii.enjhin.box2d.Box2DUtils.width;
import de.fungistudii.enjhin.box2d.PhysicsBodyComponent;
import de.fungistudii.enjhin.graphics.VisualComponent;
import de.fungistudii.enjhin.graphics.assets.SpriteData;
import de.fungistudii.enjhin.utils.Align;
import de.fungistudii.enjhin.utils.GraphicsUtils;

/**
 * Sprite that draws itself into the Body of the {@link PhysicsBodyComponent} of
 * this {@link Entity} (which means it has to have one ... ;)) auto draws itself
 * (using {@link GameScreen}s {@link RenderSystem})
 *
 * @author Samuel
 */
public class B2dSprite extends Sprite implements VisualComponent.Visual, Alignable {

    //tmp Stuff
    private final GraphicsUtils.BodyTransform tmpTransform = new GraphicsUtils.BodyTransform();
    private final Vector2 tmpVec2 = new Vector2();
    private static final ComponentMapper<PhysicsBodyComponent> physicsMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);

    private float zIndex;
    private SpriteData data;
    private Align alignX = Align.left;
    private Align alignY = Align.bottom;

    private boolean rotateWithBody = false;
    private boolean alignInFixture = false;

    @Override
    public void draw(SpriteBatch batch, Entity entity, float unitscale, float delta) {
        GraphicsUtils.BodyTransform bodyTransform = retrieveBodyTransform(entity);

        // ---ROTATION---
        float rotation = getRotation();
        if (rotateWithBody) {
            rotation += bodyTransform.rotation * MathUtils.radDeg;
        }

        // ---BODY---
        float bodyX = bodyTransform.position.x;
        float bodyY = bodyTransform.position.y;

        // ---CROP---
        float cropOffX = data.leftOffsetX * getScaleX() / unitscale;
        float cropOffY = data.bottomOffsetY * getScaleY() / unitscale;

        // ---ALIGN---
        float alignOffX = GraphicsUtils.BoxAlignX(
                getAlignX(),
                bodyTransform.width * unitscale,
                getData().actualWidth * getScaleX()
        ) / unitscale;

        float alignOffY = GraphicsUtils.BoxAlignY(
                getAlignY(),
                bodyTransform.height * unitscale,
                getData().actualHeight * getScaleY()
        ) / unitscale;

        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        //Scale uses origin, size not: like this origin will only affect rotation not the scaling (otherwise scaling would lead to weired offsets)
        batch.draw(this,
                //Position
                getX() + bodyX + alignOffX + cropOffX,
                getY() + bodyY + alignOffY + cropOffY,
                //Origin
                -(alignOffX + cropOffX),
                -(alignOffY + cropOffY),
                //Size
                super.getWidth() * getScaleX() / unitscale,
                super.getHeight() * getScaleY() / unitscale,
                //Scale
                1,
                1,
                //Rotation
                rotation
        );
        batch.setColor(Color.WHITE);
    }

    private GraphicsUtils.BodyTransform retrieveBodyTransform(Entity entity) {
        //retrieve body
        PhysicsBodyComponent physics = physicsMapper.get(entity);

        if (physics == null) {
            throw new RuntimeException("drawing b2dSprite in entity whithout physicsComponent");
        }

        Body body = physics.body;

        //Set Body Transform
        tmpTransform.width = width(body);
        tmpTransform.height = height(body);

        if(alignInFixture){
            tmpTransform.position.set(minX(body) + tmpTransform.width / 2, minY(body) + tmpTransform.height / 2);
            tmpTransform.position.set(body.getWorldPoint(tmpTransform.position));
        }else{
            tmpTransform.position.set(body.getPosition());
        }

        tmpTransform.rotation = body.getAngle();

        return tmpTransform;
    }

    public B2dSprite() {
    }

    public B2dSprite(Texture texture) {
        super(texture);
        data = new SpriteData(texture.getWidth(), texture.getHeight());
    }

    public B2dSprite(Texture texture, int srcWidth, int srcHeight) {
        super(texture, srcWidth, srcHeight);
        data = new SpriteData(texture.getWidth(), texture.getHeight());
    }

    public B2dSprite(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(texture, srcX, srcY, srcWidth, srcHeight);
        data = new SpriteData(texture.getWidth(), texture.getHeight());
    }

    public B2dSprite(TextureRegion region) {
        super(region);
        data = new SpriteData(region.getRegionWidth(), region.getRegionHeight());
    }

    public B2dSprite(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(region, srcX, srcY, srcWidth, srcHeight);
        data = new SpriteData(region.getRegionWidth(), region.getRegionHeight());
    }

    public B2dSprite(Sprite sprite) {
        super(sprite);
        data = new SpriteData(sprite.getRegionWidth(), sprite.getRegionHeight());
    }

    public SpriteData getData() {
        return data;
    }

    public void setData(SpriteData data) {
        this.data = data;
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
     * offset roation by bodys orientation
     */
    public void rotateWithBody() {
        this.rotateWithBody = true;
    }
    
    public void alignInFixture(){
        this.alignInFixture = true;
    }
    
    public void alignInPosition(){
        this.alignInFixture = false;
    }
    
    

    /**
     * only use this {@link #getRotation()}
     */
    public void fixRotation() {
        this.rotateWithBody = false;
    }
}
