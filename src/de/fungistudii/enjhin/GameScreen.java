/*
 * Samuel hasafunden. Deshalb:
 * alle rechte gehören ganz alleine mia.
 * wer gegen die obige Lizensvereinbarung verstöst wird mit 
 * sofortiger wirkung defenestriert!
 */
package de.fungistudii.enjhin;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import de.fungistudii.enjhin.box2d.B2dSystem;
import de.fungistudii.enjhin.graphics.VisualSystem;
import de.fungistudii.enjhin.input.InputSystem;
import de.fungistudii.enjhin.utils.update.UpdateSystem;

/**
 * Main class thinggy of Enjhin (#enjhin4everenjhin4life)
 * @author sreiser
 */
public class GameScreen implements Screen{

    public Engine engine;
    public B2dSystem box2d;
    public VisualSystem visual;
    public InputSystem input;

    public GameScreen(Engine engine) {
        this.engine = engine;
    }

    public GameScreen() {
        engine = new Engine();
    }
    
    /**
     * clears the screen and updates the camera.
     * @see Screen#render(float)
     */
    @Override
    public void render(float delta) {
        engine.update(delta);
    }
    
    /**
     * @see Screen#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        visual.resize(width, height);
    }

    /**
     * must be called
     * @see Screen#show() 
     */
    @Override
    public void show() {
        initializeSystems();
    }
    
    private void initializeSystems(){
        
        box2d = new B2dSystem();
        engine.addSystem(box2d);
        
        input = new InputSystem();
        engine.addSystem(input);
        
        visual = new VisualSystem(box2d);
        engine.addSystem(visual);
        
        
        //Tier2
        UpdateSystem updateSystem = new UpdateSystem();
        engine.addSystem(updateSystem);
    }

    /**
     * @see Screen#hide() 
     */
    @Override
    public void hide() {
        dispose();
    }

    /**
     * @see Screen#pause()
     */
    @Override
    public void pause() {
    }

    /**
     * @see Screen#resume() 
     */
    @Override
    public void resume() {
    }

    /**
     * disposes world batch and box2DDegugrenderer
     * @see Screen#dispose()
     */
    @Override
    public void dispose() {
//        DisposeOnCloseSystem disposeSystem = new DisposeOnCloseSystem();
//        disposeSystem.dispose();
        box2d.dispose();
    }
}
