/*
 * Samuel hasafunden. Deshalb:
 * alle rechte gehören ganz alleine mia.
 * wer gegen die obige Lizensvereinbarung verstöst wird mit 
 * sofortiger wirkung defenstriert!
 */
package de.fungistudii.enjhin.box2d.contactInterfaces;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.enjhin.box2d.Box2DUtils;
import de.fungistudii.enjhin.utils.Multiplexer;

/**
 * a {@link ContactFilter} that calls {@link ContactFilter#shouldCollide()} for
 * an array of {@link ContactFilters ContactFilter}
 *
 * @author sreiser
 */
public class CFMultiplexer extends Multiplexer<ContactFilter> implements ContactFilter {

    public CFMultiplexer(ContactFilter... receivers) {
        super(receivers);
    }

    public CFMultiplexer(Array<ContactFilter> receivers) {
        super(receivers);
    }

    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
        for (ContactFilter contactFilter : receivers) {
            if (!contactFilter.shouldCollide(fixtureA, fixtureB)) {
                return false;
            }
        }
        return Box2DUtils.checkFilterData(fixtureA, fixtureB);
    }
}
