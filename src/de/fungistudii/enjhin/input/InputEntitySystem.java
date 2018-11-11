package de.fungistudii.enjhin.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.InputProcessor;
import de.fungistudii.enjhin.GameScreen;

/**
 * an {@link EntitySystem} (yay you got an {@link EntitySystem#update(float)} method ) that also provides Input Methods (these get called automaticly, just override)
 *
 * @author sreis
 */
public abstract class InputEntitySystem extends EntitySystem implements InputProcessor {

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.getSystem(InputSystemsNotifier.class).addListener(this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.getSystem(InputSystemsNotifier.class).removeListener(this);
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
