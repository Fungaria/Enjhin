/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import box2dLight.PositionalLight;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.fungistudii.enjhin.GameScreen;
import de.fungistudii.enjhin.box2d.B2dSystem;
import de.fungistudii.enjhin.graphics.VisualComponent.Visual;
import de.fungistudii.enjhin.graphics.tweenEngine.accessors.CamAccessor;
import de.fungistudii.enjhin.graphics.visuals.ParticleEffectVisual;
import de.fungistudii.enjhin.graphics.tweenEngine.accessors.LightAccessor;
import de.fungistudii.enjhin.graphics.tweenEngine.accessors.ParticleEffectAccessor;
import de.fungistudii.enjhin.graphics.tweenEngine.accessors.SpriteAccessor;
import de.fungistudii.enjhin.graphics.tweenEngine.accessors.TextAccessor;
import de.fungistudii.enjhin.graphics.tweenEngine.accessors.VisualAccessor;
import de.fungistudii.enjhin.graphics.visuals.TextVisual;
import de.fungistudii.enjhin.view.EffectCam;

/**
 * Handles a {@link RenderSystem}, {@link Box2DDebugRenderer}, {@link Stage},
 * and a {@link TweenManager}. Draws all your stuff
 *
 * @author sreis
 */
public class VisualSystem extends EntitySystem {

    public RenderSystem renderSystem;
    public final SpriteBatch batch;
    public Stage stage;

    public EffectCam camera;
    public Viewport viewport;

    /**
     * this has to be seperated since it holds its own internal SpriteBatch (so
     * it cant be rendered inside of batch.begin and batch.end)
     */
    public Box2DDebugRenderer debugRenderer;
    public boolean drawDebug = true;

    public Color clearColor = new Color(0.5f, 0.5f, 0.5f, 1);

    public TweenManager tweenManager;

    public ShapeRenderer renderer;

    private final B2dSystem box2d;
    
    public VisualSystem(B2dSystem b2d) {
        super(100);
        
        this.box2d = b2d;
        this.batch = new SpriteBatch();
        this.debugRenderer = new Box2DDebugRenderer();
        this.renderSystem = new RenderSystem();

        this.camera = new EffectCam();
        this.viewport = new FillViewport(10, 10, camera);
        viewport.apply();


        this.stage = new Stage(viewport, new SpriteBatch());

        this.tweenManager = new TweenManager();
        registerTweenAccessors();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addSystem(renderSystem);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        camera.update();

        tweenManager.update(deltaTime);
        drawVisuals(deltaTime);
        drawDebug();

        stage.act();
        stage.draw();
    }

    private void drawVisuals(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        renderSystem.draw(batch, deltaTime);
        batch.end();
        box2d.rayHandler.setCombinedMatrix(camera);
        box2d.rayHandler.render();
    }

    private void drawDebug() {
        if (drawDebug == true) {
            debugRenderer.render(box2d.world, camera.combined);
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    private void registerTweenAccessors() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(PositionalLight.class, new LightAccessor());
        Tween.registerAccessor(ParticleEffectVisual.class, new ParticleEffectAccessor());
        Tween.registerAccessor(Visual.class, new VisualAccessor());
        Tween.registerAccessor(OrthographicCamera.class, new CamAccessor());
        Tween.registerAccessor(TextVisual.class, new TextAccessor());
    }
}
