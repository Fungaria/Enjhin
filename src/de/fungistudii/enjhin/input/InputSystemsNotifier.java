package de.fungistudii.enjhin.input;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.enjhin.box2d.collisionSystems.CollisionSystemsNotifier;

/**
 * Big Brother of the whole Input System: detects Iput and forwards them
 * to every {@link InputProcessor} (aka every the Input System) 
 * should only be used by {@link InputSystem}
 * @see CollisionSystemsNotifier
 * @author Samuel
 */
public class InputSystemsNotifier extends EntitySystem implements InputProcessor{
    
    private Array<InputProcessor> listeners;
    
    public InputSystemsNotifier(InputMultiplexer inputMultiplexer){
        inputMultiplexer.addProcessor(this);
        listeners = new Array<InputProcessor>();
    }
    
    @Override
    public boolean keyDown(int i) {
        for (InputProcessor listener : listeners) {
            listener.keyDown(i);
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        for (InputProcessor listener : listeners) {
            listener.keyUp(i);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        for (InputProcessor listener : listeners) {
            listener.keyTyped(c);
        }
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        for (InputProcessor listener : listeners) {
            listener.touchDown(i, i1, i2, i3);
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        for (InputProcessor listener : listeners) {
            listener.touchUp(i, i1, i2, i3);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        for (InputProcessor listener : listeners) {
            listener.touchDragged(i, i1, i2);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        for (InputProcessor listener : listeners) {
            listener.mouseMoved(i, i1);
        }
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        for (InputProcessor listener : listeners) {
            listener.scrolled(i);
        }
        return false;
    }
    
    public void addListener(InputProcessor listener){
        listeners.add(listener);
    }
    public void removeListener(InputProcessor listener){
        listeners.removeValue(listener, true);
    }
}
