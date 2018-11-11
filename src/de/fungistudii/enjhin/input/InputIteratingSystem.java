package de.fungistudii.enjhin.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import de.fungistudii.enjhin.GameScreen;

/**
 * a standart {@link IteratingSystem} but in addition also provides the input
 * methods (so it just combines {@link IteratingSystem} and {@link InputAdapter})
 *
 * {@link InputOnDemandSystem} will be better in most cases
 *
 * @author Samuel
 */
public abstract class InputIteratingSystem extends IteratingSystem implements InputProcessor {

    public InputIteratingSystem(Family family, int priority) {
        super(family, priority);
    }

    public InputIteratingSystem(Family family) {
        super(family);
    }

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
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
